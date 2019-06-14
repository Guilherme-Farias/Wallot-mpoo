package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.negocio.UsuarioServices;

public class RegisterActivity extends AppCompatActivity {
    private EditText editNome, editEmail, editSenha, editConfirmarSenha;
    private TextView textIntentLogin;
    private Button cadastroButton;
    private UsuarioServices usuarioServices = new UsuarioServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editNome = findViewById(R.id.edittext_nome_cadastro);
        editEmail = findViewById(R.id.edittext_email_cadastro);
        editSenha = findViewById(R.id.edittext_confirmar_senha_cadastro);
        editConfirmarSenha = findViewById(R.id.edittext_confirmar_senha_cadastro);
        textIntentLogin = findViewById(R.id.textview_logar);
        cadastroButton = findViewById(R.id.cadastrar_button);

        cadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

        textIntentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIntent();
            }
        });

    }

    private void loginIntent() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    private void cadastrar(){
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String confirmarSenha = editConfirmarSenha.getText().toString();
        if(validateFields(nome, email, senha, confirmarSenha)){
            Usuario usuario = new Usuario(nome,email,senha);
            try {
                usuarioServices.cadastrar(usuario);
                SessaoUsuario.instance.setUsuario(usuario);
                loginIntent();
            } catch (Exception e){
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private boolean validateFields(String nome, String email, String senha, String confirmarSenha){
        boolean res = true;
        editEmail.setError(null);
        editSenha.setError(null);
        editSenha.setError(null);
        editConfirmarSenha.setError(null);
        View focusView = null;
        if (nome.isEmpty()){
            editNome.setError("Campo obrigatório");
            focusView = editNome;
            res = false;
        } else if (!validateName(nome)){
            editNome.setError("Nome inválido, não aceito caracteres especiais");
            focusView = editNome;
            res = false;
        }
        if (senha.isEmpty()){
            editSenha.setError("Campo obrigatório");
            focusView = editSenha;
            res = false;
        }else if(!validatePassword(senha)) {
            editSenha.setError("Senha inválida");
            focusView = editSenha;
            res = false;
        } else if(!validateEqualsPassword(senha, confirmarSenha)){
            editSenha.setError("Senhas devem ser iguais");
            focusView = editSenha;
            res = false;
        }
        if(email.isEmpty()){
            editEmail.setError("Campo obrigatório");
            focusView = editEmail;
            res = false;
        } else if(!validateEmail(email)){
            editEmail.setError("Email inválido");
            focusView = editEmail;
            res = false;
        }
        return res;
    }

    private boolean validateEqualsPassword(String senha, String confirmarSenha) {
        return senha.equals(confirmarSenha);
    }

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword(String senha) {
        return senha.length() > 5;
    }

    private boolean validateName (String nome) {
        return nome.matches("^[a-zA-ZÁÂÃÀÇÉÊÍÓÔÕÚÜáâãàçéêíóôõúü ]*$");
    }
}

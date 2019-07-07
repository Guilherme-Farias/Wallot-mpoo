package com.ufrpe.bsi.mpoo.wallotapp.usuario.gui;

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
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.negocio.UsuarioServices;

public class RegisterActivity extends AppCompatActivity {
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfirmarSenha;
    private UsuarioServices usuarioServices = new UsuarioServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textIntentLogin;
        Button cadastroButton;

        //pega itens
        editNome = findViewById(R.id.edittext_nome_cadastro);
        editEmail = findViewById(R.id.edittext_email_cadastro);
        editSenha = findViewById(R.id.edittext_senha_cadastro);
        editConfirmarSenha = findViewById(R.id.edittext_confirmar_senha_cadastro);
        textIntentLogin = findViewById(R.id.textview_logar);
        cadastroButton = findViewById(R.id.cadastrar_button);

        //começa processo de cadastro
        cadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

        //vai para o login
        textIntentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIntent();
            }
        });

    }

    //cadastra o usuario
    private void cadastrar(){
        if(validateFields()){
            Usuario usuario = criaUsuario();
            try {
                usuarioServices.cadastrar(usuario);
                loginIntent();
            } catch (Exception e){
                showExceptionToast(e);
            }
        }

    }

    //controi um objeto usuario
    private Usuario criaUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome(editNome.getText().toString());
        usuario.setEmail(editEmail.getText().toString());
        usuario.setSenha(editSenha.getText().toString());
        return usuario;
    }

    //validador de campos
    private boolean validateFields(){
        //pega valores dos itens
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String confirmarSenha = editConfirmarSenha.getText().toString();

        //reseta erros
        boolean res = true;
        editEmail.setError(null);
        editSenha.setError(null);
        editSenha.setError(null);
        editConfirmarSenha.setError(null);
        View focusView = null;

        //valida nome
        if (nome.isEmpty()){
            editNome.setError("Campo obrigatório");
            focusView = editNome;
            res = false;
        } else if (!validateName(nome)){
            editNome.setError("Nome inválido, não aceito caracteres especiais");
            focusView = editNome;
            res = false;
        }
        //valida senhas
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

        //valida email
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

    //valida se as senhas são iguais
    private boolean validateEqualsPassword(String senha, String confirmarSenha) {return senha.equals(confirmarSenha);}

    //valida email pelo patterns
    private boolean validateEmail(String email) {return Patterns.EMAIL_ADDRESS.matcher(email).matches();}

    //valida tamanho da senha
    private boolean validatePassword(String senha) {return senha.length() > 5;}

    //verifica se o nome tem caracteres especiais
    private boolean validateName (String nome) {return nome.matches("^[a-zA-ZÁÂÃÀÇÉÊÍÓÔÕÚÜáâãàçéêíóôõúü ]*$");}

    //vai para o login
    private void loginIntent() {startActivity(new Intent(RegisterActivity.this, LoginActivity.class));}

    //Toast do Exception
    private void showExceptionToast(Exception e) {Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();}

    //volta para o login
    @Override
    public void onBackPressed() {loginIntent();}
}

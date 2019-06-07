package com.ufrpe.wallot_app.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.usuario.dominio.Usuario;
import com.ufrpe.wallot_app.usuario.negocio.UsuarioServices;
import com.ufrpe.wallot_app.infra.Sessao;

public class RegisterActivity extends AppCompatActivity {
    private EditText editNome, editEmail, editSenha, editConfimarSenha;
    private Button buttonCadastrar, buttonContaExistente;
    private UsuarioServices usuarioServices = new UsuarioServices();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        editConfimarSenha = findViewById(R.id.editConfimarSenha);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);
        buttonContaExistente = findViewById(R.id.buttonContaExistente);
        /*try {
            usuarioServices.cadastrar(new Usuario("gui","gui@gmail.com", "123456"));
        } catch (Exception e){
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }*/


        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

        buttonContaExistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });
    }

    private void cadastrar(){
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String confirmarSenha = editConfimarSenha.getText().toString();
        if(validateFields(nome, email, senha, confirmarSenha)){
            Usuario usuario = new Usuario(nome,email,senha);
            try {
                usuarioServices.cadastrar(usuario);
                Sessao.instance.setUsuario(usuario);
                logar();
            } catch (Exception e){
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void logar() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    private boolean validateFields(String nome, String email, String senha, String confirmarSenha){
        boolean res = true;
        editEmail.setError(null);
        editSenha.setError(null);
        editSenha.setError(null);
        editConfimarSenha.setError(null);
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

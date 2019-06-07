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
import com.ufrpe.wallot_app.usuario.negocio.UsuarioServices;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail, editSenha;
    private Button buttonLogin, buttonCadastrar;

    private UsuarioServices usuarioServices = new UsuarioServices();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    private void cadastrar() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void login() {
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        if(validateFields()){
            try{
                usuarioServices.login(email, senha);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } catch (Exception e){
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            return;
        }
    }

    private boolean validateFields(){
        boolean res = true;
        editEmail.setError(null);
        editSenha.setError(null);
        View focusView = null;
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        if (senha.isEmpty()){
            editSenha.setError("Campo obrigatório");
            focusView = editSenha;
            res = false;
        }else if(!validatePassword(senha)) {
            editSenha.setError("Senha inválida");
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

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword(String senha) {
        return senha.length() > 5;
    }
}

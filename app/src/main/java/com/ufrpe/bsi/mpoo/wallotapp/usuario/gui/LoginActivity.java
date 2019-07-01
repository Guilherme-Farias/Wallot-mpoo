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
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.MainActivity;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.negocio.UsuarioServices;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail, editSenha;
    private TextView textIntentRegister;
    private Button loginButton;
    private UsuarioServices usuarioServices = new UsuarioServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //pega os itens
        editEmail = findViewById(R.id.edittext_email_login);
        editSenha = findViewById(R.id.edittext_senha_login);
        textIntentRegister = findViewById(R.id.textview_registrar);
        loginButton = findViewById(R.id.loggin_button);

        //loga o usuario no sitema
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //leva o usuario para registrar
        textIntentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroIntent();
            }
        });

    }

    //leva o usuario para o registro
    private void CadastroIntent() {startActivity(new Intent(LoginActivity.this, RegisterActivity.class));}

    //
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

    private boolean validateEmail(String email) {return Patterns.EMAIL_ADDRESS.matcher(email).matches();}

    private boolean validatePassword(String senha) {return senha.length() > 5;}

    @Override
    public void onBackPressed() {finish();}
}

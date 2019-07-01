package com.ufrpe.bsi.mpoo.wallotapp.usuario.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.ConfiguracaoActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.negocio.UsuarioServices;

public class EditarUsuarioActivity extends AppCompatActivity {
    private EditText editNome,editEmail,editSenha;
    private Button btnSalvar;
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private UsuarioServices usuarioServices = new UsuarioServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        getSupportActionBar().setTitle("Editar Usuário");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //pega itens
        editNome = findViewById(R.id.edit_nome_alterar);
        editEmail = findViewById(R.id.edit_email_alterar);
        editSenha = findViewById(R.id.edit_senha_alterar);
        btnSalvar = findViewById(R.id.buttonSalvar);

        //pre seta alguns dados
        editNome.setHint(usuario.getNome());
        editEmail.setHint(usuario.getEmail());

        //salva dados
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editaSalvaDados();
            }
        });

    }

    //edita os dados de usuario
    private void editaSalvaDados() {
        if(validateFieldsUsuario()){
            Usuario userEditado = controiUsuario();
            if(usuarioServices.emailCadastrado(userEditado) == null){
                usuarioServices.alterarDados(userEditado);
                atualizarIntent();
                showToast("Dados editados");
            } else{
                showToast("Email já cadastrado");
            }
        }
    }


    //atualiza a activity
    private void atualizarIntent() {startActivity(new Intent(EditarUsuarioActivity.this, EditarUsuarioActivity.class));}

    //toast
    private void showToast(String s) {Toast.makeText(EditarUsuarioActivity.this, s, Toast.LENGTH_LONG).show();}

    //pega dados setados do usuario
    private Usuario controiUsuario() {
        Usuario usuarioEditado = new Usuario();
        usuarioEditado.setNome(editNome.getText().toString());
        usuarioEditado.setEmail(editEmail.getText().toString());
        usuarioEditado.setSenha(editSenha.getText().toString());
        return usuarioEditado;
    }

    private boolean validateFieldsUsuario(){
        //pega valores
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        //reseta erros
        boolean res = true;
        editEmail.setError(null);
        editSenha.setError(null);
        editSenha.setError(null);
        View focusView = null;

        //valida nome
        if (!validateName(nome) ){
            editNome.setError("Nome inválido, não aceito caracteres especiais");
            focusView = editNome;
            res = false;
        }
        //valida tamanho minimo
        if(!validateLenght(senha) && !senha.isEmpty()) {
            editSenha.setError("Senha inválida(menor que 5 caracteres");
            focusView = editSenha;
            res = false;
        }
        //valida email
        if(!validateEmail(email) && !email.isEmpty()){
            editEmail.setError("Email inválido");
            focusView = editEmail;
            res = false;
        }
        return res;
    }

    //valida email pelo patterns
    private boolean validateEmail(String email) {return Patterns.EMAIL_ADDRESS.matcher(email).matches();}

    //valida tamanho do nome
    private boolean validateLenght(String str) {return str.length() > 5;}

    //valida caracterez especiais no nome
    private boolean validateName (String nome) {return nome.matches("^[a-zA-ZÁÂÃÀÇÉÊÍÓÔÕÚÜáâãàçéêíóôõúü ]*$");}

    @Override
    public void onBackPressed() {startActivity(new Intent(EditarUsuarioActivity.this, ConfiguracaoActivity.class));}

}

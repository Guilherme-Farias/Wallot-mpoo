package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.gui.CategoriasActivity;
import com.ufrpe.bsi.mpoo.wallotapp.conta.gui.ContasActivity;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.gui.EditarUsuarioActivity;

public class ConfiguracaoActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        getSupportActionBar().setTitle("Configurações");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CardView cvUsuario;
        CardView cvConta;
        CardView cvCategoria;

        //pega dados do layout
        cvUsuario = findViewById(R.id.cardViewUsuario);
        cvConta = findViewById(R.id.cardViewConta);
        cvCategoria = findViewById(R.id.cardViewCategoria);


        //listener dos cardviews para ir editar(usuario/conta/categoria
        cvUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarUsuarioIntert();
            }
        });
        cvConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarContasIntent();
            }
        });
        cvCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarCategoriasIntent();
            }
        });


    }
    //intent de categorias
    private void listarCategoriasIntent() {startActivity(new Intent(ConfiguracaoActivity.this, CategoriasActivity.class));}

    //intent de contas
    private void listarContasIntent() {startActivity(new Intent(ConfiguracaoActivity.this, ContasActivity.class));}

    //intent de usuario
    private void editarUsuarioIntert() {startActivity(new Intent(ConfiguracaoActivity.this, EditarUsuarioActivity.class));}

    //volto para o inicio
    @Override
    public void onBackPressed() {startActivity(new Intent(ConfiguracaoActivity.this, MainActivity.class));}
}

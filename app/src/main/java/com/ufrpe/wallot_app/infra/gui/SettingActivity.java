package com.ufrpe.wallot_app.infra.gui;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.infra.ViewHolder;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    CardView cvUsuario, cvConta, cvCategoria, cvInicio, cvHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        cvUsuario = findViewById(R.id.cardViewUsuario);
        cvCategoria = findViewById(R.id.cardViewCategoria);
        cvConta = findViewById(R.id.cardViewConta);
        cvInicio = findViewById(R.id.cardViewInicio);
        cvHistorico = findViewById(R.id.cardViewHistorico);

        cvUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsuarioIntent();
            }
        });

        cvConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contaIntent();
            }
        });

        cvCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
            }
        });

        cvInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicioIntent();
            }
        });

        cvHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //donothing
            }
        });

    }

    private void editUsuarioIntent() {
        startActivity(new Intent(SettingActivity.this, EditUsuarioActivity.class));
    }

    private void contaIntent() {
        startActivity(new Intent(SettingActivity.this, ContasActivity.class));
    }

    private void inicioIntent(){
        startActivity(new Intent(SettingActivity.this, InicioActivity.class));
    }

    private void historicoIntent(){
        startActivity(new Intent(SettingActivity.this, HistoricoActivity.class));
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingActivity.this, MainActivity.class));
    }
}

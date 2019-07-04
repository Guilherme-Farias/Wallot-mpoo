package com.ufrpe.bsi.mpoo.wallotapp.conta.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.ConfiguracaoActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoConta;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;

public class ContasActivity extends AppCompatActivity implements OnRecyclerListener {
    RecyclerView mRecyclerView;
    ArrayList<Conta> contas;
    FloatingActionButton btnCriarConta;
    Usuario usuario = SessaoUsuario.instance.getUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);
        getSupportActionBar().setTitle("Contas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Cria recycler
        mRecyclerView = findViewById(R.id.recyclerview_conta);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ContasActivity.this);
        mRecyclerView.setLayoutManager(llm);

        //Pega contas do usuario
        contas = new ContaServices().listarContas(usuario.getId());
        RecyclerViewAdapterConta adapter = new RecyclerViewAdapterConta(ContasActivity.this, contas, this);
        mRecyclerView.setAdapter(adapter);

        //Cria conta do usuario
        btnCriarConta = findViewById(R.id.nova_conta_fab);
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessaoConta.instance.reset();
                crudContaIntent();
            }
        });
    }

    //vai para CrudConta
    private void crudContaIntent() {
        startActivity(new Intent(ContasActivity.this, CrudContaActivity.class));
    }


    //volta para configuração
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ContasActivity.this, ConfiguracaoActivity.class));
    }

    //pega a conta clicada e seta na sessão
    @Override
    public void onClickRecycler(int position) {
        SessaoConta.instance.setConta(contas.get(position));
        crudContaIntent();
    }

}

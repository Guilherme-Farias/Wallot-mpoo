package com.ufrpe.bsi.mpoo.wallotapp.conta.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoConta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoEstadoConta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.ConfiguracaoActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoConta;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ContasActivity extends AppCompatActivity implements OnRecyclerListener {
    RecyclerView mRecyclerView;
    ArrayList<Conta> contas;
    FloatingActionButton btnCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);
        final Usuario usuario = SessaoUsuario.instance.getUsuario();
        getSupportActionBar().setTitle("Contas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_conta);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ContasActivity.this);
        mRecyclerView.setLayoutManager(llm);

        contas = new ContaServices().listarContas(usuario.getId());
        RecyclerViewAdapterConta adapter = new RecyclerViewAdapterConta(ContasActivity.this, contas, this);
        mRecyclerView.setAdapter(adapter);


        btnCriarConta = findViewById(R.id.nova_conta_fab);
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conta conta = new Conta();
                conta.setId(0);
                conta.setFkUsuario(usuario.getId());
                conta.setTipoConta(TipoConta.DINHEIRO);
                conta.setTipoEstadoConta(TipoEstadoConta.ATIVO);
                SessaoConta.instance.setConta(conta);
                crudContaIntent();
            }
        });


    }


    private void crudContaIntent() {
        startActivity(new Intent(ContasActivity.this, CrudContaActivity.class));
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(ContasActivity.this, ConfiguracaoActivity.class));
    }


    @Override
    public void onClickRecycler(int position) {
        Log.d("Dalle", "clicado:" + contas.get(position).getId());
        SessaoConta.instance.setConta(contas.get(position));
        crudContaIntent();
    }

}

package com.ufrpe.bsi.mpoo.wallotapp.conta.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.ConfiguracaoActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoConta;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;

public class ContasActivity extends AppCompatActivity {
    ListView listViewContas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);
        final Usuario usuario = SessaoUsuario.instance.getUsuario();

        listViewContas = (ListView) findViewById(R.id.list_contas);
        listarContas(usuario.getId());
        listViewContas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conta conta = ((Conta)parent.getAdapter().getItem(position));
                SessaoConta.instance.setConta(conta);
                editarContaIntent();
            }
        });


    }


    private void editarContaIntent() {
        startActivity(new Intent(ContasActivity.this, CrudContaActivity.class));
    }

    private void listarContas(long idUsuario) {
            ContaServices contaServices = new ContaServices();
            ArrayList<Conta> contas = contaServices.listarContas(idUsuario);
            ArrayAdapter adapter = new ArrayAdapter<Conta>(ContasActivity.this,android.R.layout.simple_list_item_1,contas);
            listViewContas.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ContasActivity.this, ConfiguracaoActivity.class));
    }
}

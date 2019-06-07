package com.ufrpe.wallot_app.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.conta.negocio.ContaServices;
import com.ufrpe.wallot_app.infra.Sessao;
import com.ufrpe.wallot_app.infra.SessaoConta;
import com.ufrpe.wallot_app.usuario.dominio.Usuario;

import java.util.ArrayList;

public class ContasActivity extends AppCompatActivity {
    ListView listViewContas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);
        final Usuario usuario = Sessao.instance.getUsuario();


        listViewContas = (ListView) findViewById(R.id.list_contas);
        listarContas(usuario.getId());
        listViewContas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecionaConta(position, usuario);
                editarContaIntent();
            }
        });
    }

    private void editarContaIntent() {
        startActivity(new Intent(ContasActivity.this, EditContaActivity.class));
    }


    private void listarContas(long idUsuario) {
        ContaServices contaServices = new ContaServices();

        ArrayList<Conta> contas = contaServices.listarContas(idUsuario);
        ArrayList<String> contasStr = new ArrayList<String>();

        ArrayAdapter adapter = new ArrayAdapter<String>(ContasActivity.this,android.R.layout.simple_list_item_1,contasStr);
        listViewContas.setAdapter(adapter);
        for(Conta conta: contas){
            contasStr.add(conta.getNome() + "\n" +
                    "Saldo: R$" + conta.getSaldo().toString());
            adapter.notifyDataSetChanged();
        }
    }

    private void selecionaConta(int position, Usuario usuario) {
        String conteudo = (String) listViewContas.getItemAtPosition(position);
        String nomeContaSelecionada = conteudo.substring(0,conteudo.indexOf("\n"));
        ContaServices contaServices = new ContaServices();
        Conta conta = contaServices.getConta(usuario.getId(), nomeContaSelecionada);
        SessaoConta.instance.setConta(conta);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ContasActivity.this, SettingActivity.class));
    }
}

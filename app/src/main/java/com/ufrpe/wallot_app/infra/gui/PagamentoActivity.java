package com.ufrpe.wallot_app.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.conta.negocio.ContaServices;
import com.ufrpe.wallot_app.infra.Sessao;
import com.ufrpe.wallot_app.pagamento.dominio.Pagamento;
import com.ufrpe.wallot_app.usuario.dominio.Usuario;

import java.util.ArrayList;

public class PagamentoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        //final Usuario usuario = Sessao.instance.getUsuario();


       // ArrayList<Conta> contas = pegarContas(usuario.getId());
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,contas);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner spinConta = findViewById(R.id.spinner_conta);
        //spinConta.setAdapter(adapter);
        //long idConta = ((Conta)spinConta.getSelectedItem()).getId();


        /*;
        Pagamento pagamento = SessaoPagamento.instance.getPagamento();
        if(tipo == 1){

        }*/
    }
    private ArrayList<Conta> pegarContas(long idUsuario) {
        ContaServices contaServices = new ContaServices();
        ArrayList<Conta> contas = contaServices.listarContas(idUsuario);
        return contas;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PagamentoActivity.this, InicioActivity.class));
    }
}

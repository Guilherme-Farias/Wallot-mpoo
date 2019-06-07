package com.ufrpe.wallot_app.infra.gui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.conta.negocio.ContaServices;
import com.ufrpe.wallot_app.infra.DatePickerFragments;
import com.ufrpe.wallot_app.infra.Sessao;
import com.ufrpe.wallot_app.pagamento.dominio.Pagamento;
import com.ufrpe.wallot_app.pagamento.negocio.PagamentoServices;
import com.ufrpe.wallot_app.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransacaoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText editDescricao, editValor, editCategoria, editSubCategoria;
    TextView textData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);
        final Usuario usuario = Sessao.instance.getUsuario();


        editDescricao = findViewById(R.id.edit_descricao);
        editValor = findViewById(R.id.edit_valor);
        textData = findViewById(R.id.strData);
        ArrayList<Conta> contas = pegarContas(usuario.getId());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,contas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinConta = findViewById(R.id.spn_contas);
        spinConta.setAdapter(adapter);
        textData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        Button btnSalvar = findViewById(R.id.button_salvar_pagamento);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPagamento(usuario, spinConta);
            }
        });

    }

    private void salvarPagamento(Usuario usuario, Spinner spinner) {
        String descricao = editDescricao.getText().toString();
        String valor = editValor.getText().toString();
        String categoria = "Categoria";
        String subcategoria = "Subcategoria";
        String datastr = textData.getText().toString();

        long idConta = ((Conta)spinner.getSelectedItem()).getId();


        if(validateFields(descricao,valor)){
            Pagamento pagamento = SessaoPagamento.instance.getPagamento();
            pagamento.setDescricao(descricao);
            pagamento.setValor(new BigDecimal(valor));
            pagamento.setCategoria(categoria);
            pagamento.setSubCategoria(subcategoria);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();
            try {
                data = sdf.parse(datastr);
            } catch (Exception e){
                Toast.makeText(TransacaoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            pagamento.setDataPagamento(data);
            pagamento.setTbTipoPagamento(SessaoPagamento.instance.getPagamento().getTbTipoPagamento());
            pagamento.setTbConta(idConta);
            PagamentoServices pagamentoServices = new PagamentoServices();
            long id =pagamentoServices.cadastraPagamento(pagamento);
            Toast.makeText(TransacaoActivity.this, "Transação efetuada com sucesso", Toast.LENGTH_LONG).show();
            startActivity(new Intent(TransacaoActivity.this, InicioActivity.class));

        }
        //do nothing
        //validar campos
        //confirma com popUp
        //salva volta tela inicial
        //para saber se foi receita despesa tranferencia = novo DB
    }

    private boolean validateFields(String nomeConta, String saldoConta) {
            boolean res = true;
            editDescricao.setError(null);
            editValor.setError(null);
            View focusView = null;
            /*if (!validateLenght(nomeConta, 2)){
                editDescricao.setError("Nome inválido, minimo de 2");
                focusView = editTextNome;
                res = false;
            }*/
            return res;

    }

    private ArrayList<Conta> pegarContas(long idUsuario) {
        ContaServices contaServices = new ContaServices();
        ArrayList<Conta> contas = contaServices.listarContas(idUsuario);
        return contas;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TransacaoActivity.this, InicioActivity.class));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.add(Calendar.DATE, 0);
        Date date = c.getTime();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        String datestr = formatdate.format(date);
        TextView strData = findViewById(R.id.strData);
        strData.setText(datestr);
    }

    private boolean validateLenght(String str, int tam) {
        return str.length() > tam;
    }


}

package com.ufrpe.bsi.mpoo.wallotapp.transacao.gui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.MainActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.DatePickerFragments;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoParcela;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoDeStatusTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CrudParcelaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private Transacao transacao = SessaoTransacao.instance.getTransacao();
    private TransacaoServices transacaoServices = new TransacaoServices();
    private Parcela parcela = SessaoParcela.instance.getParcela();
    private EditText valorParcela;
    private CheckBox checkConsolidado;
    private TextView dataStr;
    private Button btnSalvar, btnExluir;
    private SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_parcela);
        getSupportActionBar().setTitle("Criar parcela");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //pega itens do layout
        checkConsolidado = findViewById(R.id.consolidado_parcela_crud);
        dataStr = findViewById(R.id.str_data_parcela_crud);
        valorParcela = findViewById(R.id.valor_parcela_crud);
        btnSalvar = findViewById(R.id.button_salvar_parcela_crud);
        btnExluir = findViewById(R.id.button_excluir_parcela_crud);
        dataStr.setText(formatdate.format(new Date()));

        //se estiver editando ele irá setar os dados da parcela
        if(parcela != null){
            getSupportActionBar().setTitle("Editar Parcela");
            if(parcela.getTipoDeStatusTransacao() == TipoDeStatusTransacao.CONSOLIDADO){checkConsolidado.setChecked(true);}
            valorParcela.setText(parcela.getValorParcela().multiply(getMultiplicador()).toString());
            dataStr.setText(formatdate.format(parcela.getDataTransacao()));
            btnExluir.setVisibility(View.VISIBLE);
        }

        //pega a data por um dialog
        dataStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //começa o processo de salvar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarParcela();

            }
        });

        //começa o processo de excluir
        btnExluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirIntentDialog();
            }
        });
    }

    //abre um dialog que verifica se o usuario deseja mesmo escluir
    private void excluirIntentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CrudParcelaActivity.this);
        builder.setMessage("Você tem certeza que deseja excluir a parcela?")
                .setCancelable(false)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {excluirParcela();}
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {dialog.cancel();}
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //cria/edita a parcela
    private void salvarParcela() {
        Parcela novaParcela = controiParcela();
        if(parcela == null){
            transacaoServices.cadastraParcela(novaParcela);
            showToast("Parcela Criada com sucesso");
            parcelasDaTransacaoIntent();
        } else {
            transacaoServices.editarParcela(novaParcela);
            showToast("Parcela Editada com sucesso");
            getTipoIntent();
        }
    }

    //verifica se vai para tela de inicio ou se vai para tela de parcelas desta transacao
    private void getTipoIntent() {
        if (transacao.getQntParcelas() > 1) {
            parcelasDaTransacaoIntent();
        } else {
            inicioIntent();
        }
    }

    //controi o objeto parcela
    private Parcela controiParcela() {
        Parcela novaParcela = new Parcela();
        novaParcela.setValorParcela(getValorParcela());
        novaParcela.setDataTransacao(getValorDate());
        novaParcela.setTipoDeStatusTransacao(getValorStatus());
        novaParcela.setFkTransacao(transacao.getId());
        return novaParcela;
    }

    //pega valor da data setada em Date
    private Date getValorDate() {
        Date date = null;
        try {
            date = formatdate.parse(dataStr.getText().toString());
        } catch (Exception e){
            showToast(e.getMessage());
        }
        return date;
    }

    //verifica se esta consolidado ou não
    private TipoDeStatusTransacao getValorStatus() {
        TipoDeStatusTransacao tipoDeStatusTransacao = TipoDeStatusTransacao.NAO_CONSOLIDADO;
        if(checkConsolidado.isChecked()){
            tipoDeStatusTransacao = TipoDeStatusTransacao.CONSOLIDADO;
        }
        return tipoDeStatusTransacao;
    }

    //pega o valor multiplicado ao tipo de transacao
    private BigDecimal getValorParcela() {
        BigDecimal multiplicador = getMultiplicador();
        BigDecimal valorSetado = getValorSetado();
        return valorSetado.multiply(multiplicador);
    }

    //verifica o valor que está escrito(no campo de texto)
    private BigDecimal getValorSetado() {return new BigDecimal(valorParcela.getText().toString());}

    //pega o multiplicador transacao(ou seja se é despesa ou receita
    private BigDecimal getMultiplicador() {return new BigDecimal(transacao.getTipoTransacao().getMultiplicador());}

    //mostra msg de Toast
    private void showToast(String s) {Toast.makeText(CrudParcelaActivity.this , s, Toast.LENGTH_LONG).show();}

    //vai para tela de inicio
    private void inicioIntent() {startActivity(new Intent(CrudParcelaActivity.this, MainActivity.class));}

    //vai para a tela com todas as parcelas dessa transacao
    private void parcelasDaTransacaoIntent() {startActivity(new Intent(CrudParcelaActivity.this, ParcelasDaTransacaoActivity.class));}

    //exclui a parcela e volta para a tela de inicio
    private void excluirParcela() {
        transacaoServices.deletarParcela(parcela);
        showToast("Parcela Deleteda com sucesso");
        inicioIntent();
    }

    //pega a data que o usuario setar e coloca no texto
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.add(Calendar.DATE, 0);
        Date date = c.getTime();
        dataStr.setText(formatdate.format(date));
    }

    //se for uma transacao com mais de uma parcela lista todas/ se não volta para telade inicio
    @Override
    public void onBackPressed() {
        getTipoIntent();
    }
}

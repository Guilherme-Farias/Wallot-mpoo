package com.ufrpe.bsi.mpoo.wallotapp.transacao.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.MainActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoParcela;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ParcelasDaTransacaoActivity extends AppCompatActivity implements OnRecyclerListener {
    private Transacao transacao = SessaoTransacao.instance.getTransacao();
    private TextView valorPagoTransacao, valorTotalTransacao;
    private FloatingActionButton fabCriaParcela;
    private RecyclerView mRecyclerView;
    private ArrayList<Parcela> parcelas;
    private RecyclerViewAdapterTransacao adapter;
    private TransacaoServices transacaoServices = new TransacaoServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelas_da_transacao);

        valorPagoTransacao = findViewById(R.id.valor_pago_transacao_parcela);
        valorTotalTransacao = findViewById(R.id.valor_total_transacao_parcela);
        fabCriaParcela = findViewById(R.id.nova_parcela_transacao);


        BigDecimal[] valores = transacaoServices.getValorTotalTransacao(transacao.getId());
        valorPagoTransacao.setText(getValorFormatado(valores[0]));
        valorTotalTransacao.setText(getValorFormatado(valores[1]));


        //cria o recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_parcelas_da_transacao);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ParcelasDaTransacaoActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        //coloca a lista no recycler
        parcelas = transacaoServices.listarParcelasTransacao(transacao.getId());
        adapter = new RecyclerViewAdapterTransacao(ParcelasDaTransacaoActivity.this, parcelas, this);
        mRecyclerView.setAdapter(adapter);

        //cria uma parcela para esta transacao
        fabCriaParcela = findViewById(R.id.nova_parcela_transacao);
        fabCriaParcela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessaoParcela.instance.reset();
                crudParcelaIntent();
            }
        });


    }

    private void crudParcelaIntent() {
        startActivity(new Intent(ParcelasDaTransacaoActivity.this, CrudParcelaActivity.class));
    }

    private String getValorFormatado(BigDecimal valorTotal) {
        StringBuilder saldoStr = new StringBuilder(valorTotal.toString());
        if(valorTotal.compareTo(new BigDecimal(0)) < 0){
            saldoStr.insert(1, "R$");
        } else {
            saldoStr.insert(0, "R$");
        }
        return saldoStr.toString().replace(".",",");
    }

    @Override
    public void onClickRecycler(final int position) {
        SessaoParcela.instance.setParcela(parcelas.get(position));
        acessarParcelaIntent();
    }
    private void acessarParcelaIntent() {
        crudParcelaIntent();
    }

    @Override
    public void onBackPressed() {
        //ir para registro fragments
        startActivity(new Intent(ParcelasDaTransacaoActivity.this, MainActivity.class));
    }
}

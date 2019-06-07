package com.ufrpe.wallot_app.infra.gui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.infra.SessaoTipoPagamento;
import com.ufrpe.wallot_app.pagamento.dominio.Pagamento;

public class InicioActivity extends AppCompatActivity {
    FloatingActionButton fabNovaReceita, fabNovaDespesa,fabNovaTransferencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button adcSaldo = findViewById(R.id.novoSaldo);
        Button adcDespesa = findViewById(R.id.novaDespesa);

        adcDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pagamento pagamento = new Pagamento();
                pagamento.setTbTipoPagamento(2);
                SessaoPagamento.instance.setPagamento(pagamento);
                try{
                    startActivity(new Intent(InicioActivity.this, TransacaoActivity.class));
                } catch (Exception e){
                    Toast.makeText(InicioActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        adcSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pagamento pagamento = new Pagamento();
                pagamento.setTbTipoPagamento(1);
                SessaoPagamento.instance.setPagamento(pagamento);
                startActivity(new Intent(InicioActivity.this, TransacaoActivity.class));
            }
        });

        /*try {
            fabNovaReceita = findViewById(R.id.nova_receita_fab);
            fabNovaDespesa = findViewById(R.id.nova_despesa_fab);
            fabNovaTransferencia = findViewById(R.id.nova_transferencia_fab);
        } catch (Exception e){
            Toast.makeText(InicioActivity.this, e.getMessage(), Toast.LENGTH_LONG);
        }
        fabNovaReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "nova receita", Toast.LENGTH_LONG).show();
            }
        });


        fabNovaDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "nova despesa", Toast.LENGTH_LONG).show();
            }
        });


        fabNovaTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "nova transferencia", Toast.LENGTH_LONG).show();
            }
        });*/
    }
}

package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.gui.TransacaoActivity;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.math.BigDecimal;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private ContaServices contaServices = new ContaServices();


    public InicioFragment() {
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        //pega itens do layout
        FloatingActionButton fab_1 = view.findViewById(R.id.nova_receita);
        FloatingActionButton fab_2 = view.findViewById(R.id.nova_despesa);
        FloatingActionButton fab_3 = view.findViewById(R.id.nova_transferencia);
        TextView valorTotalConta = view.findViewById(R.id.saldo_total_inicio);

        //pega o saldo de todas as contas do usuario e mostra aqui
        valorTotalConta.setText(getSaldoContas());

        //vai fazer uma nova receita
        fab_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Nova receita");
                transacaoIntent(TipoTransacao.RECEITA);
            }
        });
        //vai fazer uma nova despesa
        fab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Nova despesa");
                transacaoIntent(TipoTransacao.DESPESA);
            }
        });
        //vai fazer uma nova transferencia
        fab_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Nova tranferência");
                transacaoIntent(TipoTransacao.TRANSFERENCIA);
            }
        });
        return view;
    }

    private void transacaoIntent(TipoTransacao tipoTransacao) {
        startActivity(new Intent(getActivity(), TransacaoActivity.class).putExtra("TipoTransacao", tipoTransacao));
    }

    private String getSaldoContas() {
        BigDecimal saldoTotal = contaServices.getSaldoAtual(usuario.getId());
        StringBuilder saldoStr = new StringBuilder(saldoTotal.toString());
        if(saldoTotal.compareTo(new BigDecimal(0)) < 0){
            saldoStr.insert(1, "R$");
        } else {
            saldoStr.insert(0, "R$");
        }
        return saldoStr.toString().replace(".",",");
    }

    private void showToast(String messagem) {
        Toast.makeText(getContext(),messagem, Toast.LENGTH_LONG).show();
    }
}

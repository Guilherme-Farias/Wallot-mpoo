package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoParcela;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.gui.CrudParcelaActivity;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.gui.ParcelasDaTransacaoActivity;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.gui.RecyclerViewAdapterTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment implements OnRecyclerListener{
    private RecyclerView mRecyclerView;
    private ArrayList<Parcela> parcelas;
    private RecyclerViewAdapterTransacao adapter;
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private TransacaoServices transacaoServices = new TransacaoServices();
    int position = 0;

    public RegistroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registro, container, false);

        //pega os itens do layout
        mRecyclerView =  v.findViewById(R.id.recyclerview_transacao);

        //cria o Recycler
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        //seta a lista no recycler
        parcelas = transacaoServices.listarParcelasPorData(usuario.getId());
        adapter = new RecyclerViewAdapterTransacao(getActivity(), parcelas, this);
        mRecyclerView.setAdapter(adapter);


        return v;
    }

    //pega valor do click no recycler
    @Override
    public void onClickRecycler(final int position) {
        Transacao transacao = new TransacaoServices().getTransacao(parcelas.get(position).getFkTransacao());
        if (transacao.getQntParcelas() > 1){
            intentParcelaDialog(position);
        } else {
            setTransacaoSessao(position);
            showToast("Acessando esta parcela");
            acessarParcelaIntent();
        }
    }

    //seta a transacao na sessao pelo id da parcela
    private void setTransacaoSessao(int position) {
        SessaoParcela.instance.setParcela(parcelas.get(position));
        SessaoTransacao.instance.setTransacao(new TransacaoServices().getTransacao(parcelas.get(position).getFkTransacao()));
    }

    //dialog que quer saber qual o intent do usuario
    private void intentParcelaDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] list = getActivity().getResources().getStringArray(R.array.escolha_parcela);
        builder.setTitle("Faz parte de uma transação com parcelas, você deseja:")
                .setCancelable(false)
                .setSingleChoiceItems(list, this.position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        RegistroFragment.this.position = i;
                    }
                })
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {getParcelaIntent(position);}
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {dialog.cancel();}
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //verifica se o usuario deseja editar está parcela ou listar todas as parcelas da transacao
    private void getParcelaIntent(int position) {
        if(this.position == 0){
            setTransacaoSessao(position);
            showToast("Acessando esta parcela");
            acessarParcelaIntent();
        } else {
            setTransacaoSessao(position);
            showToast("Acessando todas as parcelas");
            parcelasDaTransacaoIntent();
        }
    }

    //vai para tela onde lista todas as parcelas desta transacao
    private void parcelasDaTransacaoIntent() {
        startActivity(new Intent(getActivity(), ParcelasDaTransacaoActivity.class));
    }

    //metodo de toast madrao
    private void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    //vai para o crud parcela(no sentido de editar
    private void acessarParcelaIntent() {
        startActivity(new Intent(getActivity(), CrudParcelaActivity.class));
    }
}

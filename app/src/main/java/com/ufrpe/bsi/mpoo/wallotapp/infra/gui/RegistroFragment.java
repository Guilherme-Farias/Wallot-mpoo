package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.gui.RecyclerViewAdapterTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia.TransacaoDAO;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<Parcela> parcelas;

    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registro, container, false);
        final Usuario usuario = SessaoUsuario.instance.getUsuario();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_transacao);
        mRecyclerView.setHasFixedSize(true);
        /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lln = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                RecyclerViewAdapterTransacao adapter = (RecyclerViewAdapterTransacao) mRecyclerView.getAdapter();
                if(parcelas.size() == lln.findLastCompletelyVisibleItemPosition() + 1){
                    ArrayList<Parcela> parcelasAux = parcelas;
                    for (int i = 0; i < parcelasAux.size(); i++) {
                        adapter.addListItem(parcelasAux.get(i),parcelas.size());
                    }
                }

            }
        });*/
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        ArrayList<Parcela> parcelas = new TransacaoServices().listarParcelasPorData(usuario.getId());
        RecyclerViewAdapterTransacao adapter = new RecyclerViewAdapterTransacao(getActivity(), parcelas);
        mRecyclerView.setAdapter(adapter);





        /*ListView l = v.findViewById(R.id.list_pagamentos);
        TransacaoServices transacaoServices = new TransacaoServices();
        ArrayList<Transacao> transacaos = transacaoServices.listarTransacaoPorData(usuario.getId());
        ArrayAdapter adapter = new ArrayAdapter<Transacao>(getContext(),android.R.layout.simple_list_item_1,transacaos);
        l.setAdapter(adapter);*/

        return v;
    }

}

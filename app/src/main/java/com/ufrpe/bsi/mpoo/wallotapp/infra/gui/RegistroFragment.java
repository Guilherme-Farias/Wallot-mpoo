package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.gui.RecyclerViewAdapterTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment implements OnRecyclerListener {
    private RecyclerView mRecyclerView;
    private ArrayList<Parcela> parcelas;
    private ArrayList<Parcela> todasParcelas;
    RecyclerViewAdapterTransacao adapter;

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
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        parcelas = new TransacaoServices().listarParcelasPorData(usuario.getId());
        todasParcelas = new ArrayList<Parcela>(parcelas);
        adapter = new RecyclerViewAdapterTransacao(getActivity(), parcelas, this);
        mRecyclerView.setAdapter(adapter);
        //adapter.onClick
        return v;
    }

    @Override
    public void onClickRecycler(int position) {
        /*View v = mRecyclerView.findViewHolderForAdapterPosition(position);
        Parcela p = v.find
        Parcela parcelaClicada = parcelas.get(position);*/
        //System.out.println(parce);
        //RecyclerViewAdapterTransacao adapter = (RecyclerViewAdapterTransacao) mRecyclerView.getAdapter();
        //adapter.getItem
        Transacao transacao = new TransacaoServices().getTransacao(parcelas.get(position).getFkTransacao());
        if (transacao.getQntParcelas() > 1){
            //abre dialog mostrando se ele deseja ver somente esta ou se deseja ver todas
        } else {
            //abre dialog disponibilizando mudanças
        }
    }

    public ArrayList<Parcela> filtrarParcela(Categoria categoria, SubCategoria subCategoria, Conta conta, TipoTransacao tipoTransacao/*TipoStatus*/){
        ArrayList<Parcela> parcelasFiltradas = new ArrayList<Parcela>();
        for(Parcela parcela:todasParcelas){
            Transacao transacao = new TransacaoServices().getTransacao(parcela.getFkTransacao());
            if (transacao.getFkCategoria() == categoria.getId()){
                parcelasFiltradas.add(parcela);
            }
        }
        return parcelasFiltradas;
    }

}

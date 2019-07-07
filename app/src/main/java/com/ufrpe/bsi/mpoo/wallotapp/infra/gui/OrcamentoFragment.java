package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.estatistica.dominio.Orcamento;
import com.ufrpe.bsi.mpoo.wallotapp.estatistica.gui.RecyclerViewAdapterOrcamento;
import com.ufrpe.bsi.mpoo.wallotapp.estatistica.negocio.OrcamentoServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoOrcamento;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrcamentoFragment extends Fragment implements OnRecyclerListener {
    private RecyclerView mRecyclerView;
    private ArrayList<Orcamento> orcamentos;
    private RecyclerViewAdapterOrcamento adapter;
    private OrcamentoServices orcamentoServices = new OrcamentoServices();
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    int position = 0;


    public OrcamentoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orcamento, container, false);

        mRecyclerView =  v.findViewById(R.id.recyclerview_orcamento);

        //cria o Recycler
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        //seta a lista no recycler
        orcamentos = orcamentoServices.listarOrcamentosPorData(usuario.getId());
        adapter = new RecyclerViewAdapterOrcamento(getActivity(), orcamentos, this);
        mRecyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onClickRecycler(int position) {
        showToast("Acessando este Orcamento");

    }

    private void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }
}

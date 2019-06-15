package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {


    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registro, container, false);
        ListView l = v.findViewById(R.id.list_pagamentos);
        TransacaoServices transacaoServices = new TransacaoServices();
        Usuario usuario = SessaoUsuario.instance.getUsuario();
        ArrayList<Transacao> transacaos = transacaoServices.listarTransacaoPorData(usuario.getId());
        ArrayAdapter adapter = new ArrayAdapter<Transacao>(getContext(),android.R.layout.simple_list_item_1,transacaos);
        l.setAdapter(adapter);

        return v;
    }

}

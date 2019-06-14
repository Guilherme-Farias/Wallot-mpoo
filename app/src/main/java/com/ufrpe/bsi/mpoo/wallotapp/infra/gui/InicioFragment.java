package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.gui.TransacaoActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {


    public InicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        FloatingActionButton fab_1 = view.findViewById(R.id.nova_receita);
        FloatingActionButton fab_2 = view.findViewById(R.id.nova_despesa);
        FloatingActionButton fab_3 = view.findViewById(R.id.nova_transferencia);

        fab_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Nova receita");
                Transacao transacao = new Transacao();
                transacao.setTipoTransacao(TipoTransacao.RECEITA);
                SessaoTransacao.instance.setTransacao(transacao);
                startActivity( new Intent(getActivity(), TransacaoActivity.class));
            }
        });

        fab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Nova despesa");
                Transacao transacao = new Transacao();
                transacao.setTipoTransacao(TipoTransacao.DESPESA);
                SessaoTransacao.instance.setTransacao(transacao);
                startActivity( new Intent(getActivity(), TransacaoActivity.class));
            }
        });

        fab_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Nova tranferência");
            }
        });


        return view;
    }

    private void showToast(String messagem) {
        Toast.makeText(getContext(),messagem, Toast.LENGTH_LONG).show();
    }


}

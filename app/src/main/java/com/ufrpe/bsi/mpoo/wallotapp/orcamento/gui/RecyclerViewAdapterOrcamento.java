package com.ufrpe.bsi.mpoo.wallotapp.orcamento.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.orcamento.dominio.Orcamento;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;

import java.math.BigDecimal;
import java.util.ArrayList;

public class RecyclerViewAdapterOrcamento extends RecyclerView.Adapter<RecyclerViewAdapterOrcamento.OrcamentoViewHolder>{

    private Context context;
    private ArrayList<Orcamento> orcamentos;
    private OnRecyclerListener onRecyclerListener;

    public RecyclerViewAdapterOrcamento(Context context, ArrayList<Orcamento> orcamentos, OnRecyclerListener onRecyclerListener) {
        this.context = context;
        this.orcamentos = orcamentos;
        this.onRecyclerListener = onRecyclerListener;

    }

    @NonNull
    @Override
    public OrcamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_orcamento_list , parent, false);
        return new OrcamentoViewHolder(view, onRecyclerListener);
    }


    @Override
    public void onBindViewHolder(@NonNull OrcamentoViewHolder holder, int position) {
        holder.titulo.setText(orcamentos.get(position).getTitulo());
        holder.gastoPlanejado.setText(getValorOrcamento(orcamentos.get(position).getGastoEstimado()));
        String[] datas = orcamentos.get(position).getDatesFormatada();
        holder.dataInicial.setText(datas[0]);
        holder.dataFinal.setText(datas[1]);
    }

    @Override
    public int getItemCount() {
        return orcamentos.size();
    }


public class OrcamentoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titulo;
        TextView gastoPlanejado;
        TextView dataInicial;
        TextView dataFinal;

        RelativeLayout parentLayout;
        OnRecyclerListener onRecyclerListener;

        private OrcamentoViewHolder(@NonNull View itemView, OnRecyclerListener onRecyclerListener) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_orcamento_list);
            gastoPlanejado = itemView.findViewById(R.id.gasto_planejado_orcamento_list);
            dataInicial = itemView.findViewById(R.id.data_inicio_orcamento_list);
            dataFinal = itemView.findViewById(R.id.data_final_orcamento_list);
            parentLayout = itemView.findViewById(R.id.layout_orcamento);
            this.onRecyclerListener = onRecyclerListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyclerListener.onClickRecycler(getAdapterPosition());
        }
    }

    //adicionara itens ao recycler
    public void addListItem(Orcamento p, int position) {
        orcamentos.add(p);
        notifyItemInserted(position);
    }

    //transforma o valor em
    private String getValorOrcamento(BigDecimal saldo) {
        StringBuilder saldoStr = new StringBuilder(saldo.toString());
        saldoStr.insert(0, "R$");
        return saldoStr.toString().replace(".", ",");
    }

}

package com.ufrpe.bsi.mpoo.wallotapp.conta.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;

import java.util.ArrayList;

public class RecyclerViewAdapterConta extends RecyclerView.Adapter<RecyclerViewAdapterConta.ViewHolder>{
    private ArrayList<Conta> contas;
    private Context context;
    private OnRecyclerListener onRecyclerListener;

    public RecyclerViewAdapterConta(Context context, ArrayList<Conta> contas, OnRecyclerListener onRecyclerListener) {
        this.context = context;
        this.contas = contas;
        this.onRecyclerListener = onRecyclerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_conta_list, parent, false);
        return new ViewHolder(view, onRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nome.setText(contas.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return contas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nome;
        RelativeLayout parentLayout;
        OnRecyclerListener onRecyclerListener;


        private ViewHolder(@NonNull View itemView, OnRecyclerListener onRecyclerListener) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome_conta_list);
            parentLayout = itemView.findViewById(R.id.layout_conta);
            this.onRecyclerListener = onRecyclerListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyclerListener.onClickRecycler(getAdapterPosition());
        }
    }

    //ira adicionar itens no recycler
    public void addListItem(Conta c, int position){
        contas.add(c);
        notifyItemInserted(position);
    }
}

package com.ufrpe.bsi.mpoo.wallotapp.transacao.gui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio.CategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio.SubCategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;

import java.math.BigDecimal;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterTransacao extends RecyclerView.Adapter<RecyclerViewAdapterTransacao.TransacaoViewHolder>{
    private Context context;
    private ArrayList<Parcela> parcelas;
    private OnRecyclerListener onRecyclerListener;

    public RecyclerViewAdapterTransacao(Context context, ArrayList<Parcela> parcelas, OnRecyclerListener onRecyclerListener) {
        this.context = context;
        this.parcelas = parcelas;
        this.onRecyclerListener = onRecyclerListener;

    }

    @NonNull
    @Override
    public TransacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transacao_list, parent, false);
        return new TransacaoViewHolder(view, onRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TransacaoViewHolder holder, final int position) {
        //pega variaveis necessarias
        Transacao transacao = new TransacaoServices().getTransacao(parcelas.get(position).getFkTransacao());
        Categoria categoria = new CategoriaServices().getCategoria(transacao.getFkCategoria());
        SubCategoria subCategoria = new SubCategoriaServices().getSubCategoria(transacao.getFkSubCategoria());
        Conta conta = new ContaServices().getConta(transacao.getFkConta());


        holder.titulo.setText(transacao.getTitulo());
        holder.valor.setText(getValorParcela(transacao.getTipoTransacao(), parcelas.get(position).getValorParcela()));
        holder.status.setText(parcelas.get(position).getTipoDeStatusTransacao().getDescricao());
        holder.data.setText(parcelas.get(position).dataFormatada());
        holder.conta.setText(conta.getNome());

        //muda a cor do texto de acordo com o tipo de transacao
        if(transacao.getTipoTransacao() == TipoTransacao.RECEITA){
            holder.valor.setTextColor(Color.parseColor("#008000"));
        } else {
            holder.valor.setTextColor(Color.parseColor("#ff0000"));
        }

        //coloca no card o mais específico(subcategoria se houver, caso contrario a categoria)
        if(subCategoria.getId() != 1){
            holder.image.setImageDrawable(subCategoria.byteArrayToDrawable(subCategoria.getIcone()));
            holder.categoriaSub.setText(subCategoria.toString());
        } else {
            Log.d("saas", categoria.getNome());
            holder.image.setImageDrawable(categoria.byteArrayToDrawable(categoria.getIcone()));
            holder.categoriaSub.setText(categoria.toString());
        }
    }

    @Override
    public int getItemCount() {
        return parcelas.size();
    }


    public class TransacaoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView image;
        TextView titulo;
        TextView valor;
        TextView data;
        TextView categoriaSub;
        TextView conta;
        TextView status;


        RelativeLayout parentLayout;
        OnRecyclerListener onRecyclerListener;


        private TransacaoViewHolder(@NonNull View itemView, OnRecyclerListener onRecyclerListener) {
            super(itemView);
            image = itemView.findViewById(R.id.imagem_transacao_list);
            titulo = itemView.findViewById(R.id.titulo_transacao_list);
            valor = itemView.findViewById(R.id.valor_transacao_list);
            data = itemView.findViewById(R.id.data_transacao_list);
            categoriaSub = itemView.findViewById(R.id.categoria_subcategoria_list);
            status = itemView.findViewById(R.id.tipo_status_list);
            conta = itemView.findViewById(R.id.conta_transacacao_list);
            parentLayout = itemView.findViewById(R.id.layout_transacao);
            this.onRecyclerListener = onRecyclerListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyclerListener.onClickRecycler(getAdapterPosition());
        }
    }

    //transforma o valor em
    private String getValorParcela(TipoTransacao tipoTransacao, BigDecimal saldo) {
        StringBuilder saldoStr = new StringBuilder(saldo.toString());
        if(tipoTransacao == TipoTransacao.DESPESA){
            saldoStr.insert(1, "R$");
        } else{
            saldoStr.insert(0, "R$");
        }
        return saldoStr.toString().replace(".",",");
    }

    //adicionara itens ao recycler
    public void addListItem(Parcela p, int position){
        parcelas.add(p);
        notifyItemInserted(position);
    }

}

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
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio.CategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia.CategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio.SubCategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.persistencia.SubCategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia.TransacaoDAO;

import java.math.BigDecimal;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterTransacao extends RecyclerView.Adapter<RecyclerViewAdapterTransacao.ViewHolder>{
    private ArrayList<Parcela> parcelas;
    private Context context;

    public RecyclerViewAdapterTransacao(Context context, ArrayList<Parcela> parcelas) {
        this.context = context;
        this.parcelas = parcelas;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transacao_list, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Transacao transacao = new TransacaoServices().getTransacao(parcelas.get(position).getFkTransacao());
        Categoria categoria = new CategoriaServices().getCategoria(transacao.getFkCategoria());
        SubCategoria subCategoria = new SubCategoriaServices().getSubCategoria(transacao.getFkSubCategoria());
        Conta conta = new ContaServices().getConta(transacao.getFkConta());
        holder.titulo.setText(transacao.getTitulo());
        holder.valor.setText(getSaldoContas(transacao.getTipoTransacao(), parcelas.get(position).getValorParcela()));
        if(transacao.getTipoTransacao().toString() == "Receita"){
            holder.valor.setTextColor(Color.parseColor("#008000"));
        } else {
            holder.valor.setTextColor(Color.parseColor("#ff0000"));
        }
        holder.data.setText(parcelas.get(position).dataFormatada());
        if(subCategoria.getId() == 1){
            //colocar imagem de subCategoria
            holder.image.setCircleBackgroundColor(R.mipmap.ic_launcher_round);
            holder.categoriaSub.setText(subCategoria.toString());
        } else {
            //colocar imagem de Categoria(a categoria outros/Sem categoria tem que ter imagem)
            holder.image.setCircleBackgroundColor(R.mipmap.ic_launcher_round);
            holder.categoriaSub.setText(categoria.toString());
        }
        holder.conta.setText(conta.getNome());
        System.out.println(transacao.getFkConta());



    }

    @Override
    public int getItemCount() {
        return parcelas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView titulo, valor, data, categoriaSub, conta;
        RelativeLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imagem_categoria_list);
            titulo = itemView.findViewById(R.id.titulo_transacao_list);
            valor = itemView.findViewById(R.id.valor_transacao_list);
            data = itemView.findViewById(R.id.data_transacao_list);
            categoriaSub = itemView.findViewById(R.id.categoria_subcategoria_list);
            conta = itemView.findViewById(R.id.conta_transacacao_list);
            parentLayout = itemView.findViewById(R.id.layout_transacao);
        }

    }
    private String getSaldoContas(TipoTransacao tipoTransacao, BigDecimal saldo) {
        StringBuilder saldoStr = new StringBuilder();
        if(tipoTransacao.toString() == "Despesa"){
            saldoStr.append("-");
            saldoStr.append("R$:");
            saldoStr.append(saldo.toString().substring(1));
        } else{
            saldoStr.append("R$:");
            saldoStr.append(saldo.toString());
        }
        System.out.println(saldoStr.toString());
        return saldoStr.toString();
    }

    public void addListItem(Parcela p, int position){
        parcelas.add(p);
        notifyItemInserted(position);


    }

}

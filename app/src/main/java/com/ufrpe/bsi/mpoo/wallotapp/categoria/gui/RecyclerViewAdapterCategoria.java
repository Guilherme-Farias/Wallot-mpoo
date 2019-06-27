package com.ufrpe.bsi.mpoo.wallotapp.categoria.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterCategoria extends RecyclerView.Adapter<RecyclerViewAdapterCategoria.ViewHolder>{
    private ArrayList<Categoria> categorias;
    private Context context;
    private OnRecyclerListener onRecyclerListener;

    public RecyclerViewAdapterCategoria(Context context, ArrayList<Categoria> categorias, OnRecyclerListener onRecyclerListener) {
        this.context = context;
        this.categorias = categorias;
        this.onRecyclerListener = onRecyclerListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categoria_sub, parent, false);
        ViewHolder holder = new ViewHolder(view, onRecyclerListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageDrawable(categorias.get(position).byteArrayToDrawable(categorias.get(position).getIcone()));
        holder.nome.setText(categorias.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView imageView;
        TextView nome;
        RelativeLayout parentLayout;
        OnRecyclerListener onRecyclerListener;


        public ViewHolder(@NonNull View itemView, OnRecyclerListener onRecyclerListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagem_categoria_sub_list);
            nome = itemView.findViewById(R.id.nome_categoria_sub_list);
            parentLayout = itemView.findViewById(R.id.layout_categoria_sub);
            this.onRecyclerListener = onRecyclerListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyclerListener.onClickRecycler(getAdapterPosition());
        }
    }
    public void addListItem(Categoria c, int position){
        categorias.add(c);
        notifyItemInserted(position);
    }
}

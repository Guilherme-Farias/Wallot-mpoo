package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterSubCategoria extends RecyclerView.Adapter<RecyclerViewAdapterSubCategoria.SubCategoriaViewHolder>{
    private ArrayList<SubCategoria> subCategorias;
    private Context context;
    private OnRecyclerListener onRecyclerListener;

    public RecyclerViewAdapterSubCategoria(Context context, ArrayList<SubCategoria> subCategorias, OnRecyclerListener onRecyclerListener) {
        this.context = context;
        this.subCategorias = subCategorias;
        this.onRecyclerListener = onRecyclerListener;
    }

    @NonNull
    @Override
    public SubCategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categoria_sub, parent, false);
        return new SubCategoriaViewHolder(view, onRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoriaViewHolder holder, int position) {
        holder.imageView.setImageDrawable(subCategorias.get(position).byteArrayToDrawable(subCategorias.get(position).getIcone()));
        holder.nome.setText(subCategorias.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return subCategorias.size();
    }

    public class SubCategoriaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView imageView;
        TextView nome;
        RelativeLayout parentLayout;
        OnRecyclerListener onRecyclerListener;


        private SubCategoriaViewHolder(@NonNull View itemView, OnRecyclerListener onRecyclerListener) {
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

    //vai adicionar itens ao recycler
    public void addListItem(SubCategoria s, int position){
        subCategorias.add(s);
        notifyItemInserted(position);
    }
}

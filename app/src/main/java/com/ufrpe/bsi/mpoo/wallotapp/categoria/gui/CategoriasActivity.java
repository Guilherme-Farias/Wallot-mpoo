package com.ufrpe.bsi.mpoo.wallotapp.categoria.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio.CategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.ConfiguracaoActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.gui.SubCategoriasActivity;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;

public class CategoriasActivity extends AppCompatActivity implements OnRecyclerListener {
    private ArrayList<Categoria> categorias;
    private Usuario usuario = SessaoUsuario.instance.getUsuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView mRecyclerView;
        setContentView(R.layout.activity_categorias);
        getSupportActionBar().setTitle("Categorias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //cria o recycler view
        mRecyclerView = findViewById(R.id.recyclerview_categoria);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(CategoriasActivity.this);
        mRecyclerView.setLayoutManager(llm);

        //pega a lista no banco de dados
        categorias = new CategoriaServices().listarCategorias(usuario.getId());
        RecyclerViewAdapterCategoria adapter = new RecyclerViewAdapterCategoria(CategoriasActivity.this, categorias, this);
        mRecyclerView.setAdapter(adapter);
    }

    //vai para subcategoria
    private void subCategoriaIntent(){
        startActivity(new Intent(CategoriasActivity.this, SubCategoriasActivity.class));
    }

    //volta para tela de configuração e reseta a sessao
    @Override
    public void onBackPressed(){
        SessaoCategoria.instance.reset();
        startActivity(new Intent(CategoriasActivity.this, ConfiguracaoActivity.class));
    }

    //pega o click no recycler view e Seta a categoria para sua Sessão
    @Override
    public void onClickRecycler(int position) {
        SessaoCategoria.instance.setCategoria(categorias.get(position));
        subCategoriaIntent();
    }
}

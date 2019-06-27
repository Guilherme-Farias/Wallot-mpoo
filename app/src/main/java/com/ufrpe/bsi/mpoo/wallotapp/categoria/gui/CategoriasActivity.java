package com.ufrpe.bsi.mpoo.wallotapp.categoria.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    RecyclerView mRecyclerView;
    ArrayList<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        final Usuario usuario = SessaoUsuario.instance.getUsuario();
        getSupportActionBar().setTitle("Categorias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_categoria);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(CategoriasActivity.this);
        mRecyclerView.setLayoutManager(llm);

        categorias = new CategoriaServices().listarCategorias(usuario.getId());
        RecyclerViewAdapterCategoria adapter = new RecyclerViewAdapterCategoria(CategoriasActivity.this, categorias, this);
        mRecyclerView.setAdapter(adapter);
    }










    private void subCategoriaIntent(){
        startActivity(new Intent(CategoriasActivity.this, SubCategoriasActivity.class));
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(CategoriasActivity.this, ConfiguracaoActivity.class));
    }

    @Override
    public void onClickRecycler(int position) {
        Log.d("Dalle", "clicado:" + categorias.get(position).getId());
        SessaoCategoria.instance.setCategoria(categorias.get(position));
        subCategoriaIntent();
    }
}

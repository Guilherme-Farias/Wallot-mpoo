package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.gui.CategoriasActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.OnRecyclerListener;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoSubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio.SubCategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;

public class SubCategoriasActivity extends AppCompatActivity implements OnRecyclerListener {
    RecyclerView mRecyclerView;
    ArrayList<SubCategoria> subCategorias;
    FloatingActionButton btnCriarSubCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorias);
        final  Categoria categoria = SessaoCategoria.instance.getCategoria();
        final Usuario usuario = SessaoUsuario.instance.getUsuario();
        getSupportActionBar().setTitle("Subcategorias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_sub_categoria);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(SubCategoriasActivity.this);
        mRecyclerView.setLayoutManager(llm);

        subCategorias = new SubCategoriaServices().listarSubCategorias(usuario.getId(), categoria.getId());
        RecyclerViewAdapterSubCategoria adapter = new RecyclerViewAdapterSubCategoria(SubCategoriasActivity.this, subCategorias, this);
        mRecyclerView.setAdapter(adapter);

        btnCriarSubCategoria = findViewById(R.id.nova_subcategoria_fab);
        btnCriarSubCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessaoSubCategoria.instance.reset();
                startActivity(new Intent(SubCategoriasActivity.this, CrudSubCategoriaActivity.class));
            }
        });

    }



    @Override
    public void onBackPressed(){
        startActivity(new Intent(SubCategoriasActivity.this, CategoriasActivity.class));
    }

    @Override
    public void onClickRecycler(int position) {
        if(subCategorias.get(position).getFkUsuario() != 0){
            SessaoSubCategoria.instance.setSubCategoria(subCategorias.get(position));
            startActivity(new Intent(SubCategoriasActivity.this, CrudSubCategoriaActivity.class));
        } else {
            Toast.makeText(SubCategoriasActivity.this, "Subcategoria padrão", Toast.LENGTH_LONG).show();
        }
    }
}

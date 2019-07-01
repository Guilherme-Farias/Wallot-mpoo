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
    private RecyclerView mRecyclerView;
    private ArrayList<SubCategoria> subCategorias;
    private FloatingActionButton btnCriarSubCategoria;
    private Categoria categoria = SessaoCategoria.instance.getCategoria();
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private SubCategoriaServices subCategoriaServices = new SubCategoriaServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorias);
        getSupportActionBar().setTitle("Subcategorias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //pega itens
        btnCriarSubCategoria = findViewById(R.id.nova_subcategoria_fab);

        //cria recycler
        mRecyclerView = findViewById(R.id.recyclerview_sub_categoria);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(SubCategoriasActivity.this);
        mRecyclerView.setLayoutManager(llm);

        //pega alista e coloca no banco
        subCategorias = subCategoriaServices.listarSubCategorias(usuario.getId(), categoria.getId());
        RecyclerViewAdapterSubCategoria adapter = new RecyclerViewAdapterSubCategoria(SubCategoriasActivity.this, subCategorias, this);
        mRecyclerView.setAdapter(adapter);

        //cria uma subcategoria
        btnCriarSubCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {crudSubcategoriaIntent();
            }
        });
    }

    @Override
    public void onClickRecycler(int position) {
        if(subCategorias.get(position).getFkUsuario() != 0){
            SessaoSubCategoria.instance.setSubCategoria(subCategorias.get(position));
            crudSubcategoriaIntent();
        } else {showToast("Subcategoria padrão");}
    }

    private void showToast(String s) {Toast.makeText(SubCategoriasActivity.this, s, Toast.LENGTH_LONG).show();}

    private void crudSubcategoriaIntent() {startActivity(new Intent(SubCategoriasActivity.this, CrudSubCategoriaActivity.class));}

    @Override
    public void onBackPressed(){startActivity(new Intent(SubCategoriasActivity.this, CategoriasActivity.class));}
}

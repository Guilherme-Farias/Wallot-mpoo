package com.ufrpe.bsi.mpoo.wallotapp.categoria.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio.CategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.ConfiguracaoActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.gui.SubCategoriasActivity;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;

public class CategoriasActivity extends AppCompatActivity {
    ListView listViewCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        final Usuario usuario = SessaoUsuario.instance.getUsuario();

        listViewCategorias = (ListView) findViewById(R.id.list_categorias);
        listarCategorias(usuario.getId());
        listViewCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = ((Categoria) parent.getAdapter().getItem(position));
                SessaoCategoria.instance.setCategoria(categoria);
                subCategoriaIntent();
            }
        });
    }
    private void subCategoriaIntent(){
        startActivity(new Intent(CategoriasActivity.this, SubCategoriasActivity.class));
    }

    private void listarCategorias(long idUsuario) {
        CategoriaServices categoriaServices = new CategoriaServices();
        ArrayList<Categoria> categorias = categoriaServices.listarCategorias(idUsuario);
        ArrayAdapter adapter = new ArrayAdapter<Categoria>(CategoriasActivity.this,android.R.layout.simple_list_item_1,categorias);
        listViewCategorias.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(CategoriasActivity.this, ConfiguracaoActivity.class));
    }
}

package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.gui.CategoriasActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio.SubCategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;

public class SubCategoriasActivity extends AppCompatActivity {
    ListView listViewSubCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorias);
        final  Categoria categoria = SessaoCategoria.instance.getCategoria();
        final Usuario usuario = SessaoUsuario.instance.getUsuario();
        getSupportActionBar().setTitle("Subcategorias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewSubCategoria = (ListView) findViewById(R.id.list_sub_categoria);
        listarSubCategorias(usuario.getId(), categoria.getId());
        listViewSubCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubCategoria subCategoria = ((SubCategoria) parent.getAdapter().getItem(position));
                Toast.makeText(SubCategoriasActivity.this, subCategoria.getNome(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void listarSubCategorias(long idUsuario, long idCategoria) {
        SubCategoriaServices subCategoriaServices = new SubCategoriaServices();
        ArrayList<SubCategoria> subCategorias = subCategoriaServices.listarSubCategorias(idUsuario, idCategoria);
        ArrayAdapter adapter = new ArrayAdapter<SubCategoria>(SubCategoriasActivity.this,android.R.layout.simple_list_item_1,subCategorias);
        listViewSubCategoria.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(SubCategoriasActivity.this, CategoriasActivity.class));
    }
}

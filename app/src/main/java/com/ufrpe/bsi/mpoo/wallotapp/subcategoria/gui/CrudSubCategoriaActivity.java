package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio.CategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoSubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio.SubCategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.ArrayList;

public class CrudSubCategoriaActivity extends AppCompatActivity {
    Spinner spnCategoriaPai;
    EditText editNome;
    Button btnSalvar, btnExcluir;
    SubCategoria subCategoria;
    Usuario usuario;
    Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_sub_categoria);
        usuario = SessaoUsuario.instance.getUsuario();
        categoria = SessaoCategoria.instance.getCategoria();
        subCategoria = SessaoSubCategoria.instance.getSubCategoria();

        editNome = findViewById(R.id.edit_subcategoria_crud);
        btnSalvar = findViewById(R.id.button_salvar_subcategoria);
        btnExcluir = findViewById(R.id.button_excluir_subcategoria);
        getSupportActionBar().setTitle("Criar Subcategoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (subCategoria != null){
            editNome.setText(subCategoria.getNome());
            btnExcluir.setClickable(true);
            btnExcluir.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Editar Subcategoria");
        }
        /// certo

        final ArrayList<Categoria> categorias = new CategoriaServices().listarCategorias(usuario.getId());
        final ArrayAdapter adapterCategoriaPai = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        adapterCategoriaPai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoriaPai = findViewById(R.id.spinner_categoria_subcategoria);
        spnCategoriaPai.setAdapter(adapterCategoriaPai);
        spnCategoriaPai.setSelection(getIndex(spnCategoriaPai, categoria.getNome()));
        spnCategoriaPai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnCategoriaPai.setSelection(position,true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarSubcategoria();
            }
        });
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirSubcategoria();
            }
        });
    }

    private void excluirSubcategoria() {
        new SubCategoriaServices().deletarSubCategoria(subCategoria);
        Toast.makeText(CrudSubCategoriaActivity.this, "Subcategoria Deleteda com sucesso", Toast.LENGTH_LONG).show();
        startActivity(new Intent(CrudSubCategoriaActivity.this, SubCategoriasActivity.class));
    }

    private void salvarSubcategoria() {
        if(ValidateField()){
            Categoria categoria = (Categoria) spnCategoriaPai.getSelectedItem();
            Log.d("categoria", categoria.toString());
            if(subCategoria == null){
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.setNome(editNome.getText().toString());
                subCategoria.setIcone(categoria.getIcone());
                subCategoria.setFkCategoria(categoria.getId());
                subCategoria.setFkUsuario(usuario.getId());
                new SubCategoriaServices().cadastrar(subCategoria);
                Toast.makeText(CrudSubCategoriaActivity.this, "Subcategoria criada com sucesso", Toast.LENGTH_LONG).show();
                startActivity(new Intent(CrudSubCategoriaActivity.this, SubCategoriasActivity.class));
            } else{
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.setNome(editNome.getText().toString());
                subCategoria.setIcone(categoria.getIcone());
                subCategoria.setFkCategoria(categoria.getId());
                new SubCategoriaServices().alterarDados(subCategoria);
                Toast.makeText(CrudSubCategoriaActivity.this, "Subcategoria Editada com sucesso", Toast.LENGTH_LONG).show();
                startActivity(new Intent(CrudSubCategoriaActivity.this, SubCategoriasActivity.class));
                //vai no item dele e altera os dados(icone/categoria/subcategoria)
                //vai na tabela transacao e altera todos oq que tiver o id dele para sem suBcategoria
            }
        }
    }

    private boolean ValidateField() {
        boolean res = true;
        editNome.setError(null);
        View focusView = null;
        if(new SubCategoriaServices().nomeCategoriaExist(usuario.getId(), editNome.getText().toString())!= null) {
            //se criando
            if (subCategoria == null){
                editNome.setError("Nome já existente");
                focusView = editNome;
                res = false;
            }
            //se editando
            else if (!subCategoria.getNome().equals(editNome.getText().toString())){
                editNome.setError("Nome já existente");
                focusView = editNome;
                res = false;
            }

        }
        return res;
    }

    @Override
    public void onBackPressed() {
        SessaoSubCategoria.instance.reset();
        startActivity(new Intent(CrudSubCategoriaActivity.this, SubCategoriasActivity.class));
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return -1;
    }
}

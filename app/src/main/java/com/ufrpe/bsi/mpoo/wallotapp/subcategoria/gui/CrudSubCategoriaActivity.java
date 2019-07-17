package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private Spinner spnCategoriaPai;
    private EditText editNome;
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private Categoria categoria = SessaoCategoria.instance.getCategoria();
    private SubCategoria subCategoria = SessaoSubCategoria.instance.getSubCategoria();
    private CategoriaServices categoriaServices = new CategoriaServices();
    private SubCategoriaServices subCategoriaServices = new SubCategoriaServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnSalvar;
        Button btnExcluir;
        setContentView(R.layout.activity_crud_sub_categoria);
        getSupportActionBar().setTitle("Criar Subcategoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //pega itens do layout
        editNome = findViewById(R.id.edit_subcategoria_crud);
        btnSalvar = findViewById(R.id.button_salvar_subcategoria);
        btnExcluir = findViewById(R.id.button_excluir_subcategoria);

        //pre seta alguns dados se for para editar conta
        if (subCategoria != null){
            editNome.setText(subCategoria.getNome());
            btnExcluir.setClickable(true);
            btnExcluir.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Editar Subcategoria");
        }

        //cria spinner
        ArrayList<Categoria> categorias = categoriaServices.listarCategorias(usuario.getId());
        ArrayAdapter adapterCategoriaPai = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        adapterCategoriaPai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoriaPai = findViewById(R.id.spinner_categoria_subcategoria);
        spnCategoriaPai.setAdapter(adapterCategoriaPai);

        //pre seta a categoria pai
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

        //começa o processo de salvar subcategoria
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarSubcategoria();
            }
        });

        //comeca o processo de excluir subcategoria
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaAlertDialog();
            }
        });
    }

    //cria o alerta para ver se tem certeza que deseja excluir
    private void criaAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CrudSubCategoriaActivity.this);
        builder.setMessage("Você tem certeza que deseja excluir a subcategoria?")
                .setCancelable(false)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {excluirSubcategoria();}
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {dialog.cancel();}
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //exclui a subcategoria e vai par a lista de subcategorias
    private void excluirSubcategoria() {
        subCategoriaServices.deletarSubCategoria(subCategoria);
        subCategoriasIntent();
        showToast("Subcategoria deletada com sucesso com sucesso");
    }

    //vai para lista de subcategorias
    private void subCategoriasIntent() { startActivity(new Intent(CrudSubCategoriaActivity.this, SubCategoriasActivity.class));}

    //mostra um toast
    private void showToast(String s) {Toast.makeText(CrudSubCategoriaActivity.this, s, Toast.LENGTH_LONG).show();}

    //salva subcategoria
    private void salvarSubcategoria() {
        if(validateField()){
            Categoria categoria = (Categoria) spnCategoriaPai.getSelectedItem();
            SubCategoria subCategoriaEditada = criaSubCategoria(categoria);
            if(subCategoria == null){
                subCategoriaServices.cadastrar(subCategoriaEditada);
                showToast("Subcategoria criada com sucesso");
            } else{
                subCategoriaServices.alterarDados(subCategoriaEditada);
                showToast("Subcategoria Editada com sucesso");
            }
            subCategoriasIntent();
        }
    }

    //controi objeto subcategoria
    private SubCategoria criaSubCategoria(Categoria categoria) {
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNome(editNome.getText().toString());
        subCategoria.setIcone(categoria.getIcone());
        subCategoria.setCategoria(categoria);
        subCategoria.setUsuario(usuario);
        return subCategoria;
    }

    //valida todos os campos
    private boolean validateField() {
        //pega valores
        String nome = editNome.getText().toString();

        //reseta erros
        boolean res = true;
        editNome.setError(null);
        View focusView = null;

        //verifica se o nome já existe
        if(nomeSubCategoriaExist(nome)) {
            //se criando
            if (subCategoria == null){
                editNome.setError("Nome já existente");
                focusView = editNome;
                res = false;
            }
            //se editando
            else if (!subCategoria.getNome().equals(nome)){
                editNome.setError("Nome já existente");
                focusView = editNome;
                res = false;
            }
        }
        return res;
    }

    //verifica se o nome da subcategoria já existe
    private boolean nomeSubCategoriaExist(String nome) {return subCategoriaServices.nomeCategoriaExist(usuario.getId(), nome)!= null;}

    //seta a cetegoria no spinner
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){return i;}
        }
        return -1;
    }

    //reseta subcategoria e volta para tela de subcategorias
    @Override
    public void onBackPressed() {
        SessaoSubCategoria.instance.reset();
        subCategoriasIntent();
    }
}

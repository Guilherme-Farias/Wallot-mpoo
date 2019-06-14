package com.ufrpe.bsi.mpoo.wallotapp.transacao.gui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio.CategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.MainActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.DatePickerFragments;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio.SubCategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransacaoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText editDescricao, editSaldo, editParcelas;
    TextView textData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);
        final Usuario usuario = SessaoUsuario.instance.getUsuario();
        final Transacao transacao = SessaoTransacao.instance.getTransacao();

        editDescricao = findViewById(R.id.descricao_transacao);
        editSaldo = findViewById(R.id.saldo_transacao);
        editParcelas = findViewById(R.id.parcelas_transacao);


        //spin Contas
        ArrayList<Conta> contas = new ContaServices().listarContas(usuario.getId());
        ArrayAdapter adapterContas = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, contas);
        adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spnConta = findViewById(R.id.spinner_conta_transacao);
        spnConta.setAdapter(adapterContas);

        //spin Categoria
        ArrayList<Categoria> categorias = new CategoriaServices().listarCategorias(usuario.getId());
        ArrayAdapter adapterCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spnCategoria = findViewById(R.id.spinner_categoria_transacao);
        spnCategoria.setAdapter(adapterCategorias);
        spnCategoria.getSelectedItem();
        spnCategoria.



        //spin SubCategoria
        /*ArrayList<SubCategoria> subCategorias = new SubCategoriaServices().listarSubCategorias(usuario.getId());
        ArrayAdapter adapterSubCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, subCategorias);
        adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spnSubCategorias = findViewById(R.id.spinner_sub_categoria_transacao);
        spnSubCategorias.setAdapter(adapterSubCategorias);*/

        //spin Tipo Transacao
        ArrayList<TipoTransacao> tipoTransacaos = new TransacaoServices().listarSubCategorias(usuario.getId());
        ArrayAdapter adapterSubCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoTransacaos);
        adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spnSubCategorias = findViewById(R.id.spinner_sub_categoria_transacao);
        spnSubCategorias.setAdapter(adapterSubCategorias);




        //data
        textData = findViewById(R.id.str_data_transacao);
        textData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.add(Calendar.DATE, 0);
        Date date = c.getTime();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        String datestr = formatdate.format(date);
        TextView strData = findViewById(R.id.str_data_transacao);
        strData.setText(datestr);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TransacaoActivity.this, MainActivity.class));
    }

}

package com.ufrpe.bsi.mpoo.wallotapp.transacao.gui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio.CategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.gui.MainActivity;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.DatePickerFragments;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoConta;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoSubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio.SubCategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio.TransacaoServices;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransacaoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText editDescricao, editSaldo, editParcelas;
    TextView textData;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);
        final Usuario usuario = SessaoUsuario.instance.getUsuario();
        final Transacao transacao = SessaoTransacao.instance.getTransacao();


        editDescricao = findViewById(R.id.descricao_transacao);
        editSaldo = findViewById(R.id.saldo_transacao);
        editParcelas = findViewById(R.id.parcelas_transacao);
        final Spinner spnConta = findViewById(R.id.spinner_conta_transacao);
        textData = findViewById(R.id.str_data_transacao);
        textData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        final Spinner spnCategoria = findViewById(R.id.spinner_categoria_transacao);
        final Spinner spnTipoTransacao = findViewById(R.id.spinner_tipo_transacao);


        //spin Contas
        ArrayList<Conta> contas = new ContaServices().listarContas(usuario.getId());
        ArrayAdapter adapterContas = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, contas);
        adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnConta.setAdapter(adapterContas);
        spnConta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnConta.setSelection(position, true);
                SessaoConta.instance.setConta((Conta) spnConta.getSelectedItem());
                SessaoTransacao.instance.getTransacao().setFkConta(((Conta)spnConta.getSelectedItem()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spin Categoria
        ArrayList<Categoria> categorias = new CategoriaServices().listarAllCategorias(usuario.getId());
        ArrayAdapter adapterCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoria.setAdapter(adapterCategorias);
        spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnCategoria.setSelection(position,true);
                SessaoCategoria.instance.setCategoria((Categoria) spnCategoria.getSelectedItem());
                SessaoTransacao.instance.getTransacao().setFkCategoria(((Categoria)spnCategoria.getSelectedItem()).getId());
                atualizarSubCategoria(usuario);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spin SubCategoria
        atualizarSubCategoria(usuario);
        //spin Tipo Transacao
        ArrayList<TipoTransacao> tipoTransacaos = new TransacaoServices().listarTiposConta();
        ArrayAdapter adapterTipoTransacao = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoTransacaos);
        adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoTransacao.setAdapter(adapterTipoTransacao);
        spnTipoTransacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnTipoTransacao.setSelection(position,true);
                SessaoTransacao.instance.getTransacao().setTipoTransacao((TipoTransacao)spnTipoTransacao.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //data
        textData = findViewById(R.id.str_data_transacao);
        textData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnSalvar = findViewById(R.id.button_salvar_transacao);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarTransacao();
            }
        });







    }

    private void salvarTransacao() {
        Transacao transacao = SessaoTransacao.instance.getTransacao();
        transacao.setTitulo(editDescricao.getText().toString());
        transacao.setValor(new BigDecimal(editSaldo.getText().toString()));
        transacao.setQntParcelas(Integer.parseInt(editParcelas.getText().toString()));
        transacao.setFkUsuario(SessaoUsuario.instance.getUsuario().getId());
        //System.out.println(transacao);
        String data = textData.getText().toString();
        TransacaoServices transacaoServices = new TransacaoServices();
        transacaoServices.cadastrarTransacao(transacao, data);
        Toast.makeText(TransacaoActivity.this, "Feito com sucesso", Toast.LENGTH_LONG).show();



    }

    private void atualizarSubCategoria(Usuario usuario) {
        final Spinner spnSubCategorias = findViewById(R.id.spinner_sub_categoria_transacao);
        ArrayList<SubCategoria> subCategorias = new SubCategoriaServices().listarAllSubCategorias(usuario.getId(), SessaoCategoria.instance.getCategoria().getId());
        ArrayAdapter adapterSubCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, subCategorias);
        adapterSubCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSubCategorias.setAdapter(adapterSubCategorias);
        spnSubCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnSubCategorias.setSelection(position,true);
                SessaoSubCategoria.instance.setSubCategoria((SubCategoria) spnSubCategorias.getSelectedItem());
                SessaoTransacao.instance.getTransacao().setFkSubCategoria(((SubCategoria)spnSubCategorias.getSelectedItem()).getId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        //System.out.println(date);
        SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy");
        String datestr = formatdate.format(date);
        TextView strData = findViewById(R.id.str_data_transacao);
        strData.setText(datestr);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TransacaoActivity.this, MainActivity.class));
    }

}

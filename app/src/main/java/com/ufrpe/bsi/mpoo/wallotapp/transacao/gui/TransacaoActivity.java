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
import android.widget.ImageView;
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
    private EditText editDescricao, editSaldo, editParcelas;
    private TextView textData;
    private ImageView imageTipo;
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private ContaServices contaServices = new ContaServices();
    private Spinner spnConta;
    private Spinner spnCategoria;
    private Spinner spnTipoTransacao;
    private Spinner spnSubCategorias;
    private SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy");
    private CategoriaServices categoriaServices = new CategoriaServices();
    private SubCategoriaServices subCategoriaServices = new SubCategoriaServices();
    private TransacaoServices transacaoServices = new TransacaoServices();
    private Transacao transacao = new Transacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);
        getSupportActionBar().setTitle("Transação");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //pega os dados do layout
        editDescricao = findViewById(R.id.descricao_transacao);
        editSaldo = findViewById(R.id.saldo_transacao);
        editParcelas = findViewById(R.id.parcelas_transacao);
        spnConta = findViewById(R.id.spinner_conta_transacao);
        textData = findViewById(R.id.str_data_transacao);
        imageTipo = findViewById(R.id.tipo_transacao_image);
        spnCategoria = findViewById(R.id.spinner_categoria_transacao);
        spnTipoTransacao = findViewById(R.id.spinner_tipo_transacao);
        Button btnSalvar = findViewById(R.id.button_salvar_transacao);
        spnSubCategorias = findViewById(R.id.spinner_sub_categoria_transacao);

        //seta a data atual
        textData.setText(formatdate.format(new Date()));

        //cria o spinner das contas ativas
        ArrayList<Conta> contas = contaServices.listarContasAtivas(usuario.getId());
        ArrayAdapter adapterContas = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, contas);
        adapterContas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnConta.setAdapter(adapterContas);
        spnConta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnConta.setSelection(position, true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //cria o spinner categorias
        ArrayList<Categoria> categorias = categoriaServices.listarAllCategorias(usuario.getId());
        ArrayAdapter adapterCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoria.setAdapter(adapterCategorias);
        spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnCategoria.setSelection(position,true);
                atualizarSubCategoria();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //cria spin tipo de transacao
        ArrayList<TipoTransacao> tipoTransacaos = transacaoServices.listarTiposTransacao();
        ArrayAdapter adapterTipoTransacao = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoTransacaos);
        adapterTipoTransacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoTransacao.setAdapter(adapterTipoTransacao);
        spnTipoTransacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnTipoTransacao.setSelection(position,true);
                transacao.setTipoTransacao((TipoTransacao) spnTipoTransacao.getSelectedItem());
                trocarImagemPorTipoTransacao();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Abre o dialog de datas para o usuario setar
        textData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //salva a transacao
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarTransacao();
            }
        });

        //atualiza a imagem de acordo com a escolha do usuario
        transacao.setTipoTransacao((TipoTransacao) getIntent().getExtras().get("TipoTransacao"));
        trocarImagemPorTipoTransacao();
        getIntent().getExtras().clear();

        //atualiza o spinner de subcategoria
        atualizarSubCategoria();
    }


    //começa o processo de salvar transacao(não valida dados(ainda))
    private void salvarTransacao() {
        transacao = criaTransacao();
        String data = textData.getText().toString();
        SessaoTransacao.instance.setTransacao(transacao);
        transacaoServices.cadastrarTransacao(data);
        showToast("Transação Feita com sucesso");
        inicioIntent();
    }

    //função padrao de toast
    private void showToast(String s) {Toast.makeText(TransacaoActivity.this, s, Toast.LENGTH_LONG).show();}

    //controi um objeto transacao com os dados setados
    private Transacao criaTransacao() {
        transacao.setTitulo(editDescricao.getText().toString());
        transacao.setValor(new BigDecimal(editSaldo.getText().toString()));
        transacao.setQntParcelas(Integer.parseInt(editParcelas.getText().toString()));
        transacao.setTipoTransacao((TipoTransacao) spnTipoTransacao.getSelectedItem());
        transacao.setCategoria(((Categoria) spnCategoria.getSelectedItem()));
        transacao.setSubCategoria(((SubCategoria) spnSubCategorias.getSelectedItem()));
        transacao.setConta(((Conta) spnConta.getSelectedItem()));
        transacao.setUsuario(usuario);
        return transacao;
    }

    //atualiza o spinner de subcategoria
    private void atualizarSubCategoria() {
        ArrayList<SubCategoria> subCategorias = subCategoriaServices.listarAllSubCategorias(usuario.getId(), ((Categoria) spnCategoria.getSelectedItem()).getId());
        ArrayAdapter adapterSubCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, subCategorias);
        adapterSubCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSubCategorias.setAdapter(adapterSubCategorias);
        spnSubCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnSubCategorias.setSelection(position,true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    //pega a data e seta
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.add(Calendar.DATE, 0);
        Date date = c.getTime();
        textData.setText(formatdate.format(date));
    }

    //vai para o inicio
    @Override
    public void onBackPressed() {
        inicioIntent();
    }

    //vai para o inicio
    private void inicioIntent() {
        startActivity(new Intent(TransacaoActivity.this, MainActivity.class));
    }

    //troca a imagem do topo da página
    public void trocarImagemPorTipoTransacao(){
        if (transacao.getTipoTransacao() == TipoTransacao.RECEITA){
            spnTipoTransacao.setSelection(0);
            imageTipo.setBackgroundResource(R.drawable.background_nova_receita);
        } else if (transacao.getTipoTransacao() == TipoTransacao.DESPESA){
            spnTipoTransacao.setSelection(1);
            imageTipo.setBackgroundResource(R.drawable.background_nova_despesa);
        } else {
            spnTipoTransacao.setSelection(0);
            imageTipo.setBackgroundResource(R.drawable.background_nova_receita);
            showToast("Opção ainda indisponivel");
        }
    }
}

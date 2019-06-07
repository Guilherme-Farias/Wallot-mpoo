package com.ufrpe.wallot_app.infra.gui;

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
import android.widget.ListView;
import android.widget.TextView;

import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.conta.negocio.ContaServices;
import com.ufrpe.wallot_app.infra.DatePickerFragments;
import com.ufrpe.wallot_app.infra.Sessao;
import com.ufrpe.wallot_app.infra.SessaoConta;
import com.ufrpe.wallot_app.usuario.dominio.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
   /* //alterar dados do user
    EditText editNome, editEmail, editSenha;
    Button editarSalvar;*/
    //alterar dados conta
    EditText editNomeConta, editSaldoConta;
    ListView listViewContas;
    Button editarConta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        final Usuario usuario = Sessao.instance.getUsuario();
        /*////////////////////////////////////////////////////////EDITAR USUARIO
        editNome = (EditText) findViewById(R.id.nome_usuario_id);
        editEmail = (EditText) findViewById(R.id.email_usuario_id);
        editSenha = (EditText) findViewById(R.id.senha_usuario_id);
        editarSalvar = (Button) findViewById(R.id.buttonEditarSalvar);
        editNome.setHint(usuario.getNome());
        editEmail.setHint(usuario.getEmail());
        editSenha.setHint("***********");
        editarSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editaSalvaDados();
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////FIM editar user*/


        /*///////////////////////////////LISTAR E EDITAR CARTEIRA
        editNomeConta = (EditText) findViewById(R.id.nome_conta);
        editSaldoConta = (EditText) findViewById(R.id.saldo_conta);
        editSaldoConta.setHint("R$0,00");
        /*editSaldoConta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            private  String current = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(current)) {
                    Locale myLocale = new Locale("pt", "BR");
                    //Nesse bloco ele monta a maskara para money
                    editSaldoConta.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[R$,.]", "");

                    BigDecimal parsed = new BigDecimal(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance(myLocale).format((parsed.divide(new BigDecimal("100"))));
                    current = formatted;
                    editSaldoConta.setText(formatted);
                    editSaldoConta.setSelection(formatted.length());

                    editSaldoConta.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        listViewContas = (ListView) findViewById(R.id.list_contas);
        editarConta = findViewById(R.id.buttonEditarConta);
        listarContas(usuario.getId());
        listViewContas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selecionaConta(position, usuario);

            }
        });

        /*editarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    *///editarSalvarConta(usuario);
         /*
                } catch (Exception e){
                    Toast.makeText(WarActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });*/
        ///////////////////////////////////////////////////////////////////////////////////////////fim editar conta

        ////////////////////////////////////////////////////////////////////////////////////////////INICIO pagamento
        EditText descricao, valorpagamento, categoria, subatogoria;
        descricao = findViewById(R.id.editDescricao);
        valorpagamento = findViewById(R.id.editValorPagamento);
        categoria = findViewById(R.id.editCategoria);
        subatogoria = findViewById(R.id.editSubCategoria);
        Button buttonDate = findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        String data = findViewById(R.id.strData).toString();
        //Button




    }

    /*private boolean validateFieldsConta(String nomeConta, String saldoConta) {
        boolean res = true;
        editSaldoConta.setError(null);
        editNomeConta.setError(null);
        View focusView = null;
        if (!validateLenght(nomeConta, 2)){
            editNomeConta.setError("Nome inválido, minimo de 2");
            focusView = editNomeConta;
            res = false;
        }
        return res;
    }*/

    private void selecionaConta(int position, Usuario usuario) {
        String conteudo = (String) listViewContas.getItemAtPosition(position);
        String nomeContaSelecionada = conteudo.substring(0,conteudo.indexOf("\n"));
        ContaServices contaServices = new ContaServices();
        Conta conta = contaServices.getConta(usuario.getId(), nomeContaSelecionada);
        editNomeConta.setText(conta.getNome());
        editSaldoConta.setText(conta.getSaldo().toString());
        SessaoConta.instance.setConta(conta);
    }

    private void listarContas(long idUsuario) {
        ContaServices contaServices = new ContaServices();

        ArrayList<Conta> contas = contaServices.listarContas(idUsuario);
        ArrayList<String> contasStr = new ArrayList<String>();

        ArrayAdapter adapter = new ArrayAdapter<String>(WarActivity.this,android.R.layout.simple_list_item_1,contasStr);
        listViewContas.setAdapter(adapter);
        for(Conta conta: contas){
            contasStr.add(conta.getNome() + "\n" +
                    "Saldo: R$" + conta.getSaldo().toString());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WarActivity.this, MainActivity.class));
    }

    /*private void editaSalvaDados() {
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        if(validateFieldsUsuario(nome, email, senha)){
            UsuarioServices usuarioServices = new UsuarioServices();
            Usuario userEditado = new Usuario(nome, email, senha);
            if(usuarioServices.emailCadastrado(userEditado) == null){
                usuarioServices.alterarDados(userEditado);
                startActivity(new Intent(WarActivity.this, WarActivity.class));
                Toast.makeText(WarActivity.this, "Dados editados", Toast.LENGTH_LONG).show();
                //this.recreate();
            } else{
                Toast.makeText(WarActivity.this, "Email já cadastrado", Toast.LENGTH_LONG).show();
            }
        }

    }*/

    /*private void editarSalvarConta(Usuario usuario){
        String nomeConta = editNomeConta.getText().toString();
        String saldoConta = editSaldoConta.getText().toString();
        if (validateFieldsConta(nomeConta, saldoConta)){
            ContaServices contaServices = new ContaServices();
            Conta contaEditada = new Conta(nomeConta, saldoConta);
            if (contaEditada.getNome().equals(SessaoConta.instance.getConta().getNome()) || contaServices.getConta(usuario.getId(), contaEditada.getNome()) == null){
                contaServices.alterarDados(contaEditada);
                startActivity(new Intent(WarActivity.this, WarActivity.class));
                Toast.makeText(WarActivity.this, "Dados salvos com sucesso", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(WarActivity.this, "Conta já cadastrada", Toast.LENGTH_LONG).show();
            }
        }
    }*/






    /*private boolean validateFieldsUsuario(String nome, String email, String senha){
        boolean res = true;
        editEmail.setError(null);
        editSenha.setError(null);
        editSenha.setError(null);
        View focusView = null;
        if (!validateName(nome) ){
            editNome.setError("Nome inválido, não aceito caracteres especiais");
            focusView = editNome;
            res = false;
        }
        if(!validateLenght(senha, 5) && !senha.isEmpty()) {
            editSenha.setError("Senha inválida(menor que 5 caracteres");
            focusView = editSenha;
            res = false;
        }
        if(!validateEmail(email) && !email.isEmpty()){
            editEmail.setError("Email inválido");
            focusView = editEmail;
            res = false;
        }
        return res;
    }

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateLenght(String str, int tam) {
        return str.length() > tam;
    }

    private boolean validateName (String nome) {
        return nome.matches("^[a-zA-ZÁÂÃÀÇÉÊÍÓÔÕÚÜáâãàçéêíóôõúü ]*$");
    }*/

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.add(Calendar.DATE, 0);
        Date date = c.getTime();
        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");
        String datestr = formatdate.format(date);
        TextView strData = findViewById(R.id.strData);
        strData.setText(datestr);
    }
}

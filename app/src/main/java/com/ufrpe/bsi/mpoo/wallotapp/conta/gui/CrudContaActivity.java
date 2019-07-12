package com.ufrpe.bsi.mpoo.wallotapp.conta.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoConta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoEstadoConta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoConta;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CrudContaActivity extends AppCompatActivity {

    //informação do layout
    Spinner spinnerTipoConta;
    EditText editNome,editSaldo;
    RadioGroup radioTipoEstadoConta;
    RadioButton ativoButton, inativoButton;
    Button buttonSalvar;
    //informação de objetos
    Conta conta = SessaoConta.instance.getConta();
    Usuario usuario = SessaoUsuario.instance.getUsuario();
    ContaServices contaServices = new ContaServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_conta);
        getSupportActionBar().setTitle("Criar Conta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // encontra os itens
        editNome = findViewById(R.id.edit_conta_salvar);
        editSaldo = findViewById(R.id.edit_saldo_salvar);
        spinnerTipoConta = findViewById(R.id.spinner_tipo_conta);
        radioTipoEstadoConta = findViewById(R.id.radio_tipo_estado_conta);
        ativoButton = findViewById(R.id.radio_ativo);
        inativoButton = findViewById(R.id.radio_inativo);
        buttonSalvar = findViewById(R.id.button_salvar_conta);

        //cria o Spinner dos tipos de conta e coloca no spinner
        ArrayList<TipoConta> tipoContas = pegarTiposConta();
        ArrayAdapter adapterTipocontas = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoContas);
        adapterTipocontas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoConta.setAdapter(adapterTipocontas);
        //pega o item selecionado
        spinnerTipoConta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerTipoConta.setSelection(position,true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //salva as informações da conta
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarConta(usuario);
            }
        });

        //se ele estiver editando pre seleciona os itens de acordo com o objeto na sessão
        if(conta != null){
            getSupportActionBar().setTitle("Editar Conta");
            editNome.setText(conta.getNome());
            editSaldo.setText(conta.getSaldo().toString());
            spinnerTipoConta.setSelection(conta.getTipoConta().ordinal());
            checarTipoEstadoButton();
        }
    }

    //checa o botão se a conta já não for null
    private void checarTipoEstadoButton() {
        if(conta.getTipoEstadoConta().ordinal() == 0 || conta.getId() == 0){
            ativoButton.setChecked(true);
        } else {
            inativoButton.setChecked(true);
        }
    }

    //pega as contas do usuario
    private ArrayList<TipoConta> pegarTiposConta() {
        return contaServices.listarTiposConta();
    }

    //ve se esta salvando ou editando e salva a conta do usuario
    private void salvarConta(Usuario usuario) {
        if (validateFieldsConta()){
            Conta contaEditada = controiConta();
            boolean existContaNome = existContaNome(contaEditada);
            if (conta == null && existContaNome){
                contaServices.cadastrarConta(contaEditada);
                showToast("Conta criada com sucesso");
            } else if (existContaNome || mesmoNome(contaEditada)){
                contaServices.alterarDados(contaEditada);
                showToast("Conta editada com sucesso");
            } else {
                showToast("Nome da conta já cadastrada");
                return;
            }contasActivityIntent();
        }
    }

    //controi a conta editada
    private Conta controiConta() {
        Conta novaConta = new Conta();
        novaConta.setUsuario(usuario);
        novaConta.setNome(editNome.getText().toString());
        novaConta.setSaldo(new BigDecimal(editSaldo.getText().toString()));
        novaConta.setTipoConta((TipoConta)spinnerTipoConta.getSelectedItem());
        int idTipoEstadoConta = getValorMarcador();
        novaConta.setTipoEstadoConta(TipoEstadoConta.values()[idTipoEstadoConta]);
        return novaConta;
    }

    //pega valor do radioButton
    private int getValorMarcador() {
        int radioId = radioTipoEstadoConta.getCheckedRadioButtonId();
        View radioButton = radioTipoEstadoConta.findViewById(radioId);
        return radioTipoEstadoConta.indexOfChild(radioButton);
    }

    //check botão
    public int checkButton(View v){return radioTipoEstadoConta.getCheckedRadioButtonId();}

    //valida campos
    private boolean validateFieldsConta() {
        //pega valores texto
        String nomeConta = editNome.getText().toString();

        //reseta erros
        boolean res = true;
        editNome.setError(null);
        View focusView = null;

        //verifica tamanho minimo
        if (!validateLenght(nomeConta)){
            editNome.setError("Nome inválido, minimo de 2 caracteres");
            focusView = editNome;
            res = false;
        }
        //valida se pode inativar
        if (valorRadioIsInativo(getValorMarcador())) {
            //se estiver criando conta
            if (conta == null){
                showToast("Ao criar uma conta ela deve estar Ativa");
                ativoButton.setChecked(true);
                res = false;
            }
            //se estiver editando conta
            else if (existContaAtiva(usuario) == null){
                showToast("Deve ao menos possuir uma conta ativa");
                ativoButton.setChecked(true);
                res = false;
            }
        }
        return res;
    }

    //valida se o radioButton esta inativo
    private boolean valorRadioIsInativo(int marcado) {return TipoEstadoConta.values()[marcado].toString().equals("Inativo");}

    //valida se exite alguma conta ativa
    private Conta existContaAtiva(Usuario usuario) {return contaServices.existContaAtiva(usuario.getId(), conta.getId());}

    //valida o tmanho do nome da conta
    private boolean validateLenght(String str) {return str.length() >= 2;}

    //mostra o Toast
    private void showToast(String s) {Toast.makeText(CrudContaActivity.this, s, Toast.LENGTH_LONG).show();}

    //verifica se o nome não foi editado
    private boolean mesmoNome(Conta contaEditada) {return conta.getNome().equals(contaEditada.getNome());}

    //veridica se o nome é igual a outra conta já criada pelo usuario
    private boolean existContaNome(Conta contaEditada) {return contaServices.getConta(usuario.getId(),contaEditada.getNome()) == null;}

    //volta para tela de contas
    private void contasActivityIntent() {startActivity(new Intent(CrudContaActivity.this, ContasActivity.class));}

    //volta para todas as contas e reseta a sessao da conta
    @Override
    public void onBackPressed() {
        SessaoConta.instance.reset();
        contasActivityIntent();
    }
}

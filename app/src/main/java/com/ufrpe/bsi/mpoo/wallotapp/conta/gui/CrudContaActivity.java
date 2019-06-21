package com.ufrpe.bsi.mpoo.wallotapp.conta.gui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    Spinner spinnerTipoConta;
    EditText editNome,editSaldo;
    RadioGroup radioTipoEstadoConta;
    RadioButton ativoButton, inativoButton;
    Button buttonSalvar;
    final Conta conta = SessaoConta.instance.getConta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_conta);
        final Usuario usuario = SessaoUsuario.instance.getUsuario();
        editNome = findViewById(R.id.edit_conta_salvar);
        editSaldo = findViewById(R.id.edit_saldo_salvar);
        ArrayList<TipoConta> tipoContas = new ContaServices().listarTiposConta();
        ArrayAdapter adapterTipocontas = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoContas);
        adapterTipocontas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoConta = findViewById(R.id.spinner_tipo_conta);
        spinnerTipoConta.setAdapter(adapterTipocontas);
        spinnerTipoConta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerTipoConta.setSelection(position,true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        radioTipoEstadoConta = findViewById(R.id.radio_tipo_estado_conta);
        ativoButton = findViewById(R.id.radio_ativo);
        inativoButton = findViewById(R.id.radio_inativo);
        checarTipoEstadoButton();
        buttonSalvar = findViewById(R.id.button_salvar_conta);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarConta(usuario);

            }
        });
        if(conta.getId() == 0){
            getSupportActionBar().setTitle("Criar Conta");
        } else {
            getSupportActionBar().setTitle("Editar Conta");
            editNome.setText(conta.getNome());
            editSaldo.setText(conta.getSaldo().toString());
            spinnerTipoConta.setSelection(conta.getTipoConta().ordinal());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void checarTipoEstadoButton() {
        if(conta.getTipoEstadoConta().ordinal() == 0 || conta.getId() == 0){
            ativoButton.setChecked(true);
        } else {
            inativoButton.setChecked(true);
        }
    }

    private ArrayList<TipoConta> pegarTiposConta() {
        ContaServices contaServices = new ContaServices();
        return contaServices.listarTiposConta();
    }

    private void salvarConta(Usuario usuario) {
        String nomeConta = editNome.getText().toString();
        String saldoConta = editSaldo.getText().toString();
        if (validateFieldsConta(nomeConta, usuario)){
            ContaServices contaServices = new ContaServices();
            Conta contaEditada = criaConta(usuario);
            if (contaEditada.getNome().equals(SessaoConta.instance.getConta().getNome()) || contaServices.getConta(usuario.getId(),contaEditada.getNome()) == null){
                if(conta.getId() == 0){
                    contaServices.cadastrarConta(contaEditada);
                    startActivity(new Intent(CrudContaActivity.this, ContasActivity.class));
                    Toast.makeText(CrudContaActivity.this, "Conta criada com sucesso", Toast.LENGTH_LONG).show();
                } else {
                    contaServices.alterarDados(contaEditada);
                    startActivity(new Intent(CrudContaActivity.this, CrudContaActivity.class));
                    Toast.makeText(CrudContaActivity.this, "Conta editada com sucesso", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CrudContaActivity.this, "Conta já cadastrada", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Conta criaConta(Usuario usuario) {
        Conta novaConta = new Conta();
        novaConta.setFkUsuario(usuario.getId());
        novaConta.setNome(editNome.getText().toString());
        novaConta.setSaldo(new BigDecimal(editSaldo.getText().toString()));
        novaConta.setTipoConta((TipoConta)spinnerTipoConta.getSelectedItem());
        Log.d("daleeCria",spinnerTipoConta.getSelectedItem().toString());
        int radioId = radioTipoEstadoConta.getCheckedRadioButtonId();
        View radioButton = radioTipoEstadoConta.findViewById(radioId);
        int idx = radioTipoEstadoConta.indexOfChild(radioButton);
        novaConta.setTipoEstadoConta(TipoEstadoConta.values()[idx]);

        return novaConta;
    }



    public int checkButton(View v){
        return radioTipoEstadoConta.getCheckedRadioButtonId();
    }

    private boolean validateFieldsConta(String nomeConta, Usuario usuario) {
        boolean res = true;
        editNome.setError(null);
        editSaldo.setError(null);
        String marcado = TipoEstadoConta.values()[radioTipoEstadoConta.indexOfChild(radioTipoEstadoConta.findViewById(radioTipoEstadoConta.getCheckedRadioButtonId()))].toString();

        View focusView = null;
        if (!validateLenght(nomeConta, 2)){
            editNome.setError("Nome inválido, minimo de 2");
            focusView = editNome;
            res = false;
        }
        if (conta.getId() == 0 && marcado == "Inativo"){
            Toast.makeText(CrudContaActivity.this, "Ao criar uma conta ela deve estar Ativa", Toast.LENGTH_LONG).show();
            ativoButton.setChecked(true);
            res = false;

        } else if( existContaAtiva(usuario) == null && marcado == "Inativo"){
            Toast.makeText(CrudContaActivity.this, "Deve ao menos possuir uma conta ativa", Toast.LENGTH_LONG).show();
            ativoButton.setChecked(true);
            res = false;
        }

        return res;
    }

    private Conta existContaAtiva(Usuario usuario) {
        return new ContaServices().existContaAtiva(usuario.getId(), conta.getId());
    }

    private boolean validateLenght(String str, int tam) {
        return str.length() >= tam;
    }

    @Override
    public void onBackPressed() {
        SessaoConta.instance.reset();
        startActivity(new Intent(CrudContaActivity.this, ContasActivity.class));
    }
}

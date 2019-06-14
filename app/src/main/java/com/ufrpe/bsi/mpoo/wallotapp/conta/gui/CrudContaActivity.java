package com.ufrpe.bsi.mpoo.wallotapp.conta.gui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    EditText editNome,editSaldo,editCor;
    RadioGroup radioTipoEstadoConta;
    Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_conta);
        final Usuario usuario = SessaoUsuario.instance.getUsuario();
        final Conta conta = SessaoConta.instance.getConta();
        editNome = findViewById(R.id.edit_conta_salvar);
        editSaldo = findViewById(R.id.edit_saldo_salvar);
        editCor = findViewById(R.id.edit_cor_salvar);
        editNome.setText(conta.getNome());
        editSaldo.setText(conta.getSaldo().toString());
        editCor.setText(conta.getCor());


        ArrayList<TipoConta> tipoContas = pegarTiposConta();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,tipoContas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoConta = findViewById(R.id.spinner_tipo_conta);
        spinnerTipoConta.setAdapter(adapter);
        radioTipoEstadoConta = findViewById(R.id.radio_tipo_estado_conta);
        buttonSalvar = findViewById(R.id.button_salvar_conta);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarConta(usuario);

            }
        });
    }

    private ArrayList<TipoConta> pegarTiposConta() {
        ContaServices contaServices = new ContaServices();
        return contaServices.listarTiposConta();
    }

    private void salvarConta(Usuario usuario) {
        String nomeConta = editNome.getText().toString();
        String saldoConta = editSaldo.getText().toString();
        String corConta = editCor.getText().toString();
        if (validateFieldsConta(nomeConta)){
            ContaServices contaServices = new ContaServices();
            Conta contaEditada = criaConta();
            if (contaEditada.getNome().equals(SessaoConta.instance.getConta().getNome()) || contaServices.getConta(usuario.getId(),contaEditada.getNome()) == null){
                contaServices.alterarDados(contaEditada);
                startActivity(new Intent(CrudContaActivity.this, CrudContaActivity.class));
                Toast.makeText(CrudContaActivity.this, "Dados salvos com sucesso", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CrudContaActivity.this, "Conta já cadastrada", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Conta criaConta() {
        Conta novaConta = new Conta();
        novaConta.setNome(editNome.getText().toString());
        novaConta.setSaldo(new BigDecimal(editSaldo.getText().toString()));
        novaConta.setCor(editCor.getText().toString());
        System.out.println(String.valueOf(spinnerTipoConta.getId()));
        //novaConta.setTipoConta(TipoConta.values()[0]);
        System.out.println(String.valueOf(radioTipoEstadoConta.getCheckedRadioButtonId()));
        //novaConta.setTipoEstadoConta(TipoEstadoConta.values()[(radioTipoEstadoConta.getCheckedRadioButtonId())]);
        //arrumar ambos tipos lembrar que ele ta pegando id louco é bom tentat pegar o objeto radioButton ou spinner para conseguir puxar esses dados

        return novaConta;
    }



    public int checkButton(View v){
        return radioTipoEstadoConta.getCheckedRadioButtonId();
    }

    private boolean validateFieldsConta(String nomeConta) {
        boolean res = true;
        editNome.setError(null);
        editSaldo.setError(null);
        View focusView = null;
        if (!validateLenght(nomeConta, 2)){
            editNome.setError("Nome inválido, minimo de 2");
            focusView = editNome;
            res = false;
        }
        return res;
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

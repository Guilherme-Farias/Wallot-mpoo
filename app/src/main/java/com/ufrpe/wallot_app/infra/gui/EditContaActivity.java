package com.ufrpe.wallot_app.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.conta.negocio.ContaServices;
import com.ufrpe.wallot_app.infra.Sessao;
import com.ufrpe.wallot_app.infra.SessaoConta;
import com.ufrpe.wallot_app.usuario.dominio.Usuario;

public class EditContaActivity extends AppCompatActivity {
    EditText editTextNome, editSaldo;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_conta);

        final Conta conta = SessaoConta.instance.getConta();
        final Usuario usuario = Sessao.instance.getUsuario();
        editTextNome = findViewById(R.id.nome_conta);
        editSaldo = findViewById(R.id.saldo_conta);
        btnSalvar = findViewById(R.id.buttonEditarConta);

        editTextNome.setText(conta.getNome());
        editSaldo.setText(conta.getSaldo().toString());
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editarConta(usuario);
                } catch (Exception e){
                    Toast.makeText(EditContaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void editarConta(Usuario usuario){
        String nomeConta = editTextNome.getText().toString();
        String saldoConta = editSaldo.getText().toString();
        if (validateFieldsConta(nomeConta, saldoConta)){
            ContaServices contaServices = new ContaServices();
            Conta contaEditada = new Conta(nomeConta, saldoConta);
            if (contaEditada.getNome().equals(SessaoConta.instance.getConta().getNome()) || contaServices.getConta(usuario.getId(), contaEditada.getNome()) == null){
                contaServices.alterarDados(contaEditada);
                startActivity(new Intent(EditContaActivity.this, EditContaActivity.class));
                Toast.makeText(EditContaActivity.this, "Dados salvos com sucesso", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(EditContaActivity.this, "Conta já cadastrada", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validateFieldsConta(String nomeConta, String saldoConta) {
        boolean res = true;
        editTextNome.setError(null);
        editSaldo.setError(null);
        View focusView = null;
        if (!validateLenght(nomeConta, 2)){
            editTextNome.setError("Nome inválido, minimo de 2");
            focusView = editTextNome;
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
        startActivity(new Intent(EditContaActivity.this, ContasActivity.class));
    }
}

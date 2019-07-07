package com.ufrpe.bsi.mpoo.wallotapp.estatistica.gui;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.estatistica.dominio.Orcamento;
import com.ufrpe.bsi.mpoo.wallotapp.estatistica.negocio.OrcamentoServices;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.DatePickerFragments;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.negocio.UsuarioServices;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CriaOrcamentoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private TextView txtDataInicio;
    private TextView txtDataFinal;
    private EditText txtGastoPlanejado;
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private OrcamentoServices orcamentoServices = new OrcamentoServices();
    private int position;
    private Usuario usuario = SessaoUsuario.instance.getUsuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cria_orcamento);
        txtGastoPlanejado = findViewById(R.id.valor_planejado_total);
        txtDataInicio = findViewById(R.id.str_data_inicial_orcamento);
        txtDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 1;
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        txtDataFinal = findViewById(R.id.str_data_final_orcamento);
        txtDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 2;
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        Button btnSalvar = findViewById(R.id.btn_salvar_orcamento);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orcamento orcamento = criaOrcamento();
                orcamentoServices.cadastrarOrcamento(orcamento);

            }
        });


    }

    private Orcamento criaOrcamento() {
        Orcamento orcamento = new Orcamento();
        orcamento.setTitulo("Orçamento " +  getValorDate(1));
        orcamento.setFkUsuario(usuario.getId());
        orcamento.setDataInicial(getValorDate(1));
        orcamento.setDataFinal(getValorDate(2));
        orcamento.setGastoEstimado(new BigDecimal(txtGastoPlanejado.getText().toString()));
        return orcamento;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.add(Calendar.DATE, 0);
        Date date = c.getTime();
        if(position == 1){
            txtDataInicio.setText(formatDate.format(date));
        } else {
            txtDataFinal.setText(formatDate.format(date));
        }
    }

    private Date getValorDate(int position) {
        Date date = null;
        try {
            if (position==1){
                date = formatDate.parse(txtDataInicio.getText().toString());
            } else {
                date = formatDate.parse(txtDataFinal.getText().toString());
            }
        } catch (Exception e){
            showToast(e.getMessage());
        }
        return date;
    }

    private void showToast(String s) {
        Toast.makeText(CriaOrcamentoActivity.this , s, Toast.LENGTH_LONG).show();}


}

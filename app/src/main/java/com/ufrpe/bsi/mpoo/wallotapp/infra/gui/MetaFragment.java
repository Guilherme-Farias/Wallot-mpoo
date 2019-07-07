package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia.TransacaoDAO;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MetaFragment extends Fragment {
    private BarChart mChart;
    private TransacaoDAO transacaoDAO = new TransacaoDAO();
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    TextView fbAlimentacao;
    TextView fbCasa;
    TextView fbCompras;
    TextView fbComunicao;
    TextView fbTransporte;
    TextView fbVeiculo;
    TextView fbVidaLazer;

    public MetaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meta, container, false);
        fbAlimentacao = view.findViewById(R.id.valor_alimentacao);
        fbCasa = view.findViewById(R.id.valor_casa);
        fbCompras = view.findViewById(R.id.valor_compras);
        fbComunicao = view.findViewById(R.id.valor_comunicacao);
        fbTransporte = view.findViewById(R.id.valor_transportes);
        fbVeiculo = view.findViewById(R.id.valor_veiculo);
        fbVidaLazer = view.findViewById(R.id.valor_vida_e_lazer);

        String strDateAtual = "20190706";
        String strDateInicial = "20190701";

        //PEGANDO OS GASTOS ATUAIS DA CADA CATEGORIA
        BigDecimal gastoAtualAlimentacao = transacaoDAO.getGastoEntreDatas(usuario.getId(), 1, strDateInicial, strDateAtual);
        BigDecimal gastoAtualCasa = transacaoDAO.getGastoEntreDatas(usuario.getId(), 2, strDateInicial, strDateAtual);
        BigDecimal gastoAtualCompras = transacaoDAO.getGastoEntreDatas(usuario.getId(), 3, strDateInicial, strDateAtual);
        BigDecimal gastoAtualComunicacao = transacaoDAO.getGastoEntreDatas(usuario.getId(), 4, strDateInicial, strDateAtual);
        BigDecimal gastoAtualTransporte = transacaoDAO.getGastoEntreDatas(usuario.getId(), 5, strDateInicial, strDateAtual);
        BigDecimal gastoAtualVeiculo = transacaoDAO.getGastoEntreDatas(usuario.getId(), 6, strDateInicial, strDateAtual);
        BigDecimal gastoAtualVidaLazer = transacaoDAO.getGastoEntreDatas(usuario.getId(), 7, strDateInicial, strDateAtual);

        //PEGANDO AS MEDIAS DOS GASTOS ATUAIS DE CADA CATEGORIA
        BigDecimal mediaGastoAtualAlimentacao = gastoAtualAlimentacao.divide(new BigDecimal("5"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualCasa = gastoAtualCasa.divide(new BigDecimal("5"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualCompras = gastoAtualCompras.divide(new BigDecimal("5"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualComunicacao = gastoAtualComunicacao.divide(new BigDecimal("5"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualTransporte = gastoAtualTransporte.divide(new BigDecimal("5"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualVeiculo = gastoAtualVeiculo.divide(new BigDecimal("5"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualVidaLazer = gastoAtualVidaLazer.divide(new BigDecimal("5"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial

        //GASTOS ESTIMADOS
        BigDecimal gastoEstimadoAlimentacao = new BigDecimal("200");
        BigDecimal gastoEstimadoCasa = new BigDecimal("210");
        BigDecimal gastoEstimadoCompras = new BigDecimal("180");
        BigDecimal gastoEstimadoComunicacao = new BigDecimal("200");
        BigDecimal gastoEstimadoTransporte = new BigDecimal("190");
        BigDecimal gastoEstimadoVeiculo = new BigDecimal("220");
        BigDecimal gastoEstimadoVidaLazer = new BigDecimal("200");

        //PEGANDO AS MEDIAS DOS GASTOS ESTIMADOS DE CADA CATEGORIA
        BigDecimal mediaGastoEstimadoAlimentacao = gastoEstimadoAlimentacao.divide(new BigDecimal("30"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoEstimadoCasa = gastoEstimadoCasa.divide(new BigDecimal("30"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoEstimadoCompras = gastoEstimadoCompras.divide(new BigDecimal("30"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoEstimadoComunicacao = gastoEstimadoComunicacao.divide(new BigDecimal("30"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoEstimadoTransporte = gastoEstimadoTransporte.divide(new BigDecimal("30"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoEstimadoVeiculo = gastoEstimadoVeiculo.divide(new BigDecimal("30"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoEstimadoVidaLazer = gastoEstimadoVidaLazer.divide(new BigDecimal("30"), BigDecimal.ROUND_UP); //Dividir pela data atual - data inicial

        mChart = view.findViewById(R.id.bar_chart);

        ArrayList<BarEntry> values = new ArrayList<>();

        //COLOCANDO OS VALORES DO GRÁFICO
        values.add(new BarEntry(1, gastoEstimadoAlimentacao.intValue()));
        values.add(new BarEntry(1, gastoAtualAlimentacao.intValue()));
        values.add(new BarEntry(2, gastoEstimadoCasa.intValue()));
        values.add(new BarEntry(2, gastoAtualCasa.intValue()));
        values.add(new BarEntry(3, gastoEstimadoCompras.intValue()));
        values.add(new BarEntry(3, gastoAtualCompras.intValue()));
        values.add(new BarEntry(4, gastoEstimadoComunicacao.intValue()));
        values.add(new BarEntry(4, gastoAtualComunicacao.intValue()));
        values.add(new BarEntry(5, gastoEstimadoTransporte.intValue()));
        values.add(new BarEntry(5, gastoAtualTransporte.intValue()));
        values.add(new BarEntry(6, gastoEstimadoVeiculo.intValue()));
        values.add(new BarEntry(6, gastoAtualVeiculo.intValue()));
        values.add(new BarEntry(7, gastoEstimadoVidaLazer.intValue()));
        values.add(new BarEntry(7, gastoAtualVidaLazer.intValue()));

        BarDataSet barDataSet = new BarDataSet(values, "Sla");
        barDataSet.setColors(Color.parseColor("#116030"), Color.parseColor("#32B543"));
        barDataSet.setBarBorderColor(Color.DKGRAY);
        barDataSet.setBarShadowColor(Color.GRAY);
        barDataSet.setBarBorderWidth(1);

        mChart.getLegend().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getDescription().setEnabled(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getXAxis().setAxisLineWidth(2.5f);
        mChart.getAxisLeft().setAxisLineWidth(2.5f);

        XAxis xAxis = mChart.getXAxis();
        YAxis yAxis = mChart.getAxisLeft();
        xAxis.setTextSize(12);
        yAxis.setTextSize(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mChart.setTouchEnabled(true);


        BarData data = new BarData(barDataSet);
        mChart.setData(data);


        mChart.setFitBars(true);

        if (mediaGastoAtualAlimentacao.intValue() > mediaGastoEstimadoAlimentacao.intValue()) {
            fbAlimentacao.setText(": Seus gastos nesta categoria  estão acima do recomendado, cuidado");
        } else {
            fbAlimentacao.setText(": Seus gastos nesta categoria  estão seguros, parabéns");
        }
        if (mediaGastoAtualCasa.intValue() > mediaGastoEstimadoCasa.intValue()) {
            fbCasa.setText(": Seus gastos nesta categoria estão acima do recomendado, cuidado");
        } else {
            fbCasa.setText(": Seus gastos nesta categoria estão seguros, parabéns");
        }
        if (mediaGastoAtualCompras.intValue() > mediaGastoEstimadoCompras.intValue()) {
            fbCompras.setText(": Seus gastos nesta categoria  estão acima do recomendado, cuidado");
        } else {
            fbCompras.setText(": Seus gastos nesta categoria  estão seguros, parabéns");
        }
        if (mediaGastoAtualComunicacao.intValue() > mediaGastoEstimadoComunicacao.intValue()) {
            fbComunicao.setText(": Seus gastos nesta categoria  estão acima do recomendado, cuidado");
        } else {
            fbComunicao.setText(": Seus gastos nesta categoria  estão seguros, parabéns");
        }
        if (mediaGastoAtualTransporte.intValue() > mediaGastoEstimadoTransporte.intValue()) {
            fbTransporte.setText(": Seus gastos nesta categoria  estão acima do recomendado, cuidado");
        } else {
            fbTransporte.setText(": Seus gastos nesta categoria  seguros, parabéns");
        }
        if (mediaGastoAtualVeiculo.intValue() > mediaGastoEstimadoVeiculo.intValue()) {
            fbVeiculo.setText(": Seus gastos nesta categoria  estão acima do recomendado, cuidado");
        } else {
            fbVeiculo.setText(": Seus gastos nesta categoria  estão seguros, parabéns");
        }
        if (mediaGastoAtualVidaLazer.intValue() > mediaGastoEstimadoVidaLazer.intValue()) {
            fbVidaLazer.setText(": Seus gastos nesta categoria  estão acima do recomendado, cuidado");
        } else {
            fbVidaLazer.setText(": Seus gastos nesta categoria estão seguros, parabéns");
        }

        return view;


    }



}

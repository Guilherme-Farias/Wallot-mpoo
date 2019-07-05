package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class OrcamentoFragment extends Fragment {
    private BarChart mChart;
    private TransacaoDAO transacaoDAO = new TransacaoDAO();
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public OrcamentoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orcamento, container, false);

        Date dateAtual = new Date(System.currentTimeMillis());

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

        System.out.println(gastoAtualAlimentacao);
        System.out.println(gastoAtualCasa);
        System.out.println(gastoAtualCompras);
        System.out.println(gastoAtualComunicacao);
        System.out.println(gastoAtualTransporte);
        System.out.println(gastoAtualVeiculo);
        System.out.println(gastoAtualVidaLazer);

        //PEGANDO AS MEDIAS DOS GASTOS ATUAIS DE CADA CATEGORIA
        BigDecimal mediaGastoAtualAlimentacao = gastoAtualAlimentacao.divide(new BigDecimal("5")); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualCasa = gastoAtualCasa.divide(new BigDecimal("5")); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualCompras = gastoAtualCompras.divide(new BigDecimal("5")); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualComunicacao = gastoAtualComunicacao.divide(new BigDecimal("5")); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualTransporte = gastoAtualTransporte.divide(new BigDecimal("5")); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualVeiculo = gastoAtualVeiculo.divide(new BigDecimal("5")); //Dividir pela data atual - data inicial
        BigDecimal mediaGastoAtualVidaLazer = gastoAtualVidaLazer.divide(new BigDecimal("5")); //Dividir pela data atual - data inicial

        mChart = view.findViewById(R.id.bar_chart);

        ArrayList<BarEntry> values = new ArrayList<>();

        //COLOCANDO OS VALORES DO GRÁFICO
        values.add(new BarEntry(1, 200)); // Y = Gasto Estimado de Alimentação
        values.add(new BarEntry(1, gastoAtualAlimentacao.intValue()));
        values.add(new BarEntry(2, 210)); // Y = Gasto Estimado de Casa
        values.add(new BarEntry(2, gastoAtualCasa.intValue()));
        values.add(new BarEntry(3, 180)); // Y = Gasto Estimado de Compras
        values.add(new BarEntry(3, gastoAtualCompras.intValue()));
        values.add(new BarEntry(4, 200)); // Y = Gasto Estimado de Comunicação
        values.add(new BarEntry(4, gastoAtualComunicacao.intValue()));
        values.add(new BarEntry(5, 190)); // Y = Gasto Estimado de Transporte
        values.add(new BarEntry(5, gastoAtualTransporte.intValue()));
        values.add(new BarEntry(6, 220)); // Y = Gasto Estimado de Veiculo
        values.add(new BarEntry(6, gastoAtualVeiculo.intValue()));
        values.add(new BarEntry(7, 200)); // Y = Gasto Estimado de Vida e Lazer
        values.add(new BarEntry(7, gastoAtualVidaLazer.intValue()));

        BarDataSet barDataSet = new BarDataSet(values, "Sla");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //barDataSet.setColors(Color.RED, Color.GREEN, Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.RED, Color.GREEN, Color.BLACK, Color.BLUE);

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

        return view;
    }


}

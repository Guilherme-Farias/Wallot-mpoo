package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia.TransacaoDAO;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraficoFragment extends Fragment {
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private ContaDAO contaDAO = new ContaDAO();
    private TransacaoDAO transacaoDAO = new TransacaoDAO();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");


    public GraficoFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grafico, container, false);

        //ainda está sendo modificado(está com algumass falhas)

        //pega dados do layout
        LineChart saldoChart = view.findViewById(R.id.line_chart_saldo);

        saldoChart.setDragEnabled(true);
        saldoChart.setScaleEnabled(false);

        //PEGANDO OS VALORES DO EIXO X (DIAS) #########################

        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        Date date1 = calendar.getTime();
        calendar.add(Calendar.DATE, -3);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        Date date2 = calendar.getTime();
        calendar.add(Calendar.DATE, -3);
        int day3 = calendar.get(Calendar.DAY_OF_MONTH);
        Date date3 = calendar.getTime();
        calendar.add(Calendar.DATE, -3);
        int day4 = calendar.get(Calendar.DAY_OF_MONTH);
        Date date4 = calendar.getTime();
        calendar.add(Calendar.DATE, -3);
        int day5 = calendar.get(Calendar.DAY_OF_MONTH);
        Date date5 = calendar.getTime();
        calendar.add(Calendar.DATE, -3);
        int day6 = calendar.get(Calendar.DAY_OF_MONTH);
        Date date6 = calendar.getTime();
        calendar.add(Calendar.DATE, -3);
        int day7 = calendar.get(Calendar.DAY_OF_MONTH);
        Date date7 = calendar.getTime();


        //FORMATANDO AS DATAS PARA BUSCA

        String strDate1 = dateFormat.format(date1);
        String strDate2 = dateFormat.format(date2);
        String strDate3 = dateFormat.format(date3);
        String strDate4 = dateFormat.format(date4);
        String strDate5 = dateFormat.format(date5);
        String strDate6 = dateFormat.format(date6);
        String strDate7 = dateFormat.format(date7);


        //PEGANDO OS VALORES DO EIXO Y (SALDO) #######################

        int saldo1 = contaDAO.getSaldoContas(usuario.getId()).intValue();
        int saldo2 = transacaoDAO.getSaldoEntreDatas(usuario.getId(), strDate2, strDate1, new BigDecimal(saldo1)).intValue();
        int saldo3 = transacaoDAO.getSaldoEntreDatas(usuario.getId(), strDate3, strDate2, new BigDecimal(saldo2)).intValue();
        int saldo4 = transacaoDAO.getSaldoEntreDatas(usuario.getId(), strDate4, strDate3, new BigDecimal(saldo3)).intValue();
        int saldo5 = transacaoDAO.getSaldoEntreDatas(usuario.getId(), strDate5, strDate4, new BigDecimal(saldo4)).intValue();
        int saldo6 = transacaoDAO.getSaldoEntreDatas(usuario.getId(), strDate6, strDate5, new BigDecimal(saldo5)).intValue();
        int saldo7 = transacaoDAO.getSaldoEntreDatas(usuario.getId(), strDate7, strDate6, new BigDecimal(saldo6)).intValue();


        //COLOCANDO VALORES NO GRÁFICO ###############################
        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(day7, saldo7));
        values.add(new Entry(day6, saldo6));
        values.add(new Entry(day5, saldo5));
        values.add(new Entry(day4, saldo4));
        values.add(new Entry(day3, saldo3));
        values.add(new Entry(day2, saldo2));
        values.add(new Entry(day1, saldo1));


        //PERSONALIZANDO O EIXO X
        XAxis xAxis = saldoChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#00574B"));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12);

        //PERSONALIZANDO O EIXO Y
        YAxis yAxis = saldoChart.getAxisLeft();
        xAxis.setTextColor(Color.parseColor("#00574B"));
        yAxis.setTextSize(12);

        //PERSONALIZANDO A LINHA
        LineDataSet lineDataSet = new LineDataSet(values, "Histórico do Saldo");
        lineDataSet.setColor(Color.parseColor("#32B543"));
        lineDataSet.setLineWidth(5f);
        lineDataSet.setValueTextSize(0);
        lineDataSet.setCircleColor(Color.parseColor("#32B543"));
        lineDataSet.setCircleHoleColor(Color.parseColor("#32B543"));

        //PREENCHENDO O GRÁFICO
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.chart_gradient);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(drawable);

        //AJUSTES VISUAIS
        saldoChart.getLegend().setEnabled(false);
        saldoChart.getAxisRight().setEnabled(false);
        saldoChart.getXAxis().setDrawGridLines(false);
        saldoChart.getAxisLeft().setDrawGridLines(false);
        saldoChart.getDescription().setEnabled(false);
        saldoChart.getXAxis().setAxisLineWidth(2.5f);
        saldoChart.getAxisLeft().setAxisLineWidth(2.5f);

        //PLOTANDO O GRAFICO
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        saldoChart.setData(data);

        return view;
    }

}
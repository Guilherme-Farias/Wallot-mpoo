package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia.TransacaoDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraficoFragment extends Fragment {


    public GraficoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grafico, container, false);

        LineChart mChart = (LineChart) view.findViewById(R.id.line_chart);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        //PEGANDO OS VALORES DO EIXO X (DIAS) #########################

        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, -3);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, -3);
        int day3 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, -3);
        int day4 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, -3);
        int day5 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, -3);
        int day6 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, -3);
        int day7 = calendar.get(Calendar.DAY_OF_MONTH);

        System.out.println(day1);

        //PEGANDO OS VALORES DO EIXO X (DIAS) #########################


        //PEGANDO OS VALORES DO EIXO Y (SALDO) #######################

        ContaDAO contaDAO = new ContaDAO();
        TransacaoDAO transacaoDAO = new TransacaoDAO();
        BigDecimal saldoTotal = contaDAO.getSaldoContas(SessaoUsuario.instance.getUsuario().getId());

        //BigDecimal saldoTeste = transacaoDAO.getSaldoEntreDatas(SessaoUsuario.instance.getUsuario().getId(), "20190620", "20190622");

        //System.out.println(saldoTeste);


        //PEGANDO OS VALORES DO EIXO Y (SALDO) #######################


        //COLOCANDO VALORES NO GRÁFICO ###############################

        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(day7, 50));
        values.add(new Entry(day6, 50));
        values.add(new Entry(day5, 35));
        values.add(new Entry(day4, 45));
        values.add(new Entry(day3, 25));
        values.add(new Entry(day2, 30));
        values.add(new Entry(day1, 30));

        //COLOCANDO VALORES NO GRÃ�FICO ###############################

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#00574B"));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12);

        YAxis yAxis = mChart.getAxisLeft();
        xAxis.setTextColor(Color.parseColor("#00574B"));
        yAxis.setTextSize(12);

        LineDataSet lineDataSet = new LineDataSet(values, "Histórico do Saldo");
        lineDataSet.setColor(Color.parseColor("#32B543"));
        lineDataSet.setLineWidth(5f);
        lineDataSet.setValueTextSize(0);
        lineDataSet.setCircleColor(Color.parseColor("#32B543"));
        lineDataSet.setCircleHoleColor(Color.parseColor("#32B543"));

        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.chart_gradient);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(drawable);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);


        mChart.getLegend().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getDescription().setEnabled(false);
        mChart.getXAxis().setAxisLineWidth(2.5f);
        mChart.getAxisLeft().setAxisLineWidth(2.5f);

        mChart.setData(data);

        return view;
    }

}

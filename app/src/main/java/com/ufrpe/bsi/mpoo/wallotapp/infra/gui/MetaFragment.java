package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.estatistica.dominio.Orcamento;
import com.ufrpe.bsi.mpoo.wallotapp.estatistica.persistencia.OrcamentoDAO;
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
public class MetaFragment extends Fragment {
    private TransacaoDAO transacaoDAO = new TransacaoDAO();
    private OrcamentoDAO orcamentoDAO = new OrcamentoDAO();
    private Usuario usuario = SessaoUsuario.instance.getUsuario();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public MetaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meta, container, false);

        Orcamento orcamento = orcamentoDAO.getOrcamentoByIdUsuario(usuario.getId());

        Date dataInicial = orcamento.getDataInicial();
        Date dataFinal = orcamento.getDataFinal();
        Date dataAtual = new Date(System.currentTimeMillis());

        String dataInicialStr = dateFormat.format(dataInicial);
        String dataAtualStr = dateFormat.format(dataAtual);

        BigDecimal gastoEstimado = orcamento.getGastoEstimado();
        BigDecimal gastoAtual = transacaoDAO.getGastoEntreDatas(usuario.getId(), dataInicialStr, dataAtualStr);

        Calendar dataInicialCal = Calendar.getInstance();
        dataInicialCal.setTime(dataInicial);
        Calendar dataFinalCal = Calendar.getInstance();
        dataFinalCal.setTime(dataFinal);

        dataFinalCal.add(Calendar.DATE, - dataInicialCal.get(Calendar.DAY_OF_MONTH));
        int totalDias = dataFinalCal.get(Calendar.DAY_OF_MONTH);

        dataFinalCal.add(Calendar.DATE, dataInicialCal.get(Calendar.DAY_OF_MONTH));
        dataFinalCal.add(Calendar.DATE, - Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        int diasContados = dataFinalCal.get(Calendar.DAY_OF_MONTH);

        BigDecimal mediaGastoEstimado = gastoEstimado.divide(new BigDecimal(totalDias));
        BigDecimal mediaGastoPrevisto = gastoAtual.divide(new BigDecimal(diasContados));


        //PEGA OS DADOS DO LAYOUT
        LineChart mChart = view.findViewById(R.id.line_chart_orcamento);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);


        //CRIANDO A LINHA DE GASTOS ESTIMADOS
        ArrayList<Entry> linhaGastosEstimados = new ArrayList<>();

        linhaGastosEstimados.add(new Entry(1, mediaGastoEstimado.intValue()));
        linhaGastosEstimados.add(new Entry(2, (mediaGastoEstimado.multiply(new BigDecimal(2))).intValue()));
        linhaGastosEstimados.add(new Entry(3, (mediaGastoEstimado.multiply(new BigDecimal(3))).intValue()));
        linhaGastosEstimados.add(new Entry(4, (mediaGastoEstimado.multiply(new BigDecimal(4))).intValue()));
        linhaGastosEstimados.add(new Entry(5, (mediaGastoEstimado.multiply(new BigDecimal(5))).intValue()));
        linhaGastosEstimados.add(new Entry(6, (mediaGastoEstimado.multiply(new BigDecimal(6))).intValue()));
        linhaGastosEstimados.add(new Entry(7, (mediaGastoEstimado.multiply(new BigDecimal(7))).intValue()));

        //CRIANDO A LINHA DE GASTOS PREVISTOS
        ArrayList<Entry> linhaGastosPrevistos = new ArrayList<>();

        linhaGastosPrevistos.add(new Entry(1, mediaGastoPrevisto.intValue()));
        linhaGastosPrevistos.add(new Entry(2, (mediaGastoPrevisto.multiply(new BigDecimal(2))).intValue()));
        linhaGastosPrevistos.add(new Entry(3, (mediaGastoPrevisto.multiply(new BigDecimal(3))).intValue()));
        linhaGastosPrevistos.add(new Entry(4, (mediaGastoPrevisto.multiply(new BigDecimal(4))).intValue()));
        linhaGastosPrevistos.add(new Entry(5, (mediaGastoPrevisto.multiply(new BigDecimal(5))).intValue()));
        linhaGastosPrevistos.add(new Entry(6, (mediaGastoPrevisto.multiply(new BigDecimal(6))).intValue()));
        linhaGastosPrevistos.add(new Entry(7, (mediaGastoPrevisto.multiply(new BigDecimal(7))).intValue()));

        //PERSONALIZANDO O EIXO X
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#00574B"));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12);

        //PERSONALIZANDO O EIXO Y
        YAxis yAxis = mChart.getAxisLeft();
        xAxis.setTextColor(Color.parseColor("#00574B"));
        yAxis.setTextSize(12);

        //PERSONALIZANDO A LINHA DE GASTOS ESTIMADOS
        LineDataSet lineDataEstimado = new LineDataSet(linhaGastosEstimados, "");
        lineDataEstimado.setColor(Color.parseColor("#116030"));
        lineDataEstimado.setLineWidth(5f);
        lineDataEstimado.setValueTextSize(0);
        lineDataEstimado.setCircleColor(Color.parseColor("#116030"));
        lineDataEstimado.setCircleHoleColor(Color.parseColor("#116030"));

        //PERSONALIZANDO A LINHA DE GASTOS PREVISTOS
        LineDataSet lineDataPrevisto = new LineDataSet(linhaGastosPrevistos, "");
        lineDataPrevisto.setColor(Color.parseColor("#32B543"));
        lineDataPrevisto.setLineWidth(5f);
        lineDataPrevisto.setValueTextSize(0);
        lineDataPrevisto.setCircleColor(Color.parseColor("#32B543"));
        lineDataPrevisto.setCircleHoleColor(Color.parseColor("#32B543"));

        //AJUSTES VISUAIS
        mChart.getLegend().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getDescription().setEnabled(false);
        mChart.getXAxis().setAxisLineWidth(2.5f);
        mChart.getAxisLeft().setAxisLineWidth(2.5f);

        //PLOTANDO O GRAFICO
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataEstimado);
        dataSets.add(lineDataPrevisto);

        LineData data = new LineData(dataSets);
        mChart.setData(data);


        return view;

    }


}

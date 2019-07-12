package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.WallotAppException;
import com.ufrpe.bsi.mpoo.wallotapp.orcamento.dominio.Orcamento;
import com.ufrpe.bsi.mpoo.wallotapp.orcamento.persistencia.OrcamentoDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia.TransacaoDAO;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
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
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    TextView feedbackUsuario;

    public MetaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meta, container, false);

        Orcamento orcamento = orcamentoDAO.getOrcamento(usuario.getId());

        Date dataInicial = orcamento.getDataInicial();
        Date dataFinal = orcamento.getDataFinal();
        Date dataAtual = new Date(System.currentTimeMillis());

        String dataInicialStr = format.format(dataInicial);
        String dataAtualStr = format.format(dataAtual);

        BigDecimal gastoEstimado = orcamento.getGastoEstimado();
        BigDecimal gastoAtual = transacaoDAO.getGastoEntreDatas(usuario.getId(), dataInicialStr, dataAtualStr);

        Calendar dataInicialCal = Calendar.getInstance();
        dataInicialCal.setTime(dataInicial);
        Calendar dataFinalCal = Calendar.getInstance();
        dataFinalCal.setTime(dataFinal);
        Calendar dataAtualCal = Calendar.getInstance();

        dataFinalCal.add(Calendar.DATE, -dataInicialCal.get(Calendar.DAY_OF_MONTH));
        int totalDias = dataFinalCal.get(Calendar.DAY_OF_MONTH);
        int diasContados = dataAtualCal.get(Calendar.DAY_OF_MONTH);

        BigDecimal mediaGastoEstimado = gastoEstimado.divide(new BigDecimal(totalDias), RoundingMode.UP);
        BigDecimal mediaGastoPrevisto = gastoAtual.divide(new BigDecimal(diasContados), RoundingMode.UP);


        //PEGA OS DADOS DO LAYOUT
        LineChart orcamentoChart = view.findViewById(R.id.line_chart_orcamento);


        //CRIANDO A LINHA DE GASTOS ESTIMADOS
        ArrayList<Entry> linhaGastosEstimados = new ArrayList<>();

        linhaGastosEstimados.add(new Entry(1, mediaGastoEstimado.intValue()));
        linhaGastosEstimados.add(new Entry(Math.round(totalDias * 0.15), (mediaGastoEstimado.multiply(new BigDecimal(2))).intValue()));
        linhaGastosEstimados.add(new Entry(Math.round(totalDias * 0.30), (mediaGastoEstimado.multiply(new BigDecimal(3))).intValue()));
        linhaGastosEstimados.add(new Entry(Math.round(totalDias * 0.45), (mediaGastoEstimado.multiply(new BigDecimal(4))).intValue()));
        linhaGastosEstimados.add(new Entry(Math.round(totalDias * 0.60), (mediaGastoEstimado.multiply(new BigDecimal(5))).intValue()));
        linhaGastosEstimados.add(new Entry(Math.round(totalDias * 0.75), (mediaGastoEstimado.multiply(new BigDecimal(6))).intValue()));
        linhaGastosEstimados.add(new Entry(totalDias, (mediaGastoEstimado.multiply(new BigDecimal(7))).intValue()));

        //CRIANDO A LINHA DE GASTOS PREVISTOS
        ArrayList<Entry> linhaGastosPrevistos = new ArrayList<>();

        linhaGastosPrevistos.add(new Entry(1, mediaGastoPrevisto.intValue()));
        linhaGastosPrevistos.add(new Entry(Math.round(totalDias * 0.17), (mediaGastoPrevisto.multiply(new BigDecimal(2))).intValue()));
        linhaGastosPrevistos.add(new Entry(Math.round(totalDias * 0.34), (mediaGastoPrevisto.multiply(new BigDecimal(3))).intValue()));
        linhaGastosPrevistos.add(new Entry(Math.round(totalDias * 0.51), (mediaGastoPrevisto.multiply(new BigDecimal(4))).intValue()));
        linhaGastosPrevistos.add(new Entry(Math.round(totalDias * 0.68), (mediaGastoPrevisto.multiply(new BigDecimal(5))).intValue()));
        linhaGastosPrevistos.add(new Entry(Math.round(totalDias * 0.85), (mediaGastoPrevisto.multiply(new BigDecimal(6))).intValue()));
        linhaGastosPrevistos.add(new Entry(totalDias, (mediaGastoPrevisto.multiply(new BigDecimal(7))).intValue()));

        //CRIANDO OS PONTOS DA REGRESSÃO LINEAR
        dataInicialCal.add(Calendar.MONTH, -1);
        dataFinalCal.add(Calendar.MONTH, -1);

        Date dataInicialPast = dataInicialCal.getTime();
        Date dataFinalPast = dataFinalCal.getTime();

        String dataInicialPastStr = format.format(dataInicialPast);
        String dataFinalPastStr = format.format(dataFinalPast);


        ArrayList<Integer> coordGastos = transacaoDAO.getCoordGastosEntreDatas(usuario.getId(), dataInicialPastStr, dataFinalPastStr);
        ArrayList<Entry> linhaCoordPontos = new ArrayList<>();


        for (int i = 0; i < coordGastos.size() - 1; i += 2) {
            if (coordGastos.get(i) != 0) {
                Date dataGasto = stringToDate(coordGastos.get(i + 1).toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dataGasto);
                int diaGasto = calendar.get(Calendar.DAY_OF_MONTH);
                linhaCoordPontos.add(new Entry(diaGasto, coordGastos.get(i)));
            }
        }

        //PERSONALIZANDO A LINHA DE GASTOS ESTIMADOS
        LineDataSet lineDataEstimado = new LineDataSet(linhaGastosEstimados, "Gasto Estimado Pelo Usuário");
        lineDataEstimado.setColor(Color.parseColor("#116030"));
        lineDataEstimado.setLineWidth(5f);
        lineDataEstimado.setValueTextSize(0);
        lineDataEstimado.setCircleColor(Color.parseColor("#116030"));
        lineDataEstimado.setCircleHoleColor(Color.parseColor("#116030"));

        //PERSONALIZANDO A LINHA DE GASTOS PREVISTOS
        LineDataSet lineDataPrevisto = new LineDataSet(linhaGastosPrevistos, "Gasto Previsto");
        lineDataPrevisto.setColor(Color.parseColor("#32B543"));
        lineDataPrevisto.setLineWidth(5f);
        lineDataPrevisto.setValueTextSize(0);
        lineDataPrevisto.setCircleColor(Color.parseColor("#32B543"));
        lineDataPrevisto.setCircleHoleColor(Color.parseColor("#32B543"));

        //PERSONALIZANDO OS PONTOS DA REGRESSÃO LINEAR
        LineDataSet lineDataPontos = new LineDataSet(linhaCoordPontos, "");
        lineDataPontos.setColor(Color.parseColor("#00000000"));
        lineDataPontos.setLineWidth(0);
        lineDataPontos.setValueTextSize(0);
        lineDataPontos.setCircleRadius(3.5f);
        lineDataPontos.setCircleColor(Color.parseColor("#0276FD"));
        lineDataPontos.setCircleHoleColor(Color.parseColor("#0276FD"));

        //PERSONALIZANDO O EIXO X
        XAxis xAxis = orcamentoChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#00574B"));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12);

        //PERSONALIZANDO O EIXO Y
        YAxis yAxis = orcamentoChart.getAxisLeft();
        xAxis.setTextColor(Color.parseColor("#00574B"));
        yAxis.setTextSize(12);

        //AJUSTES VISUAIS
        orcamentoChart.setDragEnabled(true);
        orcamentoChart.setScaleEnabled(false);
        orcamentoChart.getAxisRight().setEnabled(false);
        orcamentoChart.getXAxis().setDrawGridLines(false);
        orcamentoChart.getAxisLeft().setDrawGridLines(false);
        orcamentoChart.getDescription().setEnabled(false);
        orcamentoChart.getXAxis().setAxisLineWidth(2.5f);
        orcamentoChart.getAxisLeft().setAxisLineWidth(2.5f);

        //PLOTANDO O GRAFICO
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataEstimado);
        dataSets.add(lineDataPrevisto);
        dataSets.add(lineDataPontos);

        LineData data = new LineData(dataSets);
        orcamentoChart.setData(data);

        feedbackUsuario = view.findViewById(R.id.feedback_orcamento);

        BigDecimal resultado = gastoEstimado.subtract(mediaGastoPrevisto.multiply(new BigDecimal(totalDias)));

        if (mediaGastoEstimado.intValue() > mediaGastoPrevisto.intValue()) {
            feedbackUsuario.setText("Você está economizando bem. Parabéns!" + "\nSe continuar assim você economizará R$ " + resultado);
        } else {
            feedbackUsuario.setText("Seus gastos estão acima do recomendado. Cuidado." + "\nSe continuar assim você irá gastar R$ " + resultado + " além do previsto");
        }

        return view;

    }

    public Date stringToDate(String strDate) {
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            new WallotAppException("Erro");
        }
        return date;
    }

}

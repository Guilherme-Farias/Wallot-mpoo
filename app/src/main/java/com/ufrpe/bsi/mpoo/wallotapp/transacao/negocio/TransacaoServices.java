package com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio;

import android.widget.Toast;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.negocio.ContaServices;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.app.WallotApp;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoConta;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoDeStatusTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia.TransacaoDAO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransacaoServices {
    SimpleDateFormat padraoData = new SimpleDateFormat("dd/MM/yyyy");
    TransacaoDAO transacaoDAO = new TransacaoDAO();
    ContaDAO contaDAO = new ContaDAO();

    public ArrayList<TipoTransacao> listarTiposTransacao(){
        return transacaoDAO.getTiposTransacao();
    }


    public void cadastrarTransacao(Transacao transacao, String strData) {
        final Conta conta = SessaoConta.instance.getConta();
        TransacaoDAO transacaoDAO = new TransacaoDAO();
        long res = transacaoDAO.cadastrarTransacao(transacao);
        Parcela parcela = new Parcela();
        parcela.setFkTransacao(res);
        parcela.setNumeroParcela(1);
        parcela.setTipoDeStatusTransacao(TipoDeStatusTransacao.CONSOLIDADO);
        parcela.setDataTransacao(pegarDateFormat(strData));
        BigDecimal multiplicador = new BigDecimal(transacao.getTipoTransacao().getMultiplicador());
        BigDecimal valorTotal = transacao.getValor();
        BigDecimal nParcelas = new BigDecimal(transacao.getQntParcelas());
        if (transacao.getQntParcelas() == 1){
            parcela.setValorParcela(valorTotal.multiply(multiplicador));
            transacaoDAO.cadastrarParcela(parcela);
            conta.addSaldo(parcela.getValorParcela());
            contaDAO.alterarSaldo(conta);
        } else {
            BigDecimal[] result = (valorTotal.multiply(new BigDecimal("100.00"))).divideAndRemainder(nParcelas);
            parcela.setValorParcela(((result[0].add(result[1])).divide(new BigDecimal("100.00"))).multiply(multiplicador));
            conta.addSaldo(parcela.getValorParcela());
            transacaoDAO.cadastrarParcela(parcela);
            contaDAO.alterarSaldo(conta);
            parcela.setTipoDeStatusTransacao(TipoDeStatusTransacao.NAO_CONSOLIDADO);
            for (long nParcela = 2; nParcela <= transacao.getQntParcelas(); nParcela++) {
                Parcela parcelasRemanescentes = parcela;
                parcelasRemanescentes.setNumeroParcela(nParcela);
                parcelasRemanescentes.setValorParcela((result[0]).divide(new BigDecimal("100.00")).multiply(multiplicador));
                Calendar c = Calendar.getInstance();
                c.setTime(parcelasRemanescentes.getDataTransacao());
                c.add(Calendar.MONTH, 1);
                parcelasRemanescentes.setDataTransacao(c.getTime());
                transacaoDAO.cadastrarParcela(parcelasRemanescentes);
            }
        }
    }

    private Date pegarDateFormat(String strData) {
        try {
            Date data = padraoData.parse(strData);
            return data;
        } catch (Exception e){
            Toast.makeText(WallotApp.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public ArrayList<Transacao> listarTransacaoPorData(long idUsuario){
        ArrayList<Transacao> contas = transacaoDAO.getTransacaosPorData(idUsuario);
        return contas;
    }
    /*public ArrayList<Transacao> listarTransacaoPorCategoria(long idUsuario, long idTransacao){
        ArrayList<Transacao> contas = transacaoDAO.getTransacaosPorCategoria(idUsuario);
        return contas;
    }
    public ArrayList<Transacao> listarTransacaoPorSubCategoria(long idUsuario, long idTransacao){
        ArrayList<Transacao> contas = transacaoDAO.getTransacaosPorSubCategoria(idUsuario);
        return contas;
    }
    public ArrayList<Transacao> listarTransacaoPorTipoTransacao(long idUsuario, long idTransacao){
        ArrayList<Transacao> contas = transacaoDAO.getTransacaosPorTipoTransacao(idUsuario);
        return contas;
    }*/

    public ArrayList<Parcela> listarParcelasPorData(long idUsuario){
        ArrayList<Parcela> parcelas = transacaoDAO.getParcelasPorData(idUsuario);
        return parcelas;
    }


    public Transacao getTransacao(long idTransacao) {
        Transacao transacao = transacaoDAO.getTransacao(idTransacao);
        return transacao;
    }
}

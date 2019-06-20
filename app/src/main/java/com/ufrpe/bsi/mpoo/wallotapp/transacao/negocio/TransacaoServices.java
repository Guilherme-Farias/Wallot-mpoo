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

    /*public spnCategoria.ArrayList<TipoTransacao> listarSubCategorias(long id) {
        retutn trasaccaoDAO.Parcelas
    }*/

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
        parcela.setDataTransacao(pegarDateFormat(strData));//data
        BigDecimal multiplicador = new BigDecimal(transacao.getTipoTransacao().getMultiplicador());
        BigDecimal valorTotal = transacao.getValor();
        long nParcelas = transacao.getQntParcelas();
        if (nParcelas == 1){
            parcela.setValorParcela(valorTotal.multiply(multiplicador));
            transacaoDAO.cadastrarParcela(parcela);
            conta.addSaldo(parcela.getValorParcela());
            contaDAO.alterarSaldo(conta);
        } else {
            BigDecimal remanescente = valorTotal.remainder(new BigDecimal(nParcelas));
            parcela.setValorParcela((((valorTotal.subtract(remanescente)).divide(new BigDecimal(nParcelas))).add(remanescente)));
            contaDAO.alterarSaldo(conta);
            BigDecimal valorParcelasRemancentes = (parcela.getValorParcela().subtract(remanescente).multiply(multiplicador));
            conta.addSaldo(parcela.getValorParcela());
            parcela.setValorParcela(parcela.getValorParcela().multiply(multiplicador));
            transacaoDAO.cadastrarParcela(parcela);
            for (long nParcela = 2; nParcela <= nParcelas; nParcela++) {
                Parcela parcelasRemanescentes = parcela;
                parcelasRemanescentes.setNumeroParcela(nParcela);
                parcelasRemanescentes.setValorParcela(valorParcelasRemancentes);
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

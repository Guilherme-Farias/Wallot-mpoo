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
import java.util.Date;

public class TransacaoServices {
    SimpleDateFormat padraoData = new SimpleDateFormat("dd/MM/yyyy");
    TransacaoDAO transacaoDAO = new TransacaoDAO();
    ContaDAO contaDAO = new ContaDAO();

    /*public spnCategoria.ArrayList<TipoTransacao> listarSubCategorias(long id) {
        retutn trasaccaoDAO.Parcelas
    }*/

    public ArrayList<TipoTransacao> listarTiposConta(){
        return transacaoDAO.getTiposTransacao();
    }

    public void cadastrarTransacao(Transacao transacao, String strData) {
        final Conta conta = SessaoConta.instance.getConta();
        TransacaoDAO transacaoDAO = new TransacaoDAO();
        long res = transacaoDAO.cadastrarTransacao(transacao);
        Parcela parcela = new Parcela();
        parcela.setFkTransacao(res);
        parcela.setNumeroParcela(1);
        parcela.setDataTransacao(pegarDateFormat(strData));
        if (transacao.getQntParcelas() == 1){
            parcela.setValorParcela(transacao.getValor());
            transacaoDAO.cadastrarParcela(parcela);
            BigDecimal valorConta = conta.getSaldo();
            BigDecimal multiplicador = new BigDecimal(transacao.getTipoTransacao().getMultiplicador());
            BigDecimal valorTransacao = transacao.getValor();
            BigDecimal b = valorConta.add(valorTransacao.multiply(multiplicador));
            conta.setSaldo(b);
            contaDAO.alterarSaldo(conta);


        } else {
            //cadastra varias
        }
        /*transacaoDAO.cadastrarTransacao(transacao, strData);*/




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



}

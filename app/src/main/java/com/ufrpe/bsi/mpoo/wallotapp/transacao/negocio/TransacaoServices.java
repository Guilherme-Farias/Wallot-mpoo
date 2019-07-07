package com.ufrpe.bsi.mpoo.wallotapp.transacao.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoParcela;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.WallotAppException;
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
    private SimpleDateFormat padraoData = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat padraoData2 = new SimpleDateFormat("yyyyMMdd");
    private TransacaoDAO transacaoDAO = new TransacaoDAO();
    private ContaDAO contaDAO = new ContaDAO();
    private Transacao transacao = SessaoTransacao.instance.getTransacao();
    private Parcela parcelaSessao = SessaoParcela.instance.getParcela();

    //lista se é receita/despesa/transferencia
    public ArrayList<TipoTransacao> listarTiposTransacao(){
        return transacaoDAO.getTiposTransacao();
    }

    //cadastra transacao e suas parcelas
    public void cadastrarTransacao(String strData) {
        transacao = SessaoTransacao.instance.getTransacao();
        long idTransacao = transacaoDAO.cadastrarTransacao(transacao);
        Conta conta = contaDAO.getConta(transacao.getFkConta());
        Parcela parcela = criaPrimeiraParcela(strData, idTransacao);
        transacaoDAO.cadastrarParcela(parcela);
        conta.addSaldo(parcela.getValorParcela());
        contaDAO.alterarSaldo(conta);
         if(transacao.getQntParcelas()>1) {
            parcela.setTipoDeStatusTransacao(TipoDeStatusTransacao.NAO_CONSOLIDADO);
            parcela.setValorParcela(getValorParcela(false));
            for (long nParcela = 2; nParcela <= transacao.getQntParcelas(); nParcela++) {
                parcela.setNumeroParcela(nParcela);
                parcela.setDataTransacao(getProxMesParcela(parcela));
                transacaoDAO.cadastrarParcela(parcela);
            }
        }
    }




    //pega o proximo mes de cada parcela a mais em uma transacao
    private Date getProxMesParcela(Parcela parcela) {
        Calendar c = Calendar.getInstance();
        c.setTime(parcela.getDataTransacao());
        c.add(Calendar.MONTH, 1);
        return c.getTime();
    }

    //pega o valor da primeira parcela ou da segunda parcela
    private BigDecimal getValorParcela(boolean isPrimeiraParcela) {
        BigDecimal[] valoresParcelas = getValoresParcelas();
        BigDecimal valorParcela = valoresParcelas[0];
        if(isPrimeiraParcela){
            valorParcela = valorParcela.add(valoresParcelas[1]);
        }
        return valorParcela.divide(new BigDecimal("100.00"));
    }

    //faz as divisoes nas parcelas para ver se sobra resto e já põe o valor de multiplicado (positivo ou negativo)
    private BigDecimal[] getValoresParcelas() {
        BigDecimal multiplicador = new BigDecimal(transacao.getTipoTransacao().getMultiplicador());
        BigDecimal valorTotal = transacao.getValor().multiply(multiplicador);
        BigDecimal nParcelas = new BigDecimal(transacao.getQntParcelas());
        return (valorTotal.multiply(new BigDecimal("100.00"))).divideAndRemainder(nParcelas);
    }

    //construtor da primeira parcela
    private Parcela criaPrimeiraParcela(String strData, long idTransacao) {
        Parcela parcela = new Parcela();
        parcela.setTipoDeStatusTransacao(TipoDeStatusTransacao.CONSOLIDADO);
        parcela.setDataTransacao(pegarDateFormat(strData));
        parcela.setNumeroParcela(1);
        parcela.setFkTransacao(idTransacao);
        parcela.setValorParcela(getValorParcela(true));
        return parcela;
    }

    //pega o valor da data de string e devolve como Date
    private Date pegarDateFormat(String strData) {
        Date data = null;
        try {
            data = padraoData.parse(strData);
        } catch (Exception e){
            new WallotAppException("Data errada:", e);
        }
        return data;
    }

    //lista as parcelas(ainda em desenvolvimento)
    public ArrayList<Parcela> listarParcelasPorData(long idUsuario){
        return transacaoDAO.getParcelasPorData(idUsuario);
    }

    //pega uma transacão pelo id da transacao
    public Transacao getTransacao(long idTransacao) {
        return transacaoDAO.getTransacao(idTransacao);
    }

    //altera todos os dados
    public void editarParcela(Parcela novaParcela) {
        Conta conta = contaDAO.getConta(transacao.getFkConta());
        if(!parcelaSessao.getValorParcela().equals(novaParcela.getValorParcela())) {
            alterarSaldo(novaParcela, conta);
        } else if(!(parcelaSessao.getTipoDeStatusTransacao().equals(novaParcela.getTipoDeStatusTransacao()))){
            alterarStatus(novaParcela, conta);
        }
        if(!parcelaSessao.getDataTransacao().equals(novaParcela.getDataTransacao())){
            alterarData(novaParcela);
        }
    }

    //vai alterar a data da parcela
    private void alterarData(Parcela novaParcela) {
        parcelaSessao.setDataTransacao(novaParcela.getDataTransacao());
        transacaoDAO.alteraDataParcela(parcelaSessao);
    }

    //vai alterar o status da parcela
    private void alterarStatus(Parcela novaParcela, Conta conta) {
        parcelaSessao.setTipoDeStatusTransacao(novaParcela.getTipoDeStatusTransacao());
        if (isConsolidado(parcelaSessao)){
            conta.addSaldo(novaParcela.getValorParcela());
        } else{
            conta.subtractSaldo(novaParcela.getValorParcela());
        }
        contaDAO.alterarSaldo(conta);
        transacaoDAO.alteraStatusParcela(parcelaSessao);
    }

    //altera o saldo(ele ve todas as possibilidades e altera de acordo com elas
    private void alterarSaldo(Parcela novaParcela, Conta conta) {
        if (isConsolidado(parcelaSessao) && isConsolidado(novaParcela)) {
            BigDecimal variacao = novaParcela.getValorParcela().subtract(parcelaSessao.getValorParcela());
            conta.addSaldo(variacao);
        } else if (isConsolidado(parcelaSessao) && !isConsolidado(novaParcela)) {
            conta.subtractSaldo(parcelaSessao.getValorParcela());
            parcelaSessao.setTipoDeStatusTransacao(TipoDeStatusTransacao.NAO_CONSOLIDADO);
            transacaoDAO.alteraStatusParcela(parcelaSessao);
        } else if (!isConsolidado(parcelaSessao) && isConsolidado(novaParcela)){
            conta.addSaldo(novaParcela.getValorParcela());
            parcelaSessao.setTipoDeStatusTransacao(TipoDeStatusTransacao.CONSOLIDADO);
            transacaoDAO.alteraStatusParcela(parcelaSessao);
        }
        contaDAO.alterarSaldo(conta);
        parcelaSessao.setValorParcela(novaParcela.getValorParcela());
        transacaoDAO.alteraValorParcela(parcelaSessao);
    }

    private boolean isConsolidado(Parcela p){
        return p.getTipoDeStatusTransacao() == TipoDeStatusTransacao.CONSOLIDADO;
    }

    //deleta a parcela
    public void deletarParcela(Parcela parcela){
        if (isConsolidado(parcela)){
            Conta conta = contaDAO.getConta(transacao.getFkConta());
            conta.subtractSaldo(parcela.getValorParcela());
            contaDAO.alterarSaldo(conta);
        }
        transacaoDAO.deletarParcela(parcela);
        if (transacao.getQntParcelas() == 1){
            transacaoDAO.deletarTransacao(transacao);
        } else {
            transacao.setQntParcelas(transacao.getQntParcelas() - 1);
            transacaoDAO.alteraQntParcela(transacao);
        }
    }

    //lista toda as parcelas desta Transacao
    public ArrayList<Parcela> listarParcelasTransacao(long idTransacao) {
        return transacaoDAO.getParcelasDaTransacao(idTransacao);
    }

    //pega o valor total da transacao(futuramente pegará 3 valores(valores pagos/valor esperado inicial/valor esperedo atual)
    public BigDecimal[] getValorTotalTransacao(long idTransacao) {
        return transacaoDAO.getValorTotalTransacao(idTransacao);
    }

    public void cadastraParcela(Parcela parcela) {
        Conta conta = contaDAO.getConta(transacao.getFkConta());
        conta.addSaldo(parcela.getValorParcela());
        contaDAO.alterarSaldo(conta);
        transacaoDAO.cadastrarParcela(parcela);
        transacao.setQntParcelas(transacao.getQntParcelas() + 1);
        transacaoDAO.alteraQntParcela(transacao);

    }

    //será utilizado para pegar os ultimos valores pela data
    public ArrayList<Parcela> getParcelasPelaData(long idUsuario, String date) {
        return transacaoDAO.getUltimasParcelas(idUsuario, Integer.parseInt(date));
    }

    public BigDecimal getValorTotalData(Date data, long id) {
        return null/*transacaoDAO.getValorTotalData(padraoData2.format(data), id)*/;
    }
}

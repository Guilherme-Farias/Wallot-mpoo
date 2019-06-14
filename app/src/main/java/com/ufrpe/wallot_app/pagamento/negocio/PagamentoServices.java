package com.ufrpe.wallot_app.pagamento.negocio;

import android.widget.Toast;

import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.conta.persistencia.ContaDAO;
import com.ufrpe.wallot_app.infra.app.WallotApp;
import com.ufrpe.wallot_app.pagamento.dominio.Pagamento;
import com.ufrpe.wallot_app.pagamento.persistencia.PagamentoDAO;

public class PagamentoServices {
    PagamentoDAO pagamentoDAO = new PagamentoDAO();
    ContaDAO contaDAO = new ContaDAO();

    public long cadastraPagamento(Pagamento pagamento){
        long res = pagamentoDAO.cadastraPagamento(pagamento);
        Conta conta = contaDAO.getContaById(pagamento.getTbConta());
        if(pagamento.getTbTipoPagamento() == 1){
            conta.addReceita(pagamento.getValor());
        } else {
            conta.addDespesa(pagamento.getValor());
        }
        contaDAO.alterarSaldo(conta);
        return res;
    }



}

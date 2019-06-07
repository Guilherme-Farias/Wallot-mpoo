package com.ufrpe.wallot_app.conta.negocio;

import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.conta.persistencia.ContaDAO;
import com.ufrpe.wallot_app.infra.SessaoConta;
import com.ufrpe.wallot_app.pagamento.persistencia.PagamentoDAO;

import java.util.ArrayList;

public class ContaServices {
    private ContaDAO contaDAO = new ContaDAO();
    private PagamentoDAO pagamentoDAO = new PagamentoDAO();




    public long cadastrarConta(Conta conta){
        long res = contaDAO.cadastraCarteira(conta);
        return res;
    }

    public ArrayList<Conta> listarContas(long idUsuario){
        ArrayList<Conta> contas =contaDAO.getContas(idUsuario);
        return contas;
    }

    public Conta getConta(long idUsuario, String nomeConta){
        Conta conta = contaDAO.getConta(idUsuario, nomeConta);
        return conta;
    }

    public void alterarDados(Conta contaEditada) {
        Conta contaSessao = SessaoConta.instance.getConta();
        if(!contaEditada.getNome().isEmpty() && contaSessao.getNome() != contaEditada.getNome()){
            contaSessao.setNome(contaEditada.getNome());
            contaDAO.alterarNome(contaSessao);
        }

        if(contaSessao.getSaldo() != contaEditada.getSaldo() && !contaEditada.getSaldo().toString().isEmpty()){
            contaSessao.setSaldo(contaEditada.getSaldo());
            contaDAO.alterarSaldo(contaSessao);
        }
    }
}

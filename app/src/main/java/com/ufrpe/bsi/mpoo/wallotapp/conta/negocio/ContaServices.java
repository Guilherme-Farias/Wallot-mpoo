package com.ufrpe.bsi.mpoo.wallotapp.conta.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoConta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoConta;

import java.util.ArrayList;

public class ContaServices {
    private ContaDAO contaDAO = new ContaDAO();
    public long cadastrarConta(Conta conta){
        long res = contaDAO.cadastraConta(conta);
        return res;
    }

    public ArrayList<Conta> listarContas(long idUsuario){
        ArrayList<Conta> contas =contaDAO.getContas(idUsuario);
        return contas;
    }

    public ArrayList<TipoConta> listarTiposConta(){
        return contaDAO.getTiposConta();
    }

    public Conta getConta(long idConta){
        Conta conta = contaDAO.getConta(idConta);
        return conta;
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
        /*if(contaSessao.getTipoEstadoConta() != contaEditada.getTipoEstadoConta()){
            contaSessao.setTipoEstadoConta(contaEditada.getTipoEstadoConta());
            contaDAO.alterarTipoEstadoConta(contaSessao);
        }*/
    }

}

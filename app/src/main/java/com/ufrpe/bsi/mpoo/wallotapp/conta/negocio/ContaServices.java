package com.ufrpe.bsi.mpoo.wallotapp.conta.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoConta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoConta;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ContaServices {
    private ContaDAO contaDAO = new ContaDAO();
    private Conta contaSessao = SessaoConta.instance.getConta();

    //cadastra uma conta do usuario
    public long cadastrarConta(Conta conta){
        return contaDAO.cadastraConta(conta);
    }

    //lista todas as contas do usuario
    public ArrayList<Conta> listarContas(long idUsuario){return contaDAO.getContas(idUsuario, false);}

    //lista todas as contas ativas do usuario
    public ArrayList<Conta> listarContasAtivas(long idUsuario){return contaDAO.getContas(idUsuario, true);}

    //lista todos os tipos de conta(enum)
    public ArrayList<TipoConta> listarTiposConta(){return contaDAO.getTiposConta();}

    //pega conta pelo Id
    public Conta getConta(long idConta){return contaDAO.getConta(idConta);}

    //pega conta pelo id usuario e nome para ver se existe um conta com esse nome
    public Conta getConta(long idUsuario, String nomeConta){return contaDAO.getConta(idUsuario, nomeConta);}

    //altera todos os dados da conta
    public void alterarDados(Conta contaEditada) {
        if(!contaSessao.getNome().equals(contaEditada.getNome())){
            contaSessao.setNome(contaEditada.getNome());
            contaDAO.alterarNome(contaSessao);
        }
        if(!contaSessao.getSaldo().equals(contaEditada.getSaldo())){
            contaSessao.setSaldo(contaEditada.getSaldo());
            contaDAO.alterarSaldo(contaSessao);
        }
        if(contaSessao.getTipoEstadoConta() != contaEditada.getTipoEstadoConta()){
            contaSessao.setTipoEstadoConta(contaEditada.getTipoEstadoConta());
            contaDAO.alterarTipoEstadoConta(contaSessao);
        }
        if(contaSessao.getTipoConta() != contaEditada.getTipoConta()){
            contaSessao.setTipoConta(contaEditada.getTipoConta());
            contaDAO.alterarTipoConta(contaSessao);
        }
    }

    //verifica se existe conta ativa
    public Conta existContaAtiva(long idUsuario, long idConta) {return contaDAO.existContaAtiva(idUsuario, idConta);}

    //pega valor total das contas
    public BigDecimal getSaldoAtual(long idUsuario){
        return contaDAO.getSaldoContas(idUsuario);
    }
}

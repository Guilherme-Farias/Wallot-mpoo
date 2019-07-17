package com.ufrpe.bsi.mpoo.wallotapp.usuario.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoConta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoEstadoConta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.WallotAppException;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.persistencia.UsuarioDAO;

import java.math.BigDecimal;

public class UsuarioServices {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ContaDAO contaDAO = new ContaDAO();
    private Usuario usuarioSessao = SessaoUsuario.instance.getUsuario();

    //loga o usuario no sistema setando ele na sessão
    public void login(String email, String senha) throws WallotAppException{
        Usuario usuario = usuarioDAO.getUsuario(email, senha);
        if(usuario == null){throw new WallotAppException("Login inválido");
        } else {SessaoUsuario.instance.setUsuario(usuario);}
    }

    //reseta a Sessao
    public void logout(){
        SessaoUsuario sessaoUsuario = SessaoUsuario.instance;
        sessaoUsuario.reset();
    }

    public void cadastrar(Usuario usuario) throws WallotAppException{
        if(emailCadastrado(usuario) != null){
            throw new WallotAppException("Email já cadastrado");
        } else {
            Conta conta = criaContaPadrao(usuario);
            contaDAO.cadastraConta(conta);
        }
    }

    //conotroi uma conta padrão e seta no usuario
    private Conta criaContaPadrao(Usuario usuario) {
        Conta conta = new Conta();
        conta.setNome("Carteira");
        conta.setSaldo(new BigDecimal("00.00"));
        conta.setTipoConta(TipoConta.DINHEIRO);
        conta.setTipoEstadoConta(TipoEstadoConta.ATIVO);
        conta.setUsuario(usuario);
        return conta;
    }


    //verifica se o email já está cadastrado
    public Usuario emailCadastrado(Usuario usuario) {
        return this.usuarioDAO.getUsuario(usuario.getEmail());
    }

    //altera todos os dados do usuario
    public void alterarDados(Usuario usuario){
        if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equals(usuarioSessao.getEmail())){
            usuarioSessao.setEmail(usuario.getEmail());
            usuarioDAO.alterarEmail(usuarioSessao);
        }
        if (!usuario.getNome().isEmpty() && !usuario.getNome().equals(usuarioSessao.getNome())){
            usuarioSessao.setNome(usuario.getNome());
            usuarioDAO.alterarNome(usuarioSessao);
        }
        if (!usuario.getSenha().isEmpty() && !usuario.getSenha().equals(usuarioSessao.getSenha())){
            usuarioSessao.setSenha(usuario.getSenha());
            usuarioDAO.alterarSenha(usuarioSessao);
        }
    }
}

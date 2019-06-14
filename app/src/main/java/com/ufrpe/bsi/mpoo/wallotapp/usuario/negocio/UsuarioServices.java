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

    public void login(String email, String senha) throws WallotAppException{
        Usuario usuario = usuarioDAO.getUsuario(email, senha);
        if(usuario == null){
            throw new WallotAppException("Login inválido");
        } else {
            SessaoUsuario.instance.setUsuario(usuario);
        }
    }

    public void logout(){
        SessaoUsuario sessaoUsuario = SessaoUsuario.instance;
        sessaoUsuario.reset();
    }

    public Usuario cadastrar(Usuario usuario) throws WallotAppException{
        if(emailCadastrado(usuario) != null){
            throw new WallotAppException("Email já cadastrado");
        } else {
            long userId = usuarioDAO.cadastrar(usuario);
            usuario.setId(userId);

            Conta conta = new Conta();
            conta.setNome("Carteira");
            conta.setSaldo(new BigDecimal("0.00"));
            conta.setCor("#32B543");
            conta.setTipoConta(TipoConta.DINHEIRO);
            conta.setTipoEstadoConta(TipoEstadoConta.ATIVO);
            conta.setFkUsuario(userId);
            contaDAO.cadastraConta(conta);
        }
        return usuario;
    }

    public Usuario emailCadastrado(Usuario usuario) {
        return this.usuarioDAO.getUsuario(usuario.getEmail());
    }

    public void alterarDados(Usuario usuario){
        Usuario usuarioSessao = SessaoUsuario.instance.getUsuario();
        if (!usuario.getEmail().isEmpty() && usuario.getEmail() != usuarioSessao.getEmail()){
            usuarioSessao.setEmail(usuario.getEmail());
            usuarioDAO.alterarEmail(usuarioSessao);
        }
        if (!usuario.getNome().isEmpty() && usuario.getNome() != usuarioSessao.getNome()){
            usuarioSessao.setNome(usuario.getNome());
            usuarioDAO.alterarNome(usuarioSessao);
        }
        if (!usuario.getSenha().isEmpty()&& usuario.getSenha() != usuarioSessao.getSenha()){
            usuarioSessao.setSenha(usuario.getSenha());
            usuarioDAO.alterarSenha(usuarioSessao);
        }
    }

}

package com.ufrpe.wallot_app.usuario.negocio;


import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.conta.persistencia.ContaDAO;
import com.ufrpe.wallot_app.usuario.dominio.Usuario;
import com.ufrpe.wallot_app.usuario.persistencia.UsuarioDAO;
import com.ufrpe.wallot_app.infra.Sessao;
import com.ufrpe.wallot_app.infra.WallotAppException;

import java.util.ArrayList;


public class UsuarioServices {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ContaDAO contaDAO = new ContaDAO();

    public void login(String email, String senha) throws WallotAppException{
        Usuario usuario = usuarioDAO.getUsuario(email, senha);
        if(usuario == null){
            throw new WallotAppException("Login inválido");
        } else {
            Sessao.instance.setUsuario(usuario);
        }
    }

    public void logout(){
        Sessao sessao = Sessao.instance;
        sessao.reset();
    }

    public Usuario cadastrar(Usuario usuario) throws WallotAppException{
        if(emailCadastrado(usuario) != null){
            throw new WallotAppException("Email já cadastrado");
        } else {
            long userId = usuarioDAO.cadastrar(usuario);
            contaDAO.cadastraCarteira(new Conta("Carteira", "0.00", userId));
            usuario.setId(userId);
        }
        return usuario;
    }

    public Usuario emailCadastrado(Usuario usuario) {
        return this.usuarioDAO.getUsuario(usuario.getEmail());
    }

    public void alterarDados(Usuario usuario){
        Usuario usuarioSessao = Sessao.instance.getUsuario();
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

    public ArrayList<Conta> getContas(long usuarioId){
        ArrayList<Conta> contas = contaDAO.getContas(usuarioId);
        if(!contas.isEmpty()){
            return contas;
        } else{
            return  null;
        }
    }
}

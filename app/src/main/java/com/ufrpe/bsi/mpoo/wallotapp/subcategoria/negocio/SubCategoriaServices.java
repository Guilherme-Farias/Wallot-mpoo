package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ufrpe.bsi.mpoo.wallotapp.infra.app.WallotApp;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoSubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.persistencia.SubCategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia.TransacaoDAO;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SubCategoriaServices {
    private SubCategoriaDAO subCategoriasDAO = new SubCategoriaDAO();
    private TransacaoDAO transacaoDAO = new TransacaoDAO();
    private SubCategoria subCategoriaSessao = SessaoSubCategoria.instance.getSubCategoria();

    //cadastra as subcategorias pre selecionadas pela equipe
    public long cadastrarInicial(SubCategoria subCategoria) {
        long res = subCategoriasDAO.cadastroInicial(subCategoria);
        return res;
    }

    //cadastra as subcategorias do usuario
    public long cadastrar(SubCategoria subCategoria) {
        long res = subCategoriasDAO.cadastrar(subCategoria);
        return res;
    }

    //lista subcategorias sem "sem subcategoria"
    public ArrayList<SubCategoria> listarSubCategorias(long idUsuario, long idCategoria) {
        return subCategoriasDAO.getSubcategorias(idUsuario, idCategoria, false);
    }

    //lista subcategorias
    public ArrayList<SubCategoria> listarAllSubCategorias(long idUsuario, long idCategoria) {
        return subCategoriasDAO.getSubcategorias(idUsuario, idCategoria, true);
    }

    //pega uma subacategoria especifica
    public SubCategoria getSubCategoria(long idSubCategoria) {
        return  subCategoriasDAO.getSubCategoria(idSubCategoria);
    }

    //verifica se existe uma subacategoria do usuario com o mesmo nome
    public SubCategoria nomeCategoriaExist(long idUsuario, String nomeSubcategoria) {
        return  subCategoriasDAO.nomeCategoriaExist(idUsuario, nomeSubcategoria);
    }

    //transforma o byteArray em um Drawable
    public byte[] bitmapToByteArray(int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(WallotApp.getContext().getResources(), drawable);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    //altera todos os dados de uma categoria
    public void alterarDados(SubCategoria subCategoriaEditada) {
        if(!subCategoriaEditada.getNome().isEmpty() && subCategoriaSessao.getNome().equals(subCategoriaEditada.getNome())){
            subCategoriaSessao.setNome(subCategoriaEditada.getNome());
            subCategoriasDAO.alterarNome(subCategoriaSessao);
        }
        if(subCategoriaSessao.getIcone() != subCategoriaEditada.getIcone()){
            subCategoriaSessao.setIcone(subCategoriaEditada.getIcone());
            subCategoriasDAO.alterarIcone(subCategoriaSessao);
        }
        if(subCategoriaSessao.getCategoria().getId() != subCategoriaEditada.getCategoria().getId()){
            subCategoriaSessao.setCategoria(subCategoriaEditada.getCategoria());
            subCategoriasDAO.alteraCategoria(subCategoriaSessao);
            transacaoDAO.atualizaSubcategoria(subCategoriaSessao.getId());
        }
    }

    //deleta subcategoria
    public void deletarSubCategoria(SubCategoria subCategoria){
        transacaoDAO.atualizaSubcategoria(subCategoria.getId());
        subCategoriasDAO.deletarSubcategoria(subCategoria);
    }
}

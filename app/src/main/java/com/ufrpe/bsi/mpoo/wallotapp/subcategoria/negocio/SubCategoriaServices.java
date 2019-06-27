package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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


    public long cadastrarInicial(SubCategoria subCategoria) {
        long res = subCategoriasDAO.cadastroInicial(subCategoria);
        return res;
    }
    public long cadastrar(SubCategoria subCategoria) {
        long res = subCategoriasDAO.cadastrar(subCategoria);
        return res;
    }

    public ArrayList<SubCategoria> listarSubCategorias(long idUsuario, long idCategoria) {
        ArrayList<SubCategoria> subCategorias = subCategoriasDAO.getSubcategorias(idUsuario,idCategoria);
        return subCategorias;
    }
    public ArrayList<SubCategoria> listarAllSubCategorias(long idUsuario, long idCategoria) {
        ArrayList<SubCategoria> subCategorias = subCategoriasDAO.getAllSubcategorias(idUsuario,idCategoria);
        return subCategorias;
    }

    public SubCategoria getSubCategoria(long idSubCategoria) {
        SubCategoria subCategoria = subCategoriasDAO.getSubCategoria(idSubCategoria);
        return  subCategoria;
    }
    public SubCategoria nomeCategoriaExist(long idUsuario, String nomeSubcategoria) {
        SubCategoria subCategoria = subCategoriasDAO.nomeCategoriaExist(idUsuario, nomeSubcategoria);
        return  subCategoria;
    }
    public byte[] bitmapToByteArray(int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(WallotApp.getContext().getResources(), drawable);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

    public void alterarDados(SubCategoria subCategoriaEditada) {
        SubCategoria subCategoriaSessao = SessaoSubCategoria.instance.getSubCategoria();
        if(!subCategoriaEditada.getNome().isEmpty() && subCategoriaSessao.getNome() != subCategoriaEditada.getNome()){
            subCategoriaSessao.setNome(subCategoriaEditada.getNome());
            subCategoriasDAO.alterarNome(subCategoriaSessao);
        }
        if(subCategoriaSessao.getIcone() != subCategoriaEditada.getIcone()){
            subCategoriaSessao.setIcone(subCategoriaEditada.getIcone());
            subCategoriasDAO.alterarIcone(subCategoriaSessao);
        }
        if(subCategoriaSessao.getFkCategoria() != subCategoriaEditada.getFkCategoria()){
            //Log.d("ENTROUAQUI:", subCategoriaSessao.toString() + subCategoriaEditada.toString());
            subCategoriaSessao.setFkCategoria(subCategoriaEditada.getFkCategoria());
            subCategoriasDAO.alteraCategoria(subCategoriaSessao);
            transacaoDAO.alterarSubcategoria(subCategoriaSessao.getId());

            //muda nas transacoes para setFkSubcategoria(1)sem subcategoria
        }
    }
    public void deletarSubCategoria(SubCategoria subCategoria){
        transacaoDAO.alterarSubcategoria(subCategoria.getId());
        subCategoriasDAO.deletarSubcategoria(subCategoria);
    }
}

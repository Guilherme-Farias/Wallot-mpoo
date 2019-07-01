package com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia.CategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.app.WallotApp;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CategoriaServices {
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    //cadastra as categorias do usuario(ainda não disponibilizado para o usuario)
    public long cadastrar(Categoria categoria) {
        long res = categoriaDAO.cadastrar(categoria);
        return res;
    }
    //pre cadastra as nossas categorias já selecionadas
    public long cadastroInicial(Categoria categoria) {
        long res = categoriaDAO.cadastroInicial(categoria);
        return res;
    }

    //pega todas as categorias do usuario menos a categoria "Sem categoria"
    public ArrayList<Categoria> listarCategorias(long idUsuario) {
        return categoriaDAO.getCategorias(idUsuario, false);
    }

    //pega todas as categorias do usuario
    public ArrayList<Categoria> listarAllCategorias(long idUsuario){
        return categoriaDAO.getCategorias(idUsuario, true);
    }

    //pega a categoria específica
    public Categoria getCategoria(long idCategoria) {
        return  categoriaDAO.getCategoria(idCategoria);
    }

    //transforma o byteArray em um Drawable
    public byte[] bitmapToByteArray(int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(WallotApp.getContext().getResources(), drawable);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}

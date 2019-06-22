package com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia.CategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.app.WallotApp;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CategoriaServices {
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public ArrayList<Categoria> listarCategorias(long idUsuario) {
        ArrayList<Categoria> categorias = categoriaDAO.getCategorias(idUsuario);
        return categorias;
    }

    public ArrayList<Categoria> listarAllCategorias(long idUsuario){
        ArrayList<Categoria> categorias = categoriaDAO.getAllCategorias(idUsuario);
        return categorias;
    }

    public Categoria getCategoria(long idCategoria) {
        Categoria categoria = categoriaDAO.getCategoria(idCategoria);
        return  categoria;
    }

    public byte[] bitmapToByteArray(int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(WallotApp.getContext().getResources(), drawable);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

}

package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.app.WallotApp;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.persistencia.SubCategoriaDAO;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SubCategoriaServices {
    private SubCategoriaDAO subCategoriasDAO = new SubCategoriaDAO();

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

    public byte[] bitmapToByteArray(int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(WallotApp.getContext().getResources(), drawable);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }
}

package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.persistencia.SubCategoriaDAO;

import java.util.ArrayList;

public class SubCategoriaServices {
    private SubCategoriaDAO subCategoriasDAO = new SubCategoriaDAO();


    /*private long cadastrar(SubCategoria subCategoria) {
        long res = subCategoriasDAO.cadastrar(subCategoria);
        return res;
    }*/

    public ArrayList<SubCategoria> listarSubCategorias(long idUsuario, long idCategoria) {
        ArrayList<SubCategoria> subCategorias = subCategoriasDAO.getSubcategorias(idUsuario,idCategoria);
        return subCategorias;
    }
    public ArrayList<SubCategoria> listarAllSubCategorias(long idUsuario, long idCategoria) {
        ArrayList<SubCategoria> subCategorias = subCategoriasDAO.getAllSubcategorias(idUsuario,idCategoria);
        return subCategorias;
    }
}

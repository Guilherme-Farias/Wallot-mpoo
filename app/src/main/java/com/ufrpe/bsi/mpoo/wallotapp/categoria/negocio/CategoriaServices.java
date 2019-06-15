package com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia.CategoriaDAO;

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

}

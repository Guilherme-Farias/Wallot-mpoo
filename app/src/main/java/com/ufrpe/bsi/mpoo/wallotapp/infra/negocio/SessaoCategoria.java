package com.ufrpe.bsi.mpoo.wallotapp.infra.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;

import java.util.HashMap;
import java.util.Map;

public class SessaoCategoria {
    public static final SessaoCategoria instance = new SessaoCategoria();


    private Map<String, Object> values = new HashMap<>();

    public Categoria getCategoria(){return (Categoria) values.get("sessao.Categoria");}
    public void setCategoria(Categoria categoria){setValue("sessao.Categoria", categoria);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

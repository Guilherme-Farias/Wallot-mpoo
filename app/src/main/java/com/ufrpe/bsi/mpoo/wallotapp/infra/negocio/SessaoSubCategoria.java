package com.ufrpe.bsi.mpoo.wallotapp.infra.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;

import java.util.HashMap;
import java.util.Map;

public class SessaoSubCategoria {
    public static final SessaoSubCategoria instance = new SessaoSubCategoria();


    private Map<String, Object> values = new HashMap<>();

    public SubCategoria getSubCategoria(){return (SubCategoria) values.get("sessao.SubCategoria");}
    public void setSubCategoria(SubCategoria subCategoria){setValue("sessao.SubCategoria", subCategoria);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

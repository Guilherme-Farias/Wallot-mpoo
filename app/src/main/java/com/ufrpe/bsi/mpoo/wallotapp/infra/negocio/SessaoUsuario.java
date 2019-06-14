package com.ufrpe.bsi.mpoo.wallotapp.infra.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.util.HashMap;
import java.util.Map;

public class SessaoUsuario {
    public static final SessaoUsuario instance = new SessaoUsuario();

    private Map<String, Object> values = new HashMap<>();


    public Usuario getUsuario(){return (Usuario) values.get("sessao.Usuario");}
    public void setUsuario(Usuario usuario){setValue("sessao.Usuario", usuario);}


    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

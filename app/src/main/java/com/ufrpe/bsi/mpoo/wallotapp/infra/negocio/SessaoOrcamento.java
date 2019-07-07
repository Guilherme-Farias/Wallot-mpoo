package com.ufrpe.bsi.mpoo.wallotapp.infra.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.orcamento.dominio.Orcamento;

import java.util.HashMap;
import java.util.Map;

public class SessaoOrcamento {
    public static final SessaoOrcamento instance = new SessaoOrcamento();
    private Map<String, Object> values = new HashMap<>();

    public Orcamento getOrcamento(){return (Orcamento) values.get("sessao.Orcamento");}
    public void setOrcamento(Orcamento orcamento){setValue("sessao.Orcamento", orcamento);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

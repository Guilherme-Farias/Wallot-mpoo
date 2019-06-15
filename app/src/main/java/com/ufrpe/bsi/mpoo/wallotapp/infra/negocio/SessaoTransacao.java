package com.ufrpe.bsi.mpoo.wallotapp.infra.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;

import java.util.HashMap;
import java.util.Map;

public class SessaoTransacao {

    public static final SessaoTransacao instance = new SessaoTransacao();


    private Map<String, Object> values = new HashMap<>();

    public Transacao getTransacao(){return (Transacao) values.get("sessao.Transacao");}
    public void setTransacao(Transacao transacao){setValue("sessao.Transacao", transacao);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

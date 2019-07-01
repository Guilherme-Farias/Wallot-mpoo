package com.ufrpe.bsi.mpoo.wallotapp.infra.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;

import java.util.HashMap;
import java.util.Map;

public class SessaoParcela {
    public static final SessaoParcela instance = new SessaoParcela();
    private Map<String, Object> values = new HashMap<>();

    public Parcela getParcela(){return (Parcela) values.get("sessao.Parcela");}
    public void setParcela(Parcela parcela){setValue("sessao.Parcela", parcela);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

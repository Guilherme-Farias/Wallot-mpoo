package com.ufrpe.wallot_app.infra;

import java.util.HashMap;
import java.util.Map;

public class SessaoTipoPagamento {
    public static final SessaoTipoPagamento instance = new SessaoTipoPagamento();


    private Map<String, Object> values = new HashMap<>();

    public int getTipo(){return (int) values.get("sessao.int");}
    public void setTipo(int x){setValue("sessao.int", x);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

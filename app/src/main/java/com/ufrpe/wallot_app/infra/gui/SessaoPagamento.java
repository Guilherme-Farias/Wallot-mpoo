package com.ufrpe.wallot_app.infra.gui;

import com.ufrpe.wallot_app.pagamento.dominio.Pagamento;

import java.util.HashMap;
import java.util.Map;

public class SessaoPagamento {
    public static final SessaoPagamento instance = new SessaoPagamento();
    private Map<String, Object> values = new HashMap<>();

    public Pagamento getPagamento(){return (Pagamento) values.get("sessao.Pagamento");}
    public void setPagamento(Pagamento pagamento){setValue("sessao.Pagamento", pagamento);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

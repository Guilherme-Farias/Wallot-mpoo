package com.ufrpe.bsi.mpoo.wallotapp.infra.negocio;

        import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;

        import java.util.HashMap;
        import java.util.Map;

public class SessaoConta {
    public static final SessaoConta instance = new SessaoConta();
    private Map<String, Object> values = new HashMap<>();

    public Conta getConta(){return (Conta) values.get("sessao.Conta");}
    public void setConta(Conta conta){setValue("sessao.Conta", conta);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

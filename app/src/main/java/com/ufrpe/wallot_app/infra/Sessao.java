package com.ufrpe.wallot_app.infra;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import com.ufrpe.wallot_app.usuario.dominio.Usuario;
import com.ufrpe.wallot_app.infra.app.WallotApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sessao {
    public static final Sessao instance = new Sessao();

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Map<String, Object> values = new HashMap<>();

    public Usuario getUsuario(){return (Usuario) values.get("sessao.Usuario");}
    public void setUsuario(Usuario usuario){setValue("sessao.Usuario", usuario);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public String getUltimoAcesso() {
        String res = (String) values.get("sessao.ultimoAcesso");
        return res != null ? res : "-";
    }

    public void updateAcesso() {
        SharedPreferences prefs = WallotApp.getContext().getSharedPreferences("sessao", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String date = DATE_FORMAT.format(new Date());
        String key = "sessao.ultimoAcesso";
        setValue(key, date);
        editor.putString(key, date);
        editor.apply();

    }

    public void reset() {
        this.values.clear();
        updateAcesso();
    }
}

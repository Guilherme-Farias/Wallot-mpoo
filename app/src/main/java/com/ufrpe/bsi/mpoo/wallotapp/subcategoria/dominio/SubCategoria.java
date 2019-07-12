package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

public class SubCategoria {
    private long id;
    private String nome;
    private byte[] icone;
    private Categoria categoria;
    private Usuario usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getIcone() {
        return icone;
    }

    public void setIcone(byte[] icone) {
        this.icone = icone;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //pega o byte e transforma em drawable
    public Drawable byteArrayToDrawable(byte[] byteArray) {return new BitmapDrawable(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));}

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }
}

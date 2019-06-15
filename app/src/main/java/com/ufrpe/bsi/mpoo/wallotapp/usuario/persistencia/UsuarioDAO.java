package com.ufrpe.bsi.mpoo.wallotapp.usuario.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

public class UsuarioDAO {
    private DBHelper dbHelper = new DBHelper();


    public long cadastrar(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(DBHelper.USUARIO_COL_NOME, usuario.getNome());
        values.put(DBHelper.USUARIO_COL_EMAIL, usuario.getEmail());
        values.put(DBHelper.USUARIO_COL_SENHA, usuario.getSenha());


        long res = db.insert(DBHelper.TABELA_USUARIO, null, values);
        db.close();
        return res;
    }

    private Usuario criaUsuario(Cursor cursor){
        int indexID = cursor.getColumnIndex(DBHelper.USUARIO_COL_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.USUARIO_COL_NOME);
        int indexEmail = cursor.getColumnIndex(DBHelper.USUARIO_COL_EMAIL);
        int indexSenha = cursor.getColumnIndex(DBHelper.USUARIO_COL_SENHA);
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(indexID));
        usuario.setNome(cursor.getString(indexNome));
        usuario.setEmail(cursor.getString(indexEmail));
        usuario.setSenha(cursor.getString(indexSenha));
        return usuario;
    }

    public Usuario getUsuario(String email){
        String sql = "SELECT * FROM " + DBHelper.TABELA_USUARIO + " WHERE " + DBHelper.USUARIO_COL_EMAIL + " LIKE ?;";
        String[] args = {email};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        Usuario usuario = null;
        if(cursor.moveToFirst()){
            usuario = criaUsuario(cursor);
        }
        cursor.close();
        db.close();
        return usuario;
    }

    public Usuario getUsuario(String email, String senha) {
        Usuario usuario = getUsuario(email);
        if (usuario != null && !senha.equals(usuario.getSenha())) {
            return null;
        }
        return usuario;
    }

    public void alterarEmail(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.USUARIO_COL_EMAIL, usuario.getEmail());
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.USUARIO_COL_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    public void alterarSenha(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.USUARIO_COL_SENHA, usuario.getSenha());
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.USUARIO_COL_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    public void alterarNome(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.USUARIO_COL_NOME, usuario.getNome());
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.USUARIO_COL_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }
}

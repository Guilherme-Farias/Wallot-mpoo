package com.ufrpe.bsi.mpoo.wallotapp.usuario.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

public class UsuarioDAO {
    private DBHelper dbHelper = new DBHelper();

    //cadastra o usuario
    public long cadastrar(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(usuario);
        long res = db.insert(DBHelper.TABELA_USUARIO, null, values);
        db.close();
        return res;
    }

    //pega os valores do objeto para setar no banco
    private ContentValues getContentValues(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.USUARIO_COL_NOME, usuario.getNome());
        values.put(DBHelper.USUARIO_COL_EMAIL, usuario.getEmail());
        values.put(DBHelper.USUARIO_COL_SENHA, usuario.getSenha());
        return values;
    }

    //cria dado objeto com o cursor vindo do banco
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

    public Usuario getUsuario(long idUsuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Usuario usuario = null;
        String sql = " SELECT * FROM " + DBHelper.TABELA_USUARIO + " WHERE " + DBHelper.USUARIO_COL_ID + " LIKE ?;";
        String[] args = {String.valueOf(idUsuario)};
        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            usuario = criaUsuario(cursor);
        }
        cursor.close();
        db.close();
        return usuario;
    }

    //pega o usuario pelo email
    public Usuario getUsuario(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Usuario usuario = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_USUARIO + " WHERE " + DBHelper.USUARIO_COL_EMAIL + " LIKE ?;";
        String[] args = {email};
        Cursor cursor = db.rawQuery(sql, args);
        if(cursor.moveToFirst()){
            usuario = criaUsuario(cursor);
        }
        cursor.close();
        db.close();
        return usuario;
    }

    //pega o email verifica se existe se existe se existir verfica se a senha está certa
    public Usuario getUsuario(String email, String senha) {
        Usuario usuario = getUsuario(email);
        if (usuario != null && !senha.equals(usuario.getSenha())) {
            return null;
        }
        return usuario;
    }

    //altera o email
    public void alterarEmail(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.USUARIO_COL_EMAIL, usuario.getEmail());
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.USUARIO_COL_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    //altera a senha
    public void alterarSenha(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.USUARIO_COL_SENHA, usuario.getSenha());
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.USUARIO_COL_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    //altera o nome
    public void alterarNome(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.USUARIO_COL_NOME, usuario.getNome());
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.USUARIO_COL_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }
}

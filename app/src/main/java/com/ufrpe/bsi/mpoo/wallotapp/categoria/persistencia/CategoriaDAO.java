package com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;

import java.util.ArrayList;

public class CategoriaDAO {

    private DBHelper dbHelper = new DBHelper();

    public long cadastrar(Categoria categoria) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORIA_COL_NOME, categoria.getNome());
        /*values.put(DBHelper.CATEGORIA_COL_ICONE, categoria.getIcone());*/
        values.put(DBHelper.CATEGORIA_FK_USUARIO, categoria.getFkUsuario());
        long res = db.insert(DBHelper.TABELA_CATEGORIA, null, values);
        db.close();
        return res;
    }

    public Categoria criaCategoria(Cursor cursor) {
        Categoria categoria = new Categoria();
        int indexId = cursor.getColumnIndex(DBHelper.CATEGORIA_COL_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.CATEGORIA_COL_NOME);
        /*int indexIcone = cursor.getColumnIndex(DBHelper.CATEGORIA_COL_ICONE);*/
        int indexUsuario = cursor.getColumnIndex(DBHelper.CATEGORIA_FK_USUARIO);
        categoria.setId(cursor.getLong(indexId));
        categoria.setNome(cursor.getString(indexNome));
        /*categoria.setIcone(cursor.getBlob(indexIcone));*/
        categoria.setFkUsuario(cursor.getLong(indexUsuario));
        return categoria;
    }


    public ArrayList<Categoria> getCategorias (long usuarioId) {
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+ DBHelper.TABELA_CATEGORIA + " WHERE " +DBHelper.CATEGORIA_FK_USUARIO + " = ? " + " OR " + DBHelper.CATEGORIA_FK_USUARIO + " IS NULL";
        String[] args = {String.valueOf(usuarioId)};
        Cursor cursor = db.rawQuery(query, args);
        if(cursor.moveToFirst()){
            do {
                Categoria categoria = criaCategoria(cursor);
                categorias.add(categoria);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }

}

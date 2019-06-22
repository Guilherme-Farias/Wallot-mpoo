package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;

import java.util.ArrayList;

public class SubCategoriaDAO {
    private DBHelper dbHelper = new DBHelper();

    public long cadastrar(SubCategoria subCategoria) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBCATEGORIA_COL_NOME, subCategoria.getNome());
        values.put(DBHelper.SUBCATEGORIA_COL_ICONE, subCategoria.getIcone());
        values.put(DBHelper.SUBCATEGORIA_FK_CATEGORIA, subCategoria.getFkCategoria());
        if (subCategoria.getFkUsuario() == -1) {
            values.putNull(DBHelper.SUBCATEGORIA_FK_USUARIO);
        } else {
            values.put(DBHelper.SUBCATEGORIA_FK_USUARIO, subCategoria.getFkUsuario());
        }
        long res = db.insert(DBHelper.TABELA_SUBCATEGORIA, null, values);
        db.close();
        return res;
    }
    public SubCategoria criaSubCategoria(Cursor cursor) {
        SubCategoria subCategoria = new SubCategoria();
        int indexId = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_COL_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_COL_NOME);
        int indexIcone = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_COL_ICONE);
        int indexCategoria = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_FK_CATEGORIA);
        int indexUsuario = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_FK_USUARIO);
        subCategoria.setId(cursor.getLong(indexId));
        subCategoria.setNome(cursor.getString(indexNome));
        subCategoria.setIcone(cursor.getBlob(indexIcone));
        subCategoria.setFkCategoria(cursor.getLong(indexCategoria));
        subCategoria.setFkUsuario(cursor.getLong(indexUsuario));
        return subCategoria;
    }

    public ArrayList<SubCategoria> getSubcategorias(long usuarioId, long idCategoria) {
        ArrayList<SubCategoria> subCategorias = new ArrayList<SubCategoria>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+ DBHelper.TABELA_SUBCATEGORIA + " WHERE " + DBHelper.SUBCATEGORIA_FK_CATEGORIA +" =? " + " AND  (" +DBHelper.SUBCATEGORIA_FK_USUARIO + " = ? " + " OR " + DBHelper.SUBCATEGORIA_FK_USUARIO + " IS NULL)";
        String[] args = {String.valueOf(idCategoria), String.valueOf(usuarioId)};
        Cursor cursor = db.rawQuery(query, args);
        if(cursor.moveToFirst()){
            do {
                SubCategoria subcategoria = criaSubCategoria(cursor);
                subCategorias.add(subcategoria);
            } while (cursor.moveToNext());
        }
        return subCategorias;
    }

    public ArrayList<SubCategoria> getAllSubcategorias(long idUsuario, long idCategoria) {
        ArrayList<SubCategoria> subCategorias = new ArrayList<SubCategoria>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DBHelper.TABELA_SUBCATEGORIA + " WHERE " + DBHelper.SUBCATEGORIA_FK_CATEGORIA + " =?" +  " OR " + DBHelper.SUBCATEGORIA_FK_CATEGORIA + " = 1" + " AND "  + "(" + DBHelper.SUBCATEGORIA_FK_USUARIO + " =?" + " OR " + DBHelper.SUBCATEGORIA_FK_USUARIO + " IS NULL)";
        String[] args = {String.valueOf(idCategoria), String.valueOf(idUsuario)};
        Cursor cursor = db.rawQuery(query, args);
        if(cursor.moveToFirst()){
            do {
                SubCategoria subcategoria = criaSubCategoria(cursor);
                subCategorias.add(subcategoria);
            } while (cursor.moveToNext());
        }
        return subCategorias;
    }

    public SubCategoria getSubCategoria(long idSubcategoria){
        String sql = "SELECT * FROM " + DBHelper.TABELA_SUBCATEGORIA + " WHERE " + DBHelper.SUBCATEGORIA_COL_ID + " LIKE ?;";
        String[] args = {String.valueOf(idSubcategoria)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        SubCategoria subCategoria = null;
        if(cursor.moveToFirst()){
            subCategoria = criaSubCategoria(cursor);
        }
        cursor.close();
        db.close();
        return subCategoria;
    }


}

package com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;

import java.util.ArrayList;

public class CategoriaDAO {

    private DBHelper dbHelper = new DBHelper();

    //pega os dados da categoria e cadastra
    public long cadastrar(Categoria categoria) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(categoria);
        values.put(DBHelper.CATEGORIA_FK_USUARIO, categoria.getFkUsuario());
        long res = db.insert(DBHelper.TABELA_CATEGORIA, null, values);
        db.close();
        return res;
    }
    //pega ps dados da categoria padrao e inicializa com o FK do usuario como null para que possa ser acessada por qualquer usuario
    public long cadastroInicial(Categoria categoria){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(categoria);
        values.putNull(DBHelper.CATEGORIA_FK_USUARIO);
        long res = db.insert(DBHelper.TABELA_CATEGORIA, null, values);
        db.close();
        return res;
    }

    //pega valores padroes de ambos os cadastros
    private ContentValues getContentValues(Categoria categoria) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORIA_COL_NOME, categoria.getNome());
        values.put(DBHelper.CATEGORIA_COL_ICONE, categoria.getIcone());
        return values;
    }

    //constroi uma categoria pegando os seus valores do bando de dados
    private Categoria criaCategoria(Cursor cursor) {
        Categoria categoria = new Categoria();
        int indexId = cursor.getColumnIndex(DBHelper.CATEGORIA_COL_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.CATEGORIA_COL_NOME);
        int indexIcone = cursor.getColumnIndex(DBHelper.CATEGORIA_COL_ICONE);
        int indexUsuario = cursor.getColumnIndex(DBHelper.CATEGORIA_FK_USUARIO);
        categoria.setId(cursor.getLong(indexId));
        categoria.setNome(cursor.getString(indexNome));
        categoria.setIcone(cursor.getBlob(indexIcone));
        categoria.setFkUsuario(cursor.getLong(indexUsuario));
        return categoria;
    }

    //pega a lista das categorias do usuario
    public ArrayList<Categoria> getCategorias (long usuarioId, boolean querTodas) {
        ArrayList<Categoria> categorias = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+ DBHelper.TABELA_CATEGORIA + " WHERE " +DBHelper.CATEGORIA_FK_USUARIO + " = ? " + " OR " + DBHelper.CATEGORIA_FK_USUARIO + " IS NULL";
        if(querTodas) {query += " OR " + DBHelper.CATEGORIA_FK_USUARIO + " = 0";}//pega a categoria "sem categoria"
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

    //pega uma categoria específica
    public Categoria getCategoria(long idCategoria) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_CATEGORIA + " WHERE " + DBHelper.CATEGORIA_COL_ID + " = ?;";
        String[] args = {String.valueOf(idCategoria)};
        Cursor cursor = db.rawQuery(sql, args);
        Categoria categoria = null;
        if(cursor.moveToFirst()){
            categoria = criaCategoria(cursor);
        }
        cursor.close();
        db.close();
        return categoria;
    }
}

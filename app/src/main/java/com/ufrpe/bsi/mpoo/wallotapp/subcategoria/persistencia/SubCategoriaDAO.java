package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia.CategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.persistencia.UsuarioDAO;

import java.util.ArrayList;

public class SubCategoriaDAO {
    private DBHelper dbHelper = new DBHelper();

    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    //cadastra subcategoria do usuario
    public long cadastrar(SubCategoria subCategoria) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(subCategoria);
        values.put(DBHelper.SUBCATEGORIA_FK_USUARIO, subCategoria.getUsuario().getId());
        long res = db.insert(DBHelper.TABELA_SUBCATEGORIA, null, values);
        db.close();
        return res;
    }

    //controi o objeto subcategoria a partir de um cursor do banco de dados
    private SubCategoria criaSubCategoria(Cursor cursor) {
        SubCategoria subCategoria = new SubCategoria();
        int indexId = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_COL_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_COL_NOME);
        int indexIcone = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_COL_ICONE);
        int indexCategoria = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_FK_CATEGORIA);
        int indexUsuario = cursor.getColumnIndex(DBHelper.SUBCATEGORIA_FK_USUARIO);
        subCategoria.setId(cursor.getLong(indexId));
        subCategoria.setNome(cursor.getString(indexNome));
        subCategoria.setIcone(cursor.getBlob(indexIcone));
        subCategoria.setCategoria(categoriaDAO.getCategoria(cursor.getLong(indexCategoria)));
        subCategoria.setUsuario(usuarioDAO.getUsuario(cursor.getLong(indexUsuario)));
        return subCategoria;
    }

    //pega os valores padroes dos objetos
    private ContentValues getContentValues(SubCategoria subCategoria) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBCATEGORIA_COL_NOME, subCategoria.getNome());
        values.put(DBHelper.SUBCATEGORIA_COL_ICONE, subCategoria.getIcone());
        values.put(DBHelper.SUBCATEGORIA_FK_CATEGORIA, subCategoria.getCategoria().getId());
        return values;
    }

    //pega categorias(todas/sem categoria + todas)
    public ArrayList<SubCategoria> getSubcategorias(long usuarioId, long idCategoria, boolean querTodas) {
        ArrayList<SubCategoria> subCategorias = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DBHelper.TABELA_SUBCATEGORIA + " WHERE (" + DBHelper.SUBCATEGORIA_FK_USUARIO + " = ? " + " OR " + DBHelper.SUBCATEGORIA_FK_USUARIO + " IS NULL) AND " + DBHelper.SUBCATEGORIA_FK_CATEGORIA + " =?" ;
        if (querTodas){
            query += " OR " + DBHelper.SUBCATEGORIA_FK_CATEGORIA + " = 1";
        }
        String[] args = {String.valueOf(usuarioId), String.valueOf(idCategoria)};
        Cursor cursor = db.rawQuery(query, args);
        if(cursor.moveToFirst()){
            do {
                SubCategoria subcategoria = criaSubCategoria(cursor);
                subCategorias.add(subcategoria);
            } while (cursor.moveToNext());
        }
        closeArgs(db, cursor);
        return subCategorias;
    }

    //pega uma subcategoria especifica com o id dela
    public SubCategoria getSubCategoria(long idSubcategoria){
        String sql = "SELECT * FROM " + DBHelper.TABELA_SUBCATEGORIA + " WHERE " + DBHelper.SUBCATEGORIA_COL_ID + " = ?;";
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

    //verifica se nome da categoria existe ou não
    public SubCategoria nomeCategoriaExist(long idUsuario, String nomeSubcategoria){
        String sql = "SELECT * FROM " + DBHelper.TABELA_SUBCATEGORIA + " WHERE (" + DBHelper.SUBCATEGORIA_FK_USUARIO + " = ? OR " + DBHelper.SUBCATEGORIA_FK_USUARIO + " IS NULL) AND " + DBHelper.SUBCATEGORIA_COL_NOME + " LIKE ?" ;
        String[] args = {String.valueOf(idUsuario), nomeSubcategoria};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        SubCategoria subCategoria = null;
        if(cursor.moveToFirst()){
            subCategoria = criaSubCategoria(cursor);
        }
        closeArgs(db,cursor);
        return subCategoria;
    }

    //cadastra as subcategorias já construidas pela equipe
    public long cadastroInicial(SubCategoria subCategoria){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(subCategoria);
        values.putNull(DBHelper.SUBCATEGORIA_FK_USUARIO);
        long res = db.insert(DBHelper.TABELA_SUBCATEGORIA, null, values);
        db.close();
        return res;
    }

    //altera o icone da subcategoria
    public void alterarIcone(SubCategoria subCategoria) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBCATEGORIA_COL_ICONE, subCategoria.getIcone());
        db.update(DBHelper.TABELA_SUBCATEGORIA,values,DBHelper.SUBCATEGORIA_COL_ID + " = ?",new String[]{String.valueOf(subCategoria.getId())});
        db.close();
    }

    //altera o nome da subcategoria
    public void alterarNome(SubCategoria subCategoria) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBCATEGORIA_COL_NOME, subCategoria.getNome());
        db.update(DBHelper.TABELA_SUBCATEGORIA,values,DBHelper.SUBCATEGORIA_COL_ID + " = ?",new String[]{String.valueOf(subCategoria.getId())});
        db.close();
    }

    //altera a categoria pai dela
    public void alteraCategoria(SubCategoria subCategoria) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.SUBCATEGORIA_FK_CATEGORIA, subCategoria.getCategoria().getId());
        db.update(DBHelper.TABELA_SUBCATEGORIA,values,DBHelper.SUBCATEGORIA_COL_ID + " = ?",new String[]{String.valueOf(subCategoria.getId())});
        db.close();
    }

    //deleta subcategoria
    public void deletarSubcategoria(SubCategoria subCategoria){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM "+ DBHelper.TABELA_SUBCATEGORIA + " WHERE " + DBHelper.SUBCATEGORIA_COL_ID + " = " + subCategoria.getId() + ";" ;
        db.execSQL(sql);
        db.close();
    }

    //fecha o db e o cursor
    private void closeArgs(SQLiteDatabase db, Cursor cursor) {
        db.close();
        cursor.close();
    }

}

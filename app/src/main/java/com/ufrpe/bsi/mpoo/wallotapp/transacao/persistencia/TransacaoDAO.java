package com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.WallotAppException;
import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoDeStatusTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransacaoDAO {
    DBHelper dbHelper = new DBHelper();
    SimpleDateFormat padraoDataSQLite = new SimpleDateFormat("yyyyMMdd");

    public ArrayList<TipoTransacao> getTiposTransacao(){
        ArrayList<TipoTransacao> tiposTransacao = new ArrayList<TipoTransacao>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+ DBHelper.TABELA_TIPO_TRANSACAO;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                TipoTransacao tipo = TipoTransacao.values()[cursor.getInt(cursor.getColumnIndex(DBHelper.TIPO_TRANSACAO_COL_ID))-1];
                tiposTransacao.add(tipo);
            } while (cursor.moveToNext());

        }
        return tiposTransacao;
    }

    public long cadastrarTransacao(Transacao transacao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.TRANSACAO_COL_TITULO, transacao.getTitulo());
        values.put(DBHelper.TRANSACAO_COL_VALOR, transacao.getValor().toString());
        values.put(DBHelper.TRANSACAO_COL_PARCELAS, String.valueOf(transacao.getQntParcelas()));
        values.put(DBHelper.TRANSACAO_COL_FK_CONTA, String.valueOf(transacao.getFkConta()));
        values.put(DBHelper.TRANSACAO_COL_FK_CATEGORIA, String.valueOf(transacao.getFkCategoria()));
        values.put(DBHelper.TRANSACAO_COL_FK_SUBCATEGORIA, String.valueOf(transacao.getFkSubCategoria()));
        values.put(DBHelper.TRANSACAO_COL_FK_USUARIO, String.valueOf(transacao.getFkUsuario()));
        values.put(DBHelper.TRANSACAO_COL_TIPO_TRANSACAO, String.valueOf(transacao.getTipoTransacao().ordinal() + 1));
        long res = db.insert(DBHelper.TABELA_TRANSACAO, null, values);
        db.close();
        return res;
    }

    public long cadastrarParcela(Parcela parcela) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String dataString = padraoDataSQLite.format(parcela.getDataTransacao());
        values.put(DBHelper.PARCELA_COL_VALOR, parcela.getValorParcela().toString());
        values.put(DBHelper.PARCELA_COL_DATE, dataString);
        values.put(DBHelper.PARCELA_NUMERO_PARCELA, String.valueOf(parcela.getNumeroParcela()));
        values.put(DBHelper.PARCELA_COL_FK_TRANSACAO, String.valueOf(parcela.getFkTransacao()));
        values.put(DBHelper.PARCELA_TIPO_DE_STATUS, String.valueOf(parcela.getTipoDeStatusTransacao().ordinal() + 1));
        long res = db.insert(DBHelper.TABELA_PARCELA, null, values);
        db.close();
        return res;

    }

    public ArrayList<Transacao> getTransacaosPorData(long idUsuario) {
        ArrayList<Transacao> transacaos = new ArrayList<Transacao>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " +  DBHelper.TABELA_TRANSACAO + " INNER JOIN " + DBHelper.TABELA_PARCELA  + " ON " + DBHelper.TRANSACAO_COL_ID + " = " + DBHelper.PARCELA_COL_FK_TRANSACAO + " AND " + DBHelper.TRANSACAO_COL_FK_USUARIO  + " =?" + " ORDER BY " + DBHelper.PARCELA_COL_DATE;
        String[] args = {String.valueOf(idUsuario)};
        Cursor cursor = db.rawQuery(query,args);
        if (cursor.moveToFirst()){
            do {
                Transacao transacao = criaTransacao(cursor);
                transacaos.add(transacao);
            } while (cursor.moveToNext());

        }
        return transacaos;
    }

    private Transacao criaTransacao(Cursor cursor) {
        Transacao transacao = new Transacao();
        int indexId = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_ID);
        int indexTitulo = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_TITULO);
        int indexValor = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_VALOR);
        int indexQntParcelas = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_PARCELAS);
        int indexCategoria = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_FK_CATEGORIA);
        int indexSubCategoria = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_FK_SUBCATEGORIA);
        int indexTipoTransacao = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_TIPO_TRANSACAO);
        int indexFkUsuario = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_FK_USUARIO);
        int indexFkConta = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_FK_CONTA);
        transacao.setId(cursor.getLong(indexId));
        transacao.setTitulo(cursor.getString(indexTitulo));
        transacao.setValor(new BigDecimal(cursor.getString(indexValor)));
        transacao.setQntParcelas(cursor.getLong(indexQntParcelas));
        transacao.setFkCategoria(cursor.getLong(indexCategoria));
        transacao.setFkSubCategoria(cursor.getLong(indexSubCategoria));
        transacao.setTipoTransacao(TipoTransacao.values()[cursor.getInt(indexTipoTransacao) - 1]);
        transacao.setFkUsuario(cursor.getLong(indexFkUsuario));
        transacao.setFkConta(cursor.getLong(indexFkConta));

        return transacao;
    }

    private Parcela criaParcela(Cursor cursor) {
        Parcela parcela = new Parcela();
        int indexId = cursor.getColumnIndex(DBHelper.PARCELA_COL_ID);
        int indexValor = cursor.getColumnIndex(DBHelper.PARCELA_COL_VALOR);
        int indexNParcela = cursor.getColumnIndex(DBHelper.PARCELA_NUMERO_PARCELA);
        int indexData = cursor.getColumnIndex(DBHelper.PARCELA_COL_DATE);
        int indexTransacao = cursor.getColumnIndex(DBHelper.PARCELA_COL_FK_TRANSACAO);
        int indexTipoStatus = cursor.getColumnIndex(DBHelper.PARCELA_TIPO_DE_STATUS);
        parcela.setId(cursor.getLong(indexId));
        parcela.setValorParcela(new BigDecimal(cursor.getString(indexValor)));
        parcela.setNumeroParcela(cursor.getLong(indexNParcela));
        parcela.setTipoDeStatusTransacao(TipoDeStatusTransacao.values()[cursor.getInt(indexTipoStatus) - 1]);
        String datastr = cursor.getString(indexData);
        Date data = new Date();
        try {
            data = padraoDataSQLite.parse(datastr);
        } catch (Exception e) {
            new WallotAppException("A data inválida");
        }
        parcela.setDataTransacao(data);
        parcela.setFkTransacao(cursor.getLong(indexTransacao));
        return parcela;
    }

    public Transacao getTransacao(long idTransacao){
        Transacao transacao = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_TRANSACAO + " WHERE " + DBHelper.TRANSACAO_COL_ID + " LIKE ?;";
        String[] args = {String.valueOf(idTransacao)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        if(cursor.moveToFirst()){
            transacao = criaTransacao(cursor);
        }
        cursor.close();
        db.close();
        return transacao;
    }

    public ArrayList<Parcela> getParcelasPorData(long idUsuario) {
        ArrayList<Parcela> parcelas = new ArrayList<Parcela>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " +  DBHelper.TABELA_TRANSACAO + " INNER JOIN " + DBHelper.TABELA_PARCELA  + " ON " + DBHelper.TRANSACAO_COL_ID + " = " + DBHelper.PARCELA_COL_FK_TRANSACAO + " AND " + DBHelper.TRANSACAO_COL_FK_USUARIO  + " =?" + " ORDER BY " + DBHelper.PARCELA_COL_DATE;
        String[] args = {String.valueOf(idUsuario)};
        Cursor cursor = db.rawQuery(query,args);
        if (cursor.moveToFirst()){
            do {
                Parcela parcela = criaParcela(cursor);
                parcelas.add(parcela);
            } while (cursor.moveToNext());

        }
        return parcelas;
    }

    public void alterarSubcategoria(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + DBHelper.TABELA_TRANSACAO + " SET " + DBHelper.TRANSACAO_COL_FK_SUBCATEGORIA + " = 1 WHERE " + DBHelper.TRANSACAO_COL_FK_SUBCATEGORIA + " = " + id+";";
        //db.update(DBHelper.TABELA_TRANSACAO,values,DBHelper.TRANSACAO_COL_FK_SUBCATEGORIA + " = ?",new String[]{String.valueOf(id)});
        db.execSQL(sql);
        db.close();
    }
}

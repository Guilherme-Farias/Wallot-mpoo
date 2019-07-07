package com.ufrpe.bsi.mpoo.wallotapp.estatistica.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.mpoo.wallotapp.estatistica.dominio.Orcamento;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.WallotAppException;
import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrcamentoDAO {

    private DBHelper dbHelper = new DBHelper();
    private SimpleDateFormat padraoDataSQLite = new SimpleDateFormat("yyyyMMdd");

    public long cadastrarOrcamento(Orcamento orcamento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(orcamento);
        long res = db.insert(DBHelper.TABELA_ORCAMENTO, null, values);
        db.close();
        return res;
    }

    public Orcamento criaOrcamento(Cursor cursor) {
        Orcamento orcamento = new Orcamento();
        int indexId = cursor.getColumnIndex(DBHelper.ORCAMENTO_COL_ID);
        int indexGasto = cursor.getColumnIndex(DBHelper.ORCAMENTO_GASTO_ESTIMADO);
        int indexDataInicial = cursor.getColumnIndex(DBHelper.ORCAMENTO_DATA_INICIAL);
        int indexDataFinal = cursor.getColumnIndex(DBHelper.ORCAMENTO_DATA_FINAL);
        int indexUsuario = cursor.getColumnIndex(DBHelper.ORCAMENTO_FK_USUARIO);
        orcamento.setId(cursor.getLong(indexId));
        orcamento.setGastoEstimado(new BigDecimal(cursor.getString(indexGasto)));
        orcamento.setDataInicial(getDataDoSQLite(String.valueOf(cursor.getString(indexDataInicial))));
        orcamento.setDataFinal(getDataDoSQLite(String.valueOf(cursor.getString(indexDataFinal))));
        orcamento.setFkUsuario(cursor.getLong(indexUsuario));
        return orcamento;
    }

    private ContentValues getContentValues(Orcamento orcamento) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.ORCAMENTO_GASTO_ESTIMADO, orcamento.getGastoEstimado().toString());
        values.put(DBHelper.ORCAMENTO_DATA_INICIAL, Integer.parseInt(orcamento.getDataInicialFormatada()));
        values.put(DBHelper.ORCAMENTO_DATA_FINAL, Integer.parseInt(orcamento.getDataFinalFormatada()));
        values.put(DBHelper.ORCAMENTO_FK_USUARIO, String.valueOf(orcamento.getFkUsuario()));
        return values;
    }

    public Orcamento getOrcamento(long idOrcamento) {
        Orcamento orcamento = null;
        String sql = " SELECT * FROM " + DBHelper.TABELA_ORCAMENTO + " WHERE " + DBHelper.ORCAMENTO_COL_ID + " LIKE ?;";
        String[] args = {String.valueOf(idOrcamento)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            orcamento = criaOrcamento(cursor);
        }
        closeArgs(db, cursor);
        return orcamento;
    }

    public Orcamento getOrcamentoByIdUsuario(long idUsuario) {
        Orcamento orcamento = null;
        String sql = " SELECT * FROM " + DBHelper.TABELA_ORCAMENTO + " WHERE " + DBHelper.ORCAMENTO_FK_USUARIO + " LIKE ?;";
        String[] args = {String.valueOf(idUsuario)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            orcamento = criaOrcamento(cursor);
        }
        closeArgs(db, cursor);
        return orcamento;
    }

    private Date getDataDoSQLite(String datastr) {
        Date data = new Date();
        try {
            data = padraoDataSQLite.parse(datastr);
        } catch (Exception e) {
            new WallotAppException("A data inválida");
        }
        return data;
    }

    private void closeArgs(SQLiteDatabase db, Cursor cursor) {
        cursor.close();
        db.close();
    }

    public ArrayList<Orcamento> getOrcamentosPorData(long idUsuario) {
        return null;
    }
}

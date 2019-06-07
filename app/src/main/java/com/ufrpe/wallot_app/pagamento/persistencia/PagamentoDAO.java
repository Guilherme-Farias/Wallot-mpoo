package com.ufrpe.wallot_app.pagamento.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.infra.persistencia.DBHelper;
import com.ufrpe.wallot_app.pagamento.dominio.Pagamento;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PagamentoDAO {
    DBHelper dbHelper = new DBHelper();

    public long cadastraPagamento(Pagamento pagamento){
        long res;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.PAGAMENTO_COL_DESCRICAO, pagamento.getDescricao());
        values.put(DBHelper.PAGAMENTO_COL_VALOR, pagamento.getValor().toString());
        SimpleDateFormat newsdf = new SimpleDateFormat("yyyyMMdd");
        String dataString = newsdf.format(pagamento.getDataPagamento());
        values.put(DBHelper.PAGAMENTO_COL_DATA, dataString);
        values.put(DBHelper.PAGAMENTO_COL_TB_CONTA, pagamento.getTbConta());
        values.put(DBHelper.PAGAMENTO_COL_TB_USUARIO, pagamento.getTbUsuario());
        values.put(DBHelper.PAGAMENTO_COL_TB_TIPO_TRANSICAO, pagamento.getTbTipoPagamento());
        values.put(DBHelper.PAGAMENTO_COL_CATEGORIA, pagamento.getCategoria());
        values.put(DBHelper.PAGAMENTO_COL_SUBCATEGORIA, pagamento.getSubCategoria());
        res = db.insert(DBHelper.TABELA_PAGAMENTO, null, values);
        db.close();
        return res;
    }

    public Pagamento criaPagamento(Cursor cursor){
        Pagamento pagamento = new Pagamento();
        int indexId = cursor.getColumnIndex(DBHelper.PAGAMENTO_COL_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.PAGAMENTO_COL_DESCRICAO);
        int indexSaldo = cursor.getColumnIndex(DBHelper.PAGAMENTO_COL_VALOR);
        int indexUsuario = cursor.getColumnIndex(DBHelper.PAGAMENTO_COL_TB_USUARIO);
        int indexTipoPagamento = cursor.getColumnIndex(DBHelper.PAGAMENTO_COL_TB_TIPO_TRANSICAO);
        int indexTbConta = cursor.getColumnIndex(DBHelper.PAGAMENTO_COL_TB_CONTA);
        int indexCategoria = cursor.getColumnIndex(DBHelper.PAGAMENTO_COL_CATEGORIA);
        int indexSubCategoria = cursor.getColumnIndex(DBHelper.PAGAMENTO_COL_SUBCATEGORIA);
        int indexData = cursor.getColumnIndex(DBHelper.PAGAMENTO_COL_DATA);
        pagamento.setId(cursor.getLong(indexId));
        pagamento.setDescricao(cursor.getString(indexNome));
        pagamento.setValor(new BigDecimal(cursor.getString(indexSaldo)));
        pagamento.setTbUsuario(cursor.getLong(indexUsuario));
        pagamento.setTbTipoPagamento(cursor.getLong(indexTipoPagamento));
        pagamento.setTbConta(cursor.getLong(indexTbConta));
        pagamento.setCategoria(cursor.getString(indexCategoria));
        pagamento.setSubCategoria(cursor.getString(indexSubCategoria));
        String dataStr = cursor.getString(indexData);
        SimpleDateFormat newsdf = new SimpleDateFormat("yyyyMMdd");
        Date data = new Date();
        try {
            data = newsdf.parse(dataStr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        pagamento.setDataPagamento(data);

        return pagamento;
    }



    public ArrayList<Pagamento> getPagamentoOrdemData(long usuarioId) {
        ArrayList<Pagamento> pagamentos = new ArrayList<Pagamento>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DBHelper.TABELA_PAGAMENTO + " WHERE " + DBHelper.PAGAMENTO_COL_TB_USUARIO +
                " = ?" + "ORDER BY " + DBHelper.PAGAMENTO_COL_DATA  + " DESC;";
        String[] args = {String.valueOf(usuarioId)};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                Pagamento pagamento = criaPagamento(cursor);
                pagamentos.add(pagamento);
            } while (cursor.moveToNext());
        }
        return pagamentos;
    }
    /*private Cursor dbQuery(SQLiteDatabase db, long usarioId){
        String queryString = new StringBuilder()
                .append("SELECT * ")
                .append("  FROM %1$s ")
                .append("  WHERE %2$s ")
                .append(" LIKE %3$s ")
                .append("  ORDER BY %4$s ")
                .append("  DESC ")
                .append(");").toString();


        queryString = String.format(queryString, TABELA_PAGAMENTO, PAGAMENTO_COL_TB_USUARIO, usarioId, PAGAMENTO_COL_DATA);
        return db.execSraqQL(queryString);
    }*/


}

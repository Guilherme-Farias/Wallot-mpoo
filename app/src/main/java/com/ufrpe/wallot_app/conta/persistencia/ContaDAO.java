package com.ufrpe.wallot_app.conta.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.ufrpe.wallot_app.conta.dominio.Conta;
import com.ufrpe.wallot_app.infra.persistencia.DBHelper;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ContaDAO {
    private DBHelper dbHelper = new DBHelper();

    public long cadastraCarteira(Conta conta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_COL_NOME, conta.getNome());
        values.put(DBHelper.CONTA_COL_SALDO, conta.getSaldo().toString());
        values.put(DBHelper.CONTA_TB_USUARIO, conta.getTbUsuario());
        long res = db.insert(DBHelper.TABELA_CONTA, null, values);
        db.close();
        return res;
    }


    public Conta criaConta(Cursor cursor){
        Conta conta = new Conta();
        int indexId = cursor.getColumnIndex(DBHelper.CONTA_COL_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.CONTA_COL_NOME);
        int indexSaldo = cursor.getColumnIndex(DBHelper.CONTA_COL_SALDO);
        int indexUsuario= cursor.getColumnIndex(DBHelper.CONTA_TB_USUARIO);
        //pega todas os pagamentos
        conta.setId(cursor.getLong(indexId));
        conta.setNome(cursor.getString(indexNome));
        conta.setSaldo(new BigDecimal(cursor.getString(indexSaldo)));
        conta.setTbUsuario(cursor.getLong(indexUsuario));

        return conta;
    }

    public ArrayList<Conta> getContas(long usuarioId){
        ArrayList<Conta> contas = new ArrayList<Conta>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_CONTA+" WHERE "+DBHelper.CONTA_TB_USUARIO + " = ?";
        String[] args = {String.valueOf(usuarioId)};
        Cursor cursor = db.rawQuery(query, args);
        if(cursor.moveToFirst()){
            do {
                Conta conta = criaConta(cursor);
                contas.add(conta);
            } while (cursor.moveToNext());
        }
        return contas;
    }

    public void alterarNome(Conta conta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_COL_NOME, conta.getNome());
        db.update(DBHelper.TABELA_CONTA,values,DBHelper.CONTA_COL_ID + " = ?",new String[]{String.valueOf(conta.getId())});
        db.close();
    }

    public void alterarSaldo(Conta conta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_COL_SALDO,conta.getSaldo().toString());
        db.update(DBHelper.TABELA_CONTA,values,DBHelper.CONTA_COL_ID + " = ?",new String[]{String.valueOf(conta.getId())});
        db.close();
    }

    public Conta getConta(long idUsuario, String nomeConta){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_CONTA+" WHERE "+DBHelper.CONTA_TB_USUARIO + " = ?" + " and " + DBHelper.CONTA_COL_NOME + " = ?";
        String[] args = {String.valueOf(idUsuario), nomeConta};
        Cursor cursor = db.rawQuery(query, args);
        Conta conta = null;
        if(cursor.moveToFirst()){
            conta = criaConta(cursor);
        }
        cursor.close();
        db.close();
        return conta;
    }

    public Conta getContaById(long idConta){
        String sql = "SELECT * FROM " + DBHelper.TABELA_CONTA + " WHERE " + DBHelper.CONTA_COL_ID + " LIKE ?;";
        String[] args = {String.valueOf(idConta)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        Conta conta = null;
        if(cursor.moveToFirst()){
            conta = criaConta(cursor);
        }
        cursor.close();
        db.close();
        return conta;
    }



}

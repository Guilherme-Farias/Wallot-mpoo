package com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoConta;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.TipoEstadoConta;
import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ContaDAO {
    DBHelper dbHelper = new DBHelper();

    public long cadastraConta(Conta conta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_COL_NOME, conta.getNome());
        values.put(DBHelper.CONTA_COL_SALDO, conta.getSaldo().toString());
        values.put(DBHelper.CONTA_COL_COR, conta.getCor());
        values.put(DBHelper.CONTA_FK_USUARIO, conta.getFkUsuario());
        values.put(DBHelper.CONTA_FK_TIPO_CONTA, String.valueOf(conta.getTipoConta().ordinal() + 1));
        values.put(DBHelper.CONTA_FK_TIPO_ESTADO_CONTA, String.valueOf(conta.getTipoEstadoConta().ordinal() + 1));
        long res = db.insert(DBHelper.TABELA_CONTA, null, values);
        db.close();
        return res;
    }

    public Conta criaConta(Cursor cursor){
        Conta conta = new Conta();
        int indexId = cursor.getColumnIndex(DBHelper.CONTA_COL_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.CONTA_COL_NOME);
        int indexSaldo = cursor.getColumnIndex(DBHelper.CONTA_COL_SALDO);
        int indexUsuario= cursor.getColumnIndex(DBHelper.CONTA_FK_USUARIO);
        int indexCor = cursor.getColumnIndex(DBHelper.CONTA_COL_COR);
        int indexTipoConta = cursor.getColumnIndex(DBHelper.CONTA_FK_TIPO_CONTA);
        int indexTipoEstadoConta = cursor.getColumnIndex(DBHelper.CONTA_FK_TIPO_ESTADO_CONTA);
        conta.setId(cursor.getLong(indexId));
        conta.setNome(cursor.getString(indexNome));
        conta.setSaldo(new BigDecimal(cursor.getString(indexSaldo)));
        conta.setFkUsuario(cursor.getLong(indexUsuario));
        conta.setCor(cursor.getString(indexCor));
        conta.setTipoConta(TipoConta.values()[cursor.getInt(indexTipoConta) - 1]);
        conta.setTipoEstadoConta(TipoEstadoConta.values()[cursor.getInt(indexTipoEstadoConta) - 1]);
        return conta;
    }

    public ArrayList<Conta> getContas(long usuarioId){
        ArrayList<Conta> contas = new ArrayList<Conta>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_CONTA+" WHERE "+DBHelper.CONTA_FK_USUARIO + " = ?";
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

    public void alterarCor(Conta conta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_COL_COR,conta.getCor());
        db.update(DBHelper.TABELA_CONTA,values,DBHelper.CONTA_COL_ID + " = ?",new String[]{String.valueOf(conta.getId())});
        db.close();
    }
    public void alterarTipoEstadoConta(Conta conta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_FK_TIPO_ESTADO_CONTA,(conta.getTipoEstadoConta().ordinal() + 1));
        db.update(DBHelper.TABELA_CONTA,values,DBHelper.CONTA_COL_ID + " = ?",new String[]{String.valueOf(conta.getId())});
        db.close();
    }

    public Conta getConta(long idConta){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_CONTA+" WHERE " + DBHelper.CONTA_COL_ID + " = ?";
        String[] args = {String.valueOf(idConta)};
        Cursor cursor = db.rawQuery(query, args);
        Conta conta = null;
        if(cursor.moveToFirst()){
            conta = criaConta(cursor);
        }
        cursor.close();
        db.close();
        return conta;
    }

    public Conta getConta(long idUsuario, String nomeConta){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_CONTA+" WHERE "+DBHelper.CONTA_FK_USUARIO + " = ?" + " and " + DBHelper.CONTA_COL_NOME + " = ?";
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

    public ArrayList<TipoConta> getTiposConta(){
        ArrayList<TipoConta> tipoContas = new ArrayList<TipoConta>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_TIPO_CONTA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                tipoContas.add(TipoConta.values()[cursor.getColumnIndex(DBHelper.TIPO_CONTA_COL_ID)]);
            } while (cursor.moveToNext());
        }
        return tipoContas;
    }

}

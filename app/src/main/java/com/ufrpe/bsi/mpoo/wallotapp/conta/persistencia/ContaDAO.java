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
    private DBHelper dbHelper = new DBHelper();

    //Cadastra uma conta nova
    public long cadastraConta(Conta conta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValues(conta);
        long res = db.insert(DBHelper.TABELA_CONTA, null, values);
        db.close();
        return res;
    }

    //pega o content values de forma mais organizada
    private ContentValues getContentValues(Conta conta) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_COL_NOME, conta.getNome());
        values.put(DBHelper.CONTA_COL_SALDO, conta.getSaldo().toString());
        values.put(DBHelper.CONTA_FK_USUARIO, conta.getFkUsuario());
        values.put(DBHelper.CONTA_FK_TIPO_CONTA, String.valueOf(conta.getTipoConta().ordinal() + 1));
        values.put(DBHelper.CONTA_FK_TIPO_ESTADO_CONTA, String.valueOf(conta.getTipoEstadoConta().ordinal() + 1));
        return values;
    }

    //controi a conta
    private Conta criaConta(Cursor cursor){
        Conta conta = new Conta();
        int indexId = cursor.getColumnIndex(DBHelper.CONTA_COL_ID);
        int indexNome = cursor.getColumnIndex(DBHelper.CONTA_COL_NOME);
        int indexSaldo = cursor.getColumnIndex(DBHelper.CONTA_COL_SALDO);
        int indexUsuario= cursor.getColumnIndex(DBHelper.CONTA_FK_USUARIO);
        int indexTipoConta = cursor.getColumnIndex(DBHelper.CONTA_FK_TIPO_CONTA);
        int indexTipoEstadoConta = cursor.getColumnIndex(DBHelper.CONTA_FK_TIPO_ESTADO_CONTA);
        conta.setId(cursor.getLong(indexId));
        conta.setNome(cursor.getString(indexNome));
        conta.setSaldo(new BigDecimal(cursor.getString(indexSaldo)));
        conta.setFkUsuario(cursor.getLong(indexUsuario));
        conta.setTipoConta(TipoConta.values()[cursor.getInt(indexTipoConta) - 1]);
        conta.setTipoEstadoConta(TipoEstadoConta.values()[cursor.getInt(indexTipoEstadoConta) - 1]);
        return conta;
    }
    //pega contas(ativas/todas)
    public ArrayList<Conta> getContas(long usuarioId, boolean querAtivas){
        ArrayList<Conta> contas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_CONTA+" WHERE "+DBHelper.CONTA_FK_USUARIO + " = ?";
        if (querAtivas){query += " AND " + DBHelper.CONTA_FK_TIPO_ESTADO_CONTA + " = 1";}
        String[] args = {String.valueOf(usuarioId)};
        Cursor cursor = db.rawQuery(query, args);
        if(cursor.moveToFirst()){
            do {
                Conta conta = criaConta(cursor);
                contas.add(conta);
            } while (cursor.moveToNext());
        }
        closeArgs(db,cursor);
        return contas;
    }

    //altera o nome da conta
    public void alterarNome(Conta conta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_COL_NOME, conta.getNome());
        db.update(DBHelper.TABELA_CONTA,values,DBHelper.CONTA_COL_ID + " = ?",new String[]{String.valueOf(conta.getId())});
        db.close();
    }

    //altera o saldo no bancoz
    public void alterarSaldo(Conta conta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_COL_SALDO,conta.getSaldo().toString());
        db.update(DBHelper.TABELA_CONTA,values,DBHelper.CONTA_COL_ID + " = ?",new String[]{String.valueOf(conta.getId())});
        db.close();
    }

    //altera se está ativo ou inativo
    public void alterarTipoEstadoConta(Conta conta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_FK_TIPO_ESTADO_CONTA,(conta.getTipoEstadoConta().ordinal() + 1));
        db.update(DBHelper.TABELA_CONTA,values,DBHelper.CONTA_COL_ID + " = ?",new String[]{String.valueOf(conta.getId())});
        db.close();
    }

    //altera o tipo de conta
    public void alterarTipoConta(Conta conta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CONTA_FK_TIPO_CONTA,(conta.getTipoConta().ordinal() + 1));
        db.update(DBHelper.TABELA_CONTA,values,DBHelper.CONTA_COL_ID + " = ?",new String[]{String.valueOf(conta.getId())});
        db.close();
    }

    //pega a conta pelo id
    public Conta getConta(long idConta){
        Conta conta = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+ DBHelper.TABELA_CONTA + " WHERE " + DBHelper.CONTA_COL_ID + " =?";
        String[] args = {String.valueOf(idConta)};
        Cursor cursor = db.rawQuery(query, args);
        if(cursor.moveToFirst()){
            conta = criaConta(cursor);
        }
        closeArgs(db, cursor);
        return conta;
    }

    //pega conta pelo id do usuario e nome da conta para ver se a conta existe
    public Conta getConta(long idUsuario, String nomeConta){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_CONTA+" WHERE "+DBHelper.CONTA_FK_USUARIO + " = ?" + " AND " + DBHelper.CONTA_COL_NOME + " = ?";
        String[] args = {String.valueOf(idUsuario), nomeConta};
        Cursor cursor = db.rawQuery(query, args);
        Conta conta = null;
        if(cursor.moveToFirst()){
            conta = criaConta(cursor);
        }
        closeArgs(db, cursor);
        return conta;
    }

    //pega todos os tipos de conta
    public ArrayList<TipoConta> getTiposConta(){
        ArrayList<TipoConta> tipoContas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_TIPO_CONTA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                TipoConta tipoConta = TipoConta.values()[(cursor.getInt(cursor.getColumnIndex(DBHelper.TIPO_CONTA_COL_ID)))-1];
                tipoContas.add(tipoConta);
            } while (cursor.moveToNext());
        }
        closeArgs(db,cursor);
        return tipoContas;
    }

    //pega o valor total todas as contas
    public BigDecimal getSaldoContas(long usuarioId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_CONTA+" WHERE "+DBHelper.CONTA_FK_USUARIO + " = ?";
        String[] args = {String.valueOf(usuarioId)};
        Cursor cursor = db.rawQuery(query, args);
        BigDecimal saldo = new BigDecimal("00.00");
        if(cursor.moveToFirst()){
            do {
                BigDecimal valorConta = new BigDecimal(cursor.getString(cursor.getColumnIndex(DBHelper.CONTA_COL_SALDO)));
                saldo = saldo.add(valorConta);
            } while (cursor.moveToNext());
        }
        closeArgs(db,cursor);
        return saldo;
    }

    //verifica se existe conta ativa
    public Conta existContaAtiva(long idUsuario, long idConta) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABELA_CONTA + " WHERE "+DBHelper.CONTA_FK_USUARIO + " = ?" + " AND " + DBHelper.CONTA_FK_TIPO_ESTADO_CONTA + " = ? AND NOT " + DBHelper.CONTA_COL_ID + " = ?";
        String[] args = {String.valueOf(idUsuario), String.valueOf(1), String.valueOf(idConta)};
        Cursor cursor = db.rawQuery(query, args);
        Conta exist = null;
        if(cursor.moveToFirst()){
            exist = criaConta(cursor);
        }
        closeArgs(db, cursor);
        return exist;

    }
    //fecha db e o cursor
    private void closeArgs(SQLiteDatabase db, Cursor cursor) {
        cursor.close();
        db.close();
    }
}

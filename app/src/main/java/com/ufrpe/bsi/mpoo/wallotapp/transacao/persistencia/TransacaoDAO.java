package com.ufrpe.bsi.mpoo.wallotapp.transacao.persistencia;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia.CategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.conta.persistencia.ContaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.WallotAppException;
import com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia.DBHelper;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.persistencia.SubCategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Parcela;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoDeStatusTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.TipoTransacao;
import com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio.Transacao;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.persistencia.UsuarioDAO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransacaoDAO {
    private DBHelper dbHelper = new DBHelper();
    private SimpleDateFormat padraoDataSQLite = new SimpleDateFormat("yyyyMMdd");

    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private SubCategoriaDAO subCategoriaDAO = new SubCategoriaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ContaDAO contaDAO = new ContaDAO();

    //pega tipos de tranção enum
    public ArrayList<TipoTransacao> getTiposTransacao(){
        ArrayList<TipoTransacao> tiposTransacao = new ArrayList<>();
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

    //cadastra a transacao no banco de dados
    public long cadastrarTransacao(Transacao transacao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValuesTransacao(transacao);
        long res = db.insert(DBHelper.TABELA_TRANSACAO, null, values);
        db.close();
        return res;
    }

    //cadastra parcela
    public long cadastrarParcela(Parcela parcela) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValuesParcela(parcela);
        long res = db.insert(DBHelper.TABELA_PARCELA, null, values);
        db.close();
        return res;
    }

    //cria o objeto transacao
    private Transacao criaTransacao(Cursor cursor) {
        Transacao transacao = new Transacao();
        int indexId = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_ID);
        int indexTitulo = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_TITULO);
        int indexValor = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_VALOR);
        int indexQntParcelas = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_PARCELAS);
        int indexFkCategoria = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_FK_CATEGORIA);
        int indexFkSubCategoria = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_FK_SUBCATEGORIA);
        int indexTipoTransacao = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_TIPO_TRANSACAO);
        int indexFkUsuario = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_FK_USUARIO);
        int indexFkConta = cursor.getColumnIndex(DBHelper.TRANSACAO_COL_FK_CONTA);
        transacao.setId(cursor.getLong(indexId));
        transacao.setTitulo(cursor.getString(indexTitulo));
        transacao.setValor(new BigDecimal(cursor.getString(indexValor)));
        transacao.setQntParcelas(cursor.getLong(indexQntParcelas));
        transacao.setTipoTransacao(TipoTransacao.values()[cursor.getInt(indexTipoTransacao) - 1]);
        transacao.setCategoria(categoriaDAO.getCategoria(cursor.getLong(indexFkCategoria)));
        transacao.setSubCategoria(subCategoriaDAO.getSubCategoria(cursor.getLong(indexFkSubCategoria)));
        transacao.setConta(contaDAO.getConta(cursor.getLong(indexFkConta)));
        transacao.setUsuario(usuarioDAO.getUsuario(cursor.getLong(indexFkUsuario)));
        return transacao;
    }

    //cria o objeto transacao
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
        parcela.setDataTransacao(getDataDoSQLite(String.valueOf(cursor.getString(indexData))));
        parcela.setFkTransacao(cursor.getLong(indexTransacao));
        return parcela;
    }

    //pega valores de transacao
    private ContentValues getContentValuesTransacao(Transacao transacao) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.TRANSACAO_COL_TITULO, transacao.getTitulo());
        values.put(DBHelper.TRANSACAO_COL_VALOR, transacao.getValor().toString());
        values.put(DBHelper.TRANSACAO_COL_PARCELAS, String.valueOf(transacao.getQntParcelas()));
        values.put(DBHelper.TRANSACAO_COL_FK_CONTA, String.valueOf(transacao.getConta().getId()));
        values.put(DBHelper.TRANSACAO_COL_FK_CATEGORIA, String.valueOf(transacao.getCategoria().getId()));
        values.put(DBHelper.TRANSACAO_COL_FK_SUBCATEGORIA, String.valueOf(transacao.getSubCategoria().getId()));
        values.put(DBHelper.TRANSACAO_COL_FK_USUARIO, String.valueOf(transacao.getUsuario().getId()));
        values.put(DBHelper.TRANSACAO_COL_TIPO_TRANSACAO, String.valueOf(transacao.getTipoTransacao().ordinal() + 1));
        return values;
    }

    //pega valores de parcela
    private ContentValues getContentValuesParcela(Parcela parcela) {
        ContentValues values = new ContentValues();
        String dataString = padraoDataSQLite.format(parcela.getDataTransacao());
        values.put(DBHelper.PARCELA_COL_VALOR, parcela.getValorParcela().toString());
        values.put(DBHelper.PARCELA_COL_DATE, Integer.parseInt(dataString));
        values.put(DBHelper.PARCELA_NUMERO_PARCELA, String.valueOf(parcela.getNumeroParcela()));
        values.put(DBHelper.PARCELA_COL_FK_TRANSACAO, String.valueOf(parcela.getFkTransacao()));
        values.put(DBHelper.PARCELA_TIPO_DE_STATUS, String.valueOf(parcela.getTipoDeStatusTransacao().ordinal() + 1));
        return values;
    }

    //pega a trnsacao pelo id no DB
    public Transacao getTransacao(long idTransacao){
        Transacao transacao = null;
        String sql = "SELECT * FROM " + DBHelper.TABELA_TRANSACAO + " WHERE " + DBHelper.TRANSACAO_COL_ID + " LIKE ?;";
        String[] args = {String.valueOf(idTransacao)};
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        if(cursor.moveToFirst()){
            transacao = criaTransacao(cursor);
        }
        closeArgs(db, cursor);
        return transacao;
    }

    //pega os valores do dia atual até agora
    public ArrayList<Parcela> getUltimasParcelas(long idUsuario, int data) {
        ArrayList<Parcela> parcelas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " +  DBHelper.TABELA_TRANSACAO + " INNER JOIN " + DBHelper.TABELA_PARCELA + " ON " + DBHelper.TRANSACAO_COL_ID + " = " +
                DBHelper.PARCELA_COL_FK_TRANSACAO + " AND " + DBHelper.TRANSACAO_COL_FK_USUARIO + " =1  AND " + DBHelper.PARCELA_COL_DATE + " < ? AND " + DBHelper.PARCELA_TIPO_DE_STATUS  + " =1  ORDER BY " + DBHelper.PARCELA_COL_DATE;
        String[] args = {String.valueOf(data)};
        Cursor cursor = db.rawQuery(query,args);
        if (cursor.moveToFirst()){
            do {
                Parcela parcela = criaParcela(cursor);
                parcelas.add(parcela);
            } while (cursor.moveToNext());
        }
        closeArgs(db,cursor);
        return parcelas;
    }

    //pega as parcelas por data(método funcional, mas ainda em desenvolvimento)
    public ArrayList<Parcela> getParcelasPorData(long idUsuario) {
        ArrayList<Parcela> parcelas = new ArrayList<>();
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
        closeArgs(db,cursor);
        return parcelas;
    }

    //ao alterar uma subcategoria ela altera as sucategorias das transações
    public void atualizaSubcategoria(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + DBHelper.TABELA_TRANSACAO + " SET " + DBHelper.TRANSACAO_COL_FK_SUBCATEGORIA + " = 1 WHERE " + DBHelper.TRANSACAO_COL_FK_SUBCATEGORIA + " = " + id+";";
        db.execSQL(sql);
        db.close();
    }

    //deleta parcela do banco de dados
    public void deletarParcela(Parcela parcela) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM "+ DBHelper.TABELA_PARCELA + " WHERE " + DBHelper.PARCELA_COL_ID + " = " + parcela.getId() + ";" ;
        db.execSQL(sql);
        db.close();
    }

    //altera data da parcela
    public void alteraDataParcela(Parcela parcela) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.PARCELA_COL_DATE, padraoDataSQLite.format(parcela.getDataTransacao()));
        db.update(DBHelper.TABELA_PARCELA, values, DBHelper.PARCELA_COL_ID + " = ?", new String[]{String.valueOf(parcela.getId())});
        db.close();
    }

    //altera status da parcela
    public void alteraStatusParcela(Parcela parcela) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.PARCELA_TIPO_DE_STATUS,(parcela.getTipoDeStatusTransacao().ordinal() + 1));
        db.update(DBHelper.TABELA_PARCELA,values,DBHelper.PARCELA_COL_ID + " = ?",new String[]{String.valueOf(parcela.getId())});
        db.close();
    }

    //altera valor da parcela
    public void alteraValorParcela(Parcela parcela) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.PARCELA_COL_VALOR, parcela.getValorParcela().toString());
        db.update(DBHelper.TABELA_PARCELA, values, DBHelper.PARCELA_COL_ID + " = ?", new String[]{String.valueOf(parcela.getId())});
        db.close();
    }

    //pega saldo dando uma certa diferença de tempo
    public BigDecimal getSaldoEntreDatas(long idUsuario, String dataInicial, String dataFinal, BigDecimal saldo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = " SELECT * FROM " +
                DBHelper.TABELA_TRANSACAO + " INNER JOIN " + DBHelper.TABELA_PARCELA + " ON " + DBHelper.TRANSACAO_COL_ID + " = " + DBHelper.PARCELA_COL_FK_TRANSACAO + " WHERE " +
                DBHelper.PARCELA_COL_DATE + " > " + dataInicial + " AND " + DBHelper.PARCELA_TIPO_DE_STATUS + " = " + " 1 " + " AND " +
                DBHelper.PARCELA_COL_DATE + " <= " + dataFinal + " AND " + DBHelper.TRANSACAO_COL_FK_USUARIO + " =?;";
        String[] args = {String.valueOf(idUsuario)};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                BigDecimal valorParcela = new BigDecimal(cursor.getString(cursor.getColumnIndex(DBHelper.PARCELA_COL_VALOR)));
                saldo = saldo.add(valorParcela.multiply(new BigDecimal(-1)));
            } while (cursor.moveToNext());
        }
        closeArgs(db, cursor);
        return saldo;
    }

    //pega gastos dando uma certa diferença de tempo
    public BigDecimal getGastoEntreDatas(long idUsuario, String dataInicial, String dataFinal) {
        BigDecimal gastos = new BigDecimal("0.00");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = " SELECT * FROM " +
                DBHelper.TABELA_TRANSACAO + " INNER JOIN " + DBHelper.TABELA_PARCELA + " ON " + DBHelper.TRANSACAO_COL_ID + " = " + DBHelper.PARCELA_COL_FK_TRANSACAO + " WHERE " +
                DBHelper.PARCELA_COL_DATE + " > " + dataInicial + " AND " + DBHelper.PARCELA_COL_DATE + " <= " + dataFinal + " AND " + DBHelper.TRANSACAO_COL_TIPO_TRANSACAO + " = " + " 2" + " AND " +
                DBHelper.PARCELA_TIPO_DE_STATUS + " = " + " 1 " + " AND " + DBHelper.TRANSACAO_COL_FK_USUARIO + " =?;";
        String[] args = {String.valueOf(idUsuario)};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                BigDecimal valorParcela = new BigDecimal(cursor.getString(cursor.getColumnIndex(DBHelper.PARCELA_COL_VALOR)));
                gastos = gastos.add(valorParcela.multiply(new BigDecimal(-1)));
            } while (cursor.moveToNext());
        }
        closeArgs(db, cursor);
        return gastos;
    }

    public ArrayList<Integer> getCoordGastosEntreDatas(long idUsuario, String dataInicial, String dataFinal) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Integer> XYGastos = new ArrayList<>();

        for (int i = Integer.parseInt(dataInicial); i <= Integer.parseInt(dataFinal); i++) {
            BigDecimal gasto = new BigDecimal("0.00");
            String query = " SELECT * FROM " +
                    DBHelper.TABELA_TRANSACAO + " INNER JOIN " + DBHelper.TABELA_PARCELA + " ON " + DBHelper.TRANSACAO_COL_ID + " = " + DBHelper.PARCELA_COL_FK_TRANSACAO + " WHERE " +
                    DBHelper.PARCELA_COL_DATE + " = " + i + " AND " + DBHelper.TRANSACAO_COL_TIPO_TRANSACAO + " = " + " 2" + " AND " +
                    DBHelper.PARCELA_TIPO_DE_STATUS + " = " + " 1 " + " AND " + DBHelper.TRANSACAO_COL_FK_USUARIO + " =?;";
            String[] args = {String.valueOf(idUsuario)};
            Cursor cursor = db.rawQuery(query, args);
            if (cursor.moveToFirst()) {
                do {
                    BigDecimal valorParcela = new BigDecimal(cursor.getString(cursor.getColumnIndex(DBHelper.PARCELA_COL_VALOR)));
                    gasto = gasto.add(valorParcela.multiply(new BigDecimal(-1)));
                } while (cursor.moveToNext());
            }
            XYGastos.add(gasto.intValue());
            XYGastos.add(i);
            cursor.close();
        }
        db.close();
        return XYGastos;
    }


    //pega valor total das contas e depois vai fazendo esse deregue jhonson
    //criar uma função que devolve uma lista de BigDecimal // parcelas listadas ao contrario e daí eu organizo no mapa
    //acho que da errado//na ultima linha eu vou lá e pego valor total das contas

    //pega todas as parcelas da transação
    public ArrayList<Parcela> getParcelasDaTransacao(long id) {
        ArrayList<Parcela> parcelas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " +  DBHelper.TABELA_TRANSACAO + " INNER JOIN " + DBHelper.TABELA_PARCELA  + " ON " + DBHelper.TRANSACAO_COL_ID + " = " + DBHelper.PARCELA_COL_FK_TRANSACAO + " AND " + DBHelper.TRANSACAO_COL_ID  + " =?" + " ORDER BY " + DBHelper.PARCELA_COL_DATE;
        String[] args = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(query,args);
        if (cursor.moveToFirst()){
            do {
                Parcela parcela = criaParcela(cursor);
                parcelas.add(parcela);
            } while (cursor.moveToNext());
        }
        closeArgs(db, cursor);
        return parcelas;
    }

    //pega valor total de uma transacao(pago e não pago)//ainda em desenvolvimento
    public BigDecimal[] getValorTotalTransacao(long id) {
        BigDecimal[] valores = new BigDecimal[2];
        BigDecimal valorPago = new BigDecimal("0.00");
        BigDecimal valorTotal = new BigDecimal("0.00");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " +  DBHelper.TABELA_TRANSACAO + " INNER JOIN " + DBHelper.TABELA_PARCELA  + " ON " + DBHelper.TRANSACAO_COL_ID + " = " + DBHelper.PARCELA_COL_FK_TRANSACAO + " AND " + DBHelper.TRANSACAO_COL_ID  + " =?" + " ORDER BY " + DBHelper.PARCELA_COL_DATE;
        String[] args = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(query,args);
        if (cursor.moveToFirst()){
            do {
                Parcela parcela = criaParcela(cursor);
                valorTotal = valorTotal.add(parcela.getValorParcela());
                if (parcela.getTipoDeStatusTransacao() == TipoDeStatusTransacao.CONSOLIDADO){
                    valorPago = valorPago.add(parcela.getValorParcela());
                }
            } while (cursor.moveToNext());
        }
        valores[0] = valorPago;
        valores[1] = valorTotal;
        closeArgs(db, cursor);
        return valores;
    }


    //fecha argumentos
    private void closeArgs(SQLiteDatabase db, Cursor cursor) {
        cursor.close();
        db.close();
    }

    //pega a fata do sqlite e transfroma em Date
    private Date getDataDoSQLite(String datastr) {
        Date data = new Date();
        try {
            data = padraoDataSQLite.parse(datastr);
        } catch (Exception e) {
            new WallotAppException("A data inválida");
        }
        return data;
    }

    //altera a quantidade de parcelas
    public void alteraQntParcela(Transacao transacao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.TRANSACAO_COL_PARCELAS, String.valueOf(transacao.getQntParcelas()));
        db.update(DBHelper.TABELA_TRANSACAO, values, DBHelper.TRANSACAO_COL_ID + " = ?", new String[]{String.valueOf(transacao.getId())});
        db.close();
    }

    public void deletarTransacao(Transacao transacao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM "+ DBHelper.TABELA_TRANSACAO + " WHERE " + DBHelper.TRANSACAO_COL_ID + " = " + transacao.getId() + ";" ;
        db.execSQL(sql);
        db.close();
    }

}

package com.ufrpe.wallot_app.infra.persistencia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufrpe.wallot_app.infra.app.WallotApp;
import com.ufrpe.wallot_app.pagamento.dominio.TipoTransacao;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "wallot.db";
    private static final int VERSAO = 1;

    //USUARIO
    public static final String TABELA_USUARIO = "TABELA_USUARIO";
    public static final String USUARIO_COL_ID = "ID";
    public static final String USUARIO_COL_NOME = "NOME";
    public static final String USUARIO_COL_EMAIL = "EMAIL";
    public static final String USUARIO_COL_SENHA = "SENHA";
    //CARTEIRA
    public static final String TABELA_CONTA = "TABELA_CONTA";
    public static final String CONTA_COL_ID = "ID";
    public static final String CONTA_COL_NOME = "NOME";
    public static final String CONTA_COL_SALDO = "SALDO";
    public static final String CONTA_TB_USUARIO = "FK_USUARIO";
    //PAGAMENTO
    public static final String TABELA_PAGAMENTO = "TABELA_PAGAMENTO";
    public static final String PAGAMENTO_COL_ID = "ID";
    public static final String PAGAMENTO_COL_DESCRICAO = "DESCRIÇÃO";
    public static final String PAGAMENTO_COL_VALOR = "VALOR";
    public static final String PAGAMENTO_COL_DATA = "DATA";
    public static final String PAGAMENTO_COL_CATEGORIA = "FK_CATEGORIA";
    public static final String PAGAMENTO_COL_SUBCATEGORIA = "FK_SUBCATEGORIA";
    public static final String PAGAMENTO_COL_TB_CONTA = "FK_CONTA";
    public static final String PAGAMENTO_COL_TB_USUARIO = "FK_USUARIO";
    public static final String PAGAMENTO_COL_TB_TIPO_TRANSICAO = "FK_TIPO_TRANSICAO";
    //TABELA TIPO TRANSACAO
    public static final String TABELA_TIPO_TRANSICAO = "TABELA_TIPO_TRANSICAO";
    public static final String TIPO_TRANSICAO_COL_ID = "ID";
    public static final String TIPO_TRANSICAO_COL_DESCRICAO = "DESCRIÇÃO";





    private static final String[] TABELAS = {
            TABELA_USUARIO, TABELA_CONTA, TABELA_PAGAMENTO
    };

    public DBHelper() {
        super(WallotApp.getContext(), NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //criaTBTransicao(db);
        criaTBUsuario(db);
        criaTBCarteira(db);
        criaTBPagamento(db);
    }


    private void criaTBPagamento(SQLiteDatabase db) {
        String sqlTbPagamento =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL, " +
                        "  %6$s TEXT NOT NULL, " +
                        "  %7$s TEXT NOT NULL, " +
                        "  %8$s TEXT NOT NULL, " +
                        "  %9$s TEXT NOT NULL," +
                        "  %10$s TEXT NOT NULL" +
                        ");";
        sqlTbPagamento = String.format(sqlTbPagamento,
                TABELA_PAGAMENTO,
                PAGAMENTO_COL_ID,
                PAGAMENTO_COL_DESCRICAO,
                PAGAMENTO_COL_VALOR,
                PAGAMENTO_COL_DATA,
                PAGAMENTO_COL_CATEGORIA,
                PAGAMENTO_COL_SUBCATEGORIA,
                PAGAMENTO_COL_TB_CONTA,
                PAGAMENTO_COL_TB_USUARIO,
                PAGAMENTO_COL_TB_TIPO_TRANSICAO);
        db.execSQL(sqlTbPagamento);
    }

    private void criaTBCarteira(SQLiteDatabase db) {
        String sqlTbCarteira =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbCarteira = String.format(sqlTbCarteira,
                TABELA_CONTA,
                CONTA_COL_ID,
                CONTA_COL_NOME,
                CONTA_COL_SALDO,
                CONTA_TB_USUARIO
        );
        db.execSQL(sqlTbCarteira);

    }

    private void criaTBUsuario(SQLiteDatabase db) {
        String sqlTbUsuario =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL UNIQUE, " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbUsuario = String.format(sqlTbUsuario,
                TABELA_USUARIO,
                USUARIO_COL_ID,
                USUARIO_COL_NOME,
                USUARIO_COL_EMAIL,
                USUARIO_COL_SENHA
        );
        db.execSQL(sqlTbUsuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }

    public void dropTables(SQLiteDatabase db) {
        for (String tabela : TABELAS) {
            StringBuilder dropTable = new StringBuilder();
            dropTable.append(" DROP TABLE IF EXISTS ");
            dropTable.append(tabela);
            db.execSQL(dropTable.toString());
        }
    }

    private void criaTBTransicao(SQLiteDatabase db) {
        String createString = new StringBuilder()
                .append("CREATE TABLE %1$s ( ")
                .append("  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append("  %3$s TEXT NOT NULL ")
                .append(");").toString();

        createString = String.format(createString, TABELA_TIPO_TRANSICAO, TIPO_TRANSICAO_COL_ID, TIPO_TRANSICAO_COL_DESCRICAO);
        db.execSQL(createString);

        //Inicializa valores base
        initTBTransicao(db);
    }

    private void initTBTransicao(SQLiteDatabase db) {
        DBHelper dbHelper = new DBHelper();
        SQLiteDatabase dbt = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (TipoTransacao enu: TipoTransacao.values()) {
            values.put(DBHelper.TIPO_TRANSICAO_COL_DESCRICAO, enu.getDescricao());
            dbt.insert(DBHelper.TABELA_TIPO_TRANSICAO, null, values);
        }
        dbt.close();
    }
}

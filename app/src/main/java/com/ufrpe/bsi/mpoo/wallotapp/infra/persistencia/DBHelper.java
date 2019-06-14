package com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufrpe.bsi.mpoo.wallotapp.infra.app.WallotApp;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "wallot.db";
    private static final int VERSAO = 5;

    //TABELA DE USUARIO(ENTROU NA VERSÃO 1)
    public static final String TABELA_USUARIO = "TABELA_USUARIO";
    public static final String USUARIO_COL_ID = "ID";
    public static final String USUARIO_COL_NOME = "NOME";
    public static final String USUARIO_COL_EMAIL = "EMAIL";
    public static final String USUARIO_COL_SENHA = "SENHA";

    //TABELA DE CONTA VERSÃO 2
    public static final String TABELA_CONTA = "TABELA_CONTA";
    public static final String CONTA_COL_ID = "ID";
    public static final String CONTA_COL_NOME = "NOME";
    public static final String CONTA_COL_SALDO = "SALDO";
    public static final String CONTA_FK_USUARIO = "FK_USUARIO";
    public static final String CONTA_COL_COR = "COR";
    public static final String CONTA_FK_TIPO_CONTA = "FK_TIPO_CONTA";
    public static final String CONTA_FK_TIPO_ESTADO_CONTA = "FK_TIPO_ESTADO_CONTA";


    //TABELA DE TIPO CONTA VERSÃO 2
    public static final String TABELA_TIPO_CONTA = "TABELA_TIPO_CONTA";
    public static final String TIPO_CONTA_COL_ID = "ID";
    public static final String TIPO_CONTA_COL_DESCRICAO = "DESCRIÇÃO";

    //TABELA DE TIPO ESTADO CONTA VERSÃO 2
    public static final String TABELA_TIPO_ESTADO_CONTA = "TABELA_TIPO_ESTADO_CONTA";
    public static final String TIPO_ESTADO_CONTA_COL_ID = "ID";
    public static final String TIPO_ESTADO_CONTA_COL_DESCRICAO = "DESCRIÇÃO";

    //TABELA DE CATEGORIA VERSÃO 3
    public static final String TABELA_CATEGORIA = "TABELA_CATEGORIA";
    public static final String CATEGORIA_COL_ID = "ID";
    public static final String CATEGORIA_COL_NOME = "NOME";
    /*public static final String CATEGORIA_COL_ICONE = "ICONE";*/
    public static final String CATEGORIA_FK_USUARIO = "FK_USUARIO";

    //TABELA DE SUBCATEGORIA VERSÃO #
    public static final String TABELA_SUBCATEGORIA = "TABELA_SUBCATEGORIA";
    public static final String SUBCATEGORIA_COL_ID = "ID";
    public static final String SUBCATEGORIA_COL_NOME = "NOME";
    /*public static final String SUBCATEGORIA_COL_ICONE = "ICONE";*/
    public static final String SUBCATEGORIA_FK_CATEGORIA = "FK_CATEGORIA";
    public static final String SUBCATEGORIA_FK_USUARIO = "FK_USUARIO";

    //TABELA DE Transacao VERSÃO 4
    public static final String TABELA_TRANSACAO = "TABELA_TRANSACAO";
    public static final String TRANSACAO_COL_ID = "ID";
    public static final String TRANSACAO_COL_TITULO = "TITULO";
    public static final String TRANSACAO_COL_VALOR = "VALOR";
    public static final String TRANSACAO_COL_TIPO_TRANSACAO = "TIPO_TRANSACAO";
    public static final String TRANSACAO_COL_PARCELAS = "QTD_PARCELAS";
    public static final String TRANSACAO_COL_FK_CATEGORIA = "FK_CATEGORIA";
    public static final String TRANSACAO_COL_FK_SUBCATEGORIA = "FK_SUBCATEGORIA";
    public static final String TRANSACAO_COL_FK_USUARIO = "FK_USUARIO";
    public static final String TRANSACAO_COL_FK_CONTA = "FK_CONTA";

    //TABELA DE PARCELA VERSÃO 5
    public static final String TABELA_PARCELA = "TABELA_PARCELA";
    public static final String PARCELA_COL_ID = "ID";
    public static final String PARCELA_COL_VALOR = "VALOR";
    public static final String PARCELA_COL_DATE = "DATA";
    public static final String PARCELA_NUMERO_PARCELA = "N_PARCELA";
    public static final String PARCELA_COL_FK_TRANSACAO = "FK_TRANSACAO";
    


    //TABELA DE TIPO TRANSACAO VERSAO 4
    public static final String TABELA_TIPO_TRANSACAO = "TABELA_TIPO_TRANSACAO";
    public static final String TIPO_TRANSACAO_COL_ID = "ID";
    public static final String TIPO_TRANSACAO_COL_DESCRICAO = "DESCRIÇÃO";
    public static final String TIPO_TRANSACAO_COL_MULTIPLICADOR = "MULTIPLICADOR";





    private String sqlTipoTipoTransacaoInit = "INSERT INTO " + TABELA_TIPO_TRANSACAO + " ( " + TIPO_TRANSACAO_COL_ID + ", " + TIPO_TRANSACAO_COL_DESCRICAO + ", " + TIPO_TRANSACAO_COL_MULTIPLICADOR + ") VALUES " + "(1,'Receita', '1')," + "(2,'Despesa', '-1')," + "(3, 'Transferência', '-1')";
    private String sqlTipoContaInit = "INSERT INTO " + TABELA_TIPO_CONTA + "( " + TIPO_CONTA_COL_ID + "," + TIPO_CONTA_COL_DESCRICAO + ") VALUES " + "(1,'Dinheiro')";
    private String sqlTipoEstadoContaInit = "INSERT INTO " + TABELA_TIPO_ESTADO_CONTA + "( " + TIPO_ESTADO_CONTA_COL_ID + "," + TIPO_ESTADO_CONTA_COL_DESCRICAO + ") VALUES " + "(1,'Ativo')," + "(2,'Inativo')";
    private String sqlCategoriaInit = " INSERT INTO " + TABELA_CATEGORIA + "( " + CATEGORIA_COL_ID + "," + CATEGORIA_COL_NOME + "," + CATEGORIA_FK_USUARIO + ") VALUES " +
            "(1,'Sem categoria', '0' )," +
            "(2,'Alimentação',NULL)," +
            "(3,'Casa', NULL)," +
            "(4,'Compras', NULL)," +
            "(5,'Comunicação', NULL)," +
            "(6,'Transporte', NULL)," +
            "(7,'Veículo', NULL)," +
            "(8,'Vida e Lazer', NULL)," +
            "(9,'Outros', NULL)";

    private String sqlSubCategoriaInit = " INSERT INTO " + TABELA_SUBCATEGORIA + "( " + SUBCATEGORIA_COL_ID + "," + SUBCATEGORIA_COL_NOME + "," + SUBCATEGORIA_FK_CATEGORIA + "," + SUBCATEGORIA_FK_CATEGORIA + ") VALUES " +
            "(1,'Sem subcategoria', '0', '0')," +
            "(2,'FastFood', '1',NULL)," +
            "(3,'Feira', '1',NULL)," +
            "(4,'Restaurante', '1',NULL)," +
            "(5,'Água', '2',NULL)," +
            "(6,'Aluguel', '2',NULL)," +
            "(7,'Gás', '2',NULL)," +
            "(8,'Luz', '2',NULL)," +
            "(9,'Manuntenção', '2',NULL)," +
            "(10,'Animais', '3',NULL)," +
            "(11,'Beleza', '3',NULL)," +
            "(12,'Eletrônicos', '3',NULL)," +
            "(13,'Farmácia', '3',NULL)," +
            "(14,'Vestuário', '3',NULL)," +
            "(15,'Internet', '4',NULL)," +
            "(16,'Serviços Postais', '4',NULL)," +
            "(17,'Telefone', '4',NULL)," +
            "(18,'Longas Distancias', '5',NULL)," +
            "(19,'Particular', '5',NULL)," +
            "(20,'Público', '5',NULL)," +
            "(21,'Combustível', '6',NULL)," +
            "(22,'Estacionamento', '6',NULL)," +
            "(23,'Manuntenção', '6',NULL)," +
            "(24,'Seguro', '6',NULL)," +
            "(25,'Bebida e Cigarro', '7',NULL)," +
            "(26,'Educação', '7',NULL)," +
            "(27,'Fitness', '7',NULL)," +
            "(28,'Saúde', '7',NULL)," +
            "(29,'Softwares e Jogos', '7',NULL)," +
            "(30,'TV e Streaming', '7',NULL)," +
            "(31,'Viagens', '7',NULL)";



    private static final String[] TABELAS = {
            TABELA_USUARIO, TABELA_CONTA, TABELA_TIPO_CONTA, TABELA_TIPO_ESTADO_CONTA, TABELA_CATEGORIA, TABELA_SUBCATEGORIA, TABELA_TRANSACAO, TABELA_PARCELA, TABELA_TIPO_TRANSACAO
    };

    public DBHelper() {
        super(WallotApp.getContext(), NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        criaTbUsuario(db);
        criaTbConta(db);
        criaTbTipoConta(db);
        criaTbTipoEstadoConta(db);
        criaTbCategoria(db);
        criaTbSubCategoria(db);
        criaTbTransaccao(db);
        criaTbParcela(db);
        criaTbTipoTransacao(db);
        db.execSQL(sqlTipoContaInit);
        db.execSQL(sqlTipoEstadoContaInit);
        db.execSQL(sqlCategoriaInit);
        db.execSQL(sqlSubCategoriaInit);
        db.execSQL(sqlTipoTipoTransacaoInit);
    }

    private void criaTbTipoTransacao(SQLiteDatabase db) {
        String sqlTbTipoTransacao =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL " +
                        ");";
        sqlTbTipoTransacao = String.format(sqlTbTipoTransacao,
                TABELA_TIPO_TRANSACAO,
                TIPO_TRANSACAO_COL_ID,
                TIPO_TRANSACAO_COL_DESCRICAO,
                TIPO_TRANSACAO_COL_MULTIPLICADOR
        );
        db.execSQL(sqlTbTipoTransacao);

    }

    private void criaTbParcela(SQLiteDatabase db) {
        String sqlTbParcela =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL, " +
                        "  %6$s TEXT NOT NULL " +
                        ");";
        sqlTbParcela = String.format(sqlTbParcela,
                TABELA_PARCELA,
                PARCELA_COL_ID,
                PARCELA_COL_VALOR,
                PARCELA_COL_DATE,
                PARCELA_NUMERO_PARCELA,
                PARCELA_COL_FK_TRANSACAO
        );
        db.execSQL(sqlTbParcela);
    }

    private void criaTbTransaccao(SQLiteDatabase db) {
        String sqlTbTransacao =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL, " +
                        "  %6$s TEXT NOT NULL, " +
                        "  %7$s TEXT NOT NULL, " +
                        "  %8$s TEXT NOT NULL, " +
                        "  %9$s TEXT NOT NULL, " +
                        "  %10$s TEXT NOT NULL " +
                        ");";
        sqlTbTransacao = String.format(sqlTbTransacao,
                TABELA_TRANSACAO,
                TRANSACAO_COL_ID,
                TRANSACAO_COL_TITULO,
                TRANSACAO_COL_VALOR,
                TRANSACAO_COL_PARCELAS,
                TRANSACAO_COL_TIPO_TRANSACAO,
                TRANSACAO_COL_FK_CATEGORIA,
                TRANSACAO_COL_FK_SUBCATEGORIA,
                TRANSACAO_COL_FK_CONTA,
                TRANSACAO_COL_FK_USUARIO
        );
        db.execSQL(sqlTbTransacao);
    }

    private void criaTbSubCategoria(SQLiteDatabase db) {
        String sqlTbSubCategoria =
                "CREATE TABLE %1$s ( " +
                        " %2$s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " %3$s TEXT NOT NULL, " +
                        /*" %4$s BLOB, " +*/
                        " %4$s TEXT NOT NULL, " +
                        " %5$s TEXT " +
                        ");";
        sqlTbSubCategoria = String.format(sqlTbSubCategoria,
                TABELA_SUBCATEGORIA,
                SUBCATEGORIA_COL_ID,
                SUBCATEGORIA_COL_NOME,
                /*SUBCATEGORIA_COL_ICONE,*/
                SUBCATEGORIA_FK_CATEGORIA,
                SUBCATEGORIA_FK_USUARIO
        );
        db.execSQL(sqlTbSubCategoria);
    }

    private void criaTbCategoria(SQLiteDatabase db) {
        String sqlTbCategoria =
                "CREATE TABLE %1$s ( " +
                        " %2$s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " %3$s TEXT NOT NULL, " +
                        /* " %4$s BLOB, " +*/
                        " %4$s TEXT " +
                        ");";
        sqlTbCategoria = String.format(sqlTbCategoria,
                TABELA_CATEGORIA,
                CATEGORIA_COL_ID,
                CATEGORIA_COL_NOME,
                /*CATEGORIA_COL_ICONE,*/
                CATEGORIA_FK_USUARIO
        );
        db.execSQL(sqlTbCategoria);
    }


    private void criaTbTipoEstadoConta(SQLiteDatabase db) {
        String sqlTbTipoEstadoConta =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL " +
                        ");";
        sqlTbTipoEstadoConta = String.format(sqlTbTipoEstadoConta,
                TABELA_TIPO_ESTADO_CONTA,
                TIPO_ESTADO_CONTA_COL_ID,
                TIPO_ESTADO_CONTA_COL_DESCRICAO
        );
        db.execSQL(sqlTbTipoEstadoConta);
    }

    private void criaTbTipoConta(SQLiteDatabase db) {
        String sqlTbTipoConta =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL " +
                        ");";
        sqlTbTipoConta = String.format(sqlTbTipoConta,
                TABELA_TIPO_CONTA,
                TIPO_CONTA_COL_ID,
                TIPO_CONTA_COL_DESCRICAO
        );
        db.execSQL(sqlTbTipoConta);
    }



    private void criaTbConta(SQLiteDatabase db) {
        String sqlTbConta =
                "CREATE TABLE %1$s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL, " +
                        "  %6$s TEXT NOT NULL, " +
                        "  %7$s TEXT NOT NULL, " +
                        "  %8$s TEXT NOT NULL " +
                        ");";
        sqlTbConta = String.format(sqlTbConta,
                TABELA_CONTA,
                CONTA_COL_ID,
                CONTA_COL_NOME,
                CONTA_COL_SALDO,
                CONTA_COL_COR,
                CONTA_FK_USUARIO,
                CONTA_FK_TIPO_CONTA,
                CONTA_FK_TIPO_ESTADO_CONTA
        );
        db.execSQL(sqlTbConta);
    }

    private void criaTbUsuario(SQLiteDatabase db) {
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



}
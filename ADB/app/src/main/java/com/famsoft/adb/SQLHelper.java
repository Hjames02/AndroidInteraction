package com.famsoft.adb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

    //nombre de la base de datos
    private static final String __DATABASE = "dbTest";
    //versión de la base de datos
    private static final int __VERSION = 1;
    //nombre tabla y campos de tabla
    public final String __tabla__ = "productos";
    public final String __campo_id = "id";
    public final String __campo_articulo = "articulo";
    public final String __campo_descripcion = "descripcion";
    public final String __campo_serie = "serie";
    //Instrucción SQL para crear las tablas
    private final String sql = "CREATE TABLE IF NOT EXISTS " + __tabla__ + " ( " + __campo_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + __campo_articulo + " VARCHAR(50), " + __campo_descripcion + " VARCHAR(50), " + __campo_serie + " VARCHAR(50))";

    /**
     * Constructor de clase
     * */
    public SQLHelper(Context context) {
        super( new DatabaseContext(context), __DATABASE, null, __VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            db.execSQL( sql );
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade( SQLiteDatabase db,  int oldVersion, int newVersion ) {
        if ( newVersion > oldVersion )
        {
            //elimina tabla
            db.execSQL( "DROP DATABASE " + __DATABASE );
            //y luego creamos la nueva tabla
            db.execSQL( sql );
        }
    }
}
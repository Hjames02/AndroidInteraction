package com.famsoft.adb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQL {

    SQLHelper sqliteHelper;
    SQLiteDatabase db;

    /** Constructor de clase */
    public SQL(Context context) {
        sqliteHelper = new SQLHelper( context );
        //sqliteHelper.onCreate(sqliteHelper.getReadableDatabase());
    }

    /** Abre conexion a base de datos */
    public void abrir() {
        //Log.i("SQLite", "Se abre conexion a la base de datos " + sqliteHelper.getDatabaseName() );
        db = sqliteHelper.getWritableDatabase();
        //sqliteHelper.onOpen(db);
    }

    /* Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqliteHelper.getDatabaseName() );
        sqliteHelper.close();
    }

    /**
     * Metodo para obtener registros que correspondan solo a "hombres" ordenados por nombre
     * @return Cursor
     * */

    public Cursor obtenerProductos()
    {
        //Log.i("SQLite", "query -> Consulta solo registros sexo='Hombre' " );
        //tabla
        //columnas ,
        //selection WHERE ,
        //selectionArgs , groupby, having,
        //orderby
        return db.query( sqliteHelper.__tabla__ ,
                new String[]{ sqliteHelper.__campo_id , sqliteHelper.__campo_articulo , sqliteHelper.__campo_descripcion, sqliteHelper.__campo_serie},
                null, //filtro
                null, null, null,
                " id ASC ");
    }

    public Cursor obtenerProductos(int top)
    {
        //Log.i("SQLite", "query -> Consulta solo registros sexo='Hombre' " );
        //tabla
        //columnas ,
        //selection WHERE ,
        //selectionArgs , groupby, having,
        //orderby
        return db.query( sqliteHelper.__tabla__ ,
                new String[]{ sqliteHelper.__campo_id , sqliteHelper.__campo_articulo , sqliteHelper.__campo_descripcion, sqliteHelper.__campo_serie},
                null, //filtro
                null, null, null,
                " id ASC ", String.valueOf(top));
    }

    /**
     * Metodo para agregar un nuevo registro
     * @param articulo
     * @param descripcion
     * @param serie
     * @return -1 si ocurrio un error o ID de fila insertada
     * */
    public long insertarProducto( String articulo, String descripcion, String serie )
    {
        //Log.i("SQLite", "INSERT: " + articulo + "," + descripcion + "," + serie );
        ContentValues contentValues = new ContentValues();
        contentValues.put( sqliteHelper.__campo_articulo , articulo);
        contentValues.put( sqliteHelper.__campo_descripcion , descripcion);
        contentValues.put( sqliteHelper.__campo_serie , serie);
        //table, nullColumnHack, values
        return db.insert( sqliteHelper.__tabla__ , null, contentValues );
    }

    /**
     * Metodo para eliminar un registro
     * @param id Identificador unico de registro PRIMARY KEY
     * @return El número de filas afectadas 0 en caso contrario.
     * */
    public int eliminarProducto( int id )
    {
        //Log.i("SQLite", "DELETE: id=" + id );
        //table , whereClause, whereArgs
        return db.delete( sqliteHelper.__tabla__ , sqliteHelper.__campo_id + " = ?",  new String[] {String.valueOf(id)});
    }

    /**
     * Metodo para actualizar un registro
     * @param id Identificador unico de registro PRIMARY KEY
     * @param articulo
     * @param descripcion
     * @param serie
     * @return numero de filas afectadas
     * */
    public int actualizarProducto( int id, String articulo, String descripcion, String serie  )
    {
        //Log.i("SQLite", "UPDATE: id=" + id + " - " + articulo + "," + descripcion + "," + serie );
        ContentValues contentValues = new ContentValues();
        contentValues.put( sqliteHelper.__campo_articulo , articulo);
        contentValues.put( sqliteHelper.__campo_descripcion , descripcion);
        contentValues.put( sqliteHelper.__campo_serie , serie);
        return db.update( sqliteHelper.__tabla__ , contentValues, sqliteHelper.__campo_id + " = ?",  new String[] {String.valueOf(id)});
    }

    /**
     * Metodo que dado un Cursor, recorre los registros y coloca en un String
     * @param cursor
     * @return String registros separados por comas y salto de linea
     *
    public String imprimirListaArticulos( Cursor cursor )
    {
        StringBuffer lista = new StringBuffer();
        if( cursor.moveToFirst() )
        {
            do{
                lista.append( cursor.getString( 0 ) + ", " );
                lista.append( cursor.getString( 1 ) + ", " );
                lista.append( cursor.getString( 2 ) + "\r\n" );
            }
            while( cursor.moveToNext() );
        }
        return lista.toString();
    }*/

}
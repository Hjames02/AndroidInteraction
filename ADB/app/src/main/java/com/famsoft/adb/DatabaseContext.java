package com.famsoft.adb;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

public class DatabaseContext extends ContextWrapper {

    private static final String DEBUG_CONTEXT = "DatabaseContext";

    public DatabaseContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name)
    {
        //File sdcard = Environment.getExternalStorageDirectory();
        //String dbfile = sdcard.getAbsolutePath() + File.separator + "databases" + File.separator + name;
        File sdcard = new File(getApplicationInfo().dataDir);
        if (!sdcard.exists())
            sdcard.mkdir();

        String dbfile = getApplicationInfo().dataDir + File.separator + "databases" + File.separator + name;
        if (!dbfile.endsWith(".db"))
            dbfile += ".db" ;

        File result = new File(dbfile);

        if (!result.getParentFile().exists())
            result.getParentFile().mkdirs();


        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN))
            Log.w(DEBUG_CONTEXT, "getDatabasePath(" + name + ") = " + result.getAbsolutePath());


        return result;
    }

    /* this version is called for android devices >= api-11. thank to @damccull for fixing this. */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(name, mode, factory);
    }

    /* this version is called for android devices < api-11 */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory)
    {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        // SQLiteDatabase result = super.openOrCreateDatabase(name, mode, factory);
        /*if (result.isOpen())
            result.execSQL(SQLHelper.sql);*/
        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN))
            Log.w(DEBUG_CONTEXT, "openOrCreateDatabase(" + name + ",,) = " + result.getPath());

        return result;
    }
}

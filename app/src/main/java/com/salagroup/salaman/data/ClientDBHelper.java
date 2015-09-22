package com.salagroup.salaman.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClientDBHelper extends SQLiteOpenHelper {

    public static final String TEST = "test";

    private static final String DATABASE_NAME = "saladb_2015_09_18";
    private static final int DATABASE_VERSION = 1;

    private String databasePath;
    private Context context;

    private static ClientDBHelper instance;

    public static ClientDBHelper newInstance(Context context) {
        if (instance == null)
            instance = new ClientDBHelper(context.getApplicationContext());
        return instance;
    }

    private ClientDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        databasePath = context.getFilesDir().getParent() + "/databases/" + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDatabase() {
        if (!checkDatabase()) {
            getWritableDatabase();
            copyDatabase();
        }
    }

    public SQLiteDatabase openDatabase() {
        return SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void copyDatabase() {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("database/" + DATABASE_NAME);
            FileOutputStream fos = new FileOutputStream(databasePath);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDb = null;
        try {
            checkDb = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }
}

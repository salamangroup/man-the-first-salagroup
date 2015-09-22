package com.salagroup.salaman.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDatasource {

    private ClientDBHelper clientDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    public SQLiteDatasource(Context context) {
        clientDBHelper = ClientDBHelper.newInstance(context);
        clientDBHelper.createDatabase();
        sqLiteDatabase = clientDBHelper.openDatabase();
    }

    public void closeDBConnection() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
    }
}

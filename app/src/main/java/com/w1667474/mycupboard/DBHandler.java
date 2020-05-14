package com.w1667474.mycupboard;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context ;
import android.util.Log;

import static android.provider.BaseColumns._ID ;
import static com.w1667474.mycupboard.details.tableName;
import static com.w1667474.mycupboard.details.col1;
import static com.w1667474.mycupboard.details.col2;
import static com.w1667474.mycupboard.details.col3;
import static com.w1667474.mycupboard.details.col4;
import static com.w1667474.mycupboard.details.col5;



public class DBHandler extends SQLiteOpenHelper {
    private static final String databaseName = "items.db";
    private static final int databaseVersion = 6;

    public DBHandler(Context con){
        super (con, databaseName, null, databaseVersion);
    }

    @Override
    //creates a new db with the names found on details (interface) into the columns
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName + " ("
        + _ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + col1 + " TEXT,"
        + col2 + " INTEGER,"
        + col3 + " REAL,"
        + col4 + " TEXT,"
        + col5 + " INTEGER);");
        Log.i("column 1", col1);
    }
//checks if there is a new version of the DB, if so it creates a new db or it will leave it as the current db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

}

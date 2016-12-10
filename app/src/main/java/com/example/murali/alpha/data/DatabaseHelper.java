package com.example.murali.alpha.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by murali on 12/8/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    Context context;

    static final String DATABASE_NAME = "login.db";
    static final String TABLE_NAME = "login";
    static final String UID ="_id";
    static final String USER_NAME="username";
    static final int DATABASE_VERSION = 3;
    static final String PASS = "pwd";
    static final String DATABASE_CREATE = "CREATE TABLE "+TABLE_NAME+"( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+USER_NAME+" VARCHAR(255),"+PASS +" VARCHAR(20));";
    static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    // called when no database exists in the disk and the helper class needs to create a new one
    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(DATABASE_CREATE);
        }catch (SQLException e){

        }

    }

    //called when upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


        try{
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (SQLException e){

        }


    }
}


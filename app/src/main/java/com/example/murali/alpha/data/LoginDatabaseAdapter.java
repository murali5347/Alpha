package com.example.murali.alpha.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoginDatabaseAdapter {



    DatabaseHelper dataBaseHelper;

    public LoginDatabaseAdapter(Context context){

        dataBaseHelper = new DatabaseHelper(context);

    }

    /*
      enter the given username and password into the database
     */
    public long insert(String name,String password){

        SQLiteDatabase sqLiteDatabase=dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.USER_NAME,name);
        contentValues.put(dataBaseHelper.PASS,password);
        return sqLiteDatabase.insert(dataBaseHelper.TABLE_NAME,null,contentValues);
    }

    /*
       check the given username and password correct
     */
    public String storedPassword(String userName) {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(dataBaseHelper.TABLE_NAME, null, dataBaseHelper.USER_NAME+" =?",
                new String[] { userName }, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("pwd"));
        cursor.close();
        return password;
    }



}

package com.itvillage.dev.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    ArrayList<String> columeNames;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("Database Create ", "Successfully");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable(String tableName, ArrayList<String> columnName) {
        columeNames = columnName;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "ID INTEGER PRIMARY KEY AUTOINCREMENT";
        for (String s : columnName) {
            query = query + "," + s + " VARCHAR";
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(" + query + ");");
        Log.d("Table Create ", "Successfully");


    }

    public void dropTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        Log.d("Table ", "Drop Successful");


    }

    public boolean insertDate(String tableName, ArrayList<String> values) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < columeNames.size(); i++) {
            contentValues.put(columeNames.get(i), values.get(i));
        }
        long result = db.insert(tableName, null, contentValues);
        if (result == -1) {
            return false;
        } else
            return true;
    }

    public Cursor getData(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + tableName, null);
        return res;
    }

    public Cursor getDataById(String tableName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + tableName + " WHERE ID=" + id, null);
        return res;
    }

    public boolean updateData(String tableName, String id, ArrayList<String> values) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < columeNames.size(); i++) {
            contentValues.put(columeNames.get(i), values.get(i));
        }
        db.update(tableName, contentValues, "ID=?", new String[]{id});
        return true;
    }

    public boolean deleteData(String tableName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer result = db.delete(tableName, "ID=?", new String[]{id});
        if (result == -1) {
            return false;
        } else
            return true;
    }

}

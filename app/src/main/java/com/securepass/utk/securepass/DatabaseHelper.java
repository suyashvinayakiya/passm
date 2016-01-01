package com.securepass.utk.securepass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by utk on 15-10-28.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PasswordsData.db";
    public static final String TABLE_NAME = "passwords";
    public static final String NAMES_KEY = "name";
    public static final String PASS_KEY = "pass";


    public DatabaseHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + NAMES_KEY +
                " STRING PRIMARY KEY," + PASS_KEY + " TEXT" + ")");
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void deleteAllItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

    public boolean insertItem (String name, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMES_KEY, name);
        contentValues.put(PASS_KEY, pass);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    Password getItem(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from passwords where name="+name+"", null);
        cursor.close();
        db.close();
        return (new Password(cursor.getString(0), cursor.getString(1)));
    }

    boolean updateItem (String name, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMES_KEY, name);
        contentValues.put(PASS_KEY, pass);
        db.update (TABLE_NAME, contentValues, "name = ? ", new String[] {name});
        db.close();
        return true;
    }

    public void deleteItem (String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "name = ? ", new String[]{name});
        db.close();

    }

    public ArrayList <Password> getAllItems(){
        ArrayList<Password> list = new ArrayList<Password>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from passwords", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){

            list.add (new Password(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;

    }




}

package com.example.mymedical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(username text, password text)");
        db.execSQL("create table event(category text, date text, creator text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createEvent(String category, String date, String creator){
        ContentValues cv = new ContentValues();
        cv.put("category", category);
        cv.put("date", date);
        cv.put("creator", creator);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("events", null, cv);
        db.close();
    }


    public void register(String username, String password){
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    public int login (String username, String password){
        int result = 0;
        String[] str = new String[2];
        str[0] = username;
        str[1] = password;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username=? and password=?",str);


        if(c.moveToFirst()){
            result = 1;
        }

        return result;

    }
}

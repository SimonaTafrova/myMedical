package com.example.mymedical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(username text, password text, fullname text)");
        db.execSQL("create table event(message text, date text, viewed int)");
        db.execSQL("create table gpLogs(doctor text, topic text, date text)");
        db.execSQL("create table sensors(name text, quantity int)");
        ContentValues cv = new ContentValues();
        cv.put("name", "Freestyle Libre");
        cv.put("quantity", 0);
        db.execSQL("insert into sensors (name, quantity) values ('FreestyleLibre', 0)");
        db.execSQL("insert into sensors (name, quantity) values ('Dexcom', 0)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createEvent(String message, String date){
        ContentValues cv = new ContentValues();
        cv.put("message", message);
        cv.put("date", date);
        cv.put("viewed", 0);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("event", null, cv);
        db.close();
    }

    public void createSensors(){
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        cv1.put("name", "Freestyle Libre");
        cv1.put("quantity", 0);
        cv2.put("name", "Dexcom");
        cv2.put("quantity", 0);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("sensors", null, cv1);
        db.insert("sensors", null, cv2);
        db.close();
    }


    public void register(String username, String password, String fullname){
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("fullname", fullname);

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

    public int isUniqueUsername (String username){
        int result = 0;
        String[] str = new String[1];
        str[0] = username;


        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username=?",str);


        if(c.moveToFirst()){
            result = 1;
        }

        return result;

    }

    public void saveGPLog(String doctor, String topic, String date){
        ContentValues cv = new ContentValues();
        cv.put("doctor", doctor);
        cv.put("topic", topic);
        cv.put("date", date);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("gpLogs", null, cv);
        db.close();
    }


    public List<String> getAllGPLogs(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from gpLogs", null);
        List<String> result = new ArrayList<>();
        int count = 0;

        if (c.moveToLast()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                result.add(c.getString(0) + " : " +  c.getString(1) + " - " + c.getString(2));
                count++;
            } while (c.moveToPrevious() && count < 10);
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        c.close();

        return result;

    }

    public Map<String, Integer> getAllSensors(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from sensors", null);
        Map<String, Integer> result = new HashMap<>();

        if (c.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.

                result.put(c.getString(0), Integer.parseInt(c.getString(1)));

            } while (c.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        c.close();

        return result;

    }

    public List<Event> getAllEvents(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from event", null);

        List<Event> allEvents = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.

                Event currentEvent = new Event(c.getString(0), c.getString(1), Integer.parseInt(c.getString(2)));
                allEvents.add(currentEvent);

            } while (c.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        c.close();

        return allEvents;



    }

    public void increase(String name, int currentValue) {
        ContentValues cv = new ContentValues();
        cv.put("quantity", currentValue + 1);
        SQLiteDatabase db = getWritableDatabase();
        db.update("sensors", cv, "name = ?", new String[]{name});



    }

    public void decrease(String name, int currentValue) {
        ContentValues cv = new ContentValues();
        if(currentValue - 1 < 0){
            cv.put("quantity", 0);
        } else {
            cv.put("quantity", currentValue - 1);
            createEvent("Started new sensor", LocalDate.now().toString());
        }

        SQLiteDatabase db = getWritableDatabase();
        db.update("sensors", cv, "name = ?", new String[]{name});

        if(currentValue - 1 <=1){
            createEvent("You need to order " + name + " sensors!", LocalDate.now().toString());
        }



    }

    public void viewAllEvents(){
        SQLiteDatabase dbRead = getReadableDatabase();
        Cursor c = dbRead.rawQuery("select * from event", null);
        SQLiteDatabase dbWrite = getWritableDatabase();

        if (c.moveToFirst()) {
            do {
                String message = c.getString(0);
                String date = c.getString(1);
                ContentValues cv = new ContentValues();
                cv.put("date", date);
                cv.put("viewed", 1);
                dbWrite.update("event", cv, "message = ?", new String[]{message});
                // on below line we are adding the data from
                // cursor to our array list.

            } while (c.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        c.close();

    }

    public List<Event> getLast10Events(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from event", null);

        List<Event> allEvents = new ArrayList<>();
        int count = 0;

        if (c.moveToLast()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                Event currentEvent = new Event(c.getString(0), c.getString(1), Integer.parseInt(c.getString(2)));
                allEvents.add(currentEvent);
                count++;
            } while (c.moveToPrevious() && count < 10);
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        c.close();

        return allEvents;

    }

    public int getSensorQuantity(String name){
        SQLiteDatabase db = getReadableDatabase();
        String [] str = new String[1];
        str[0] = name;
        Cursor c = db.rawQuery("select * from sensors where name=?",str);
        c.moveToFirst();
        int quantity = Integer.parseInt(c.getString(1));
        c.close();

        return quantity;




    }

    public String getFullName(String username){
        SQLiteDatabase db = getReadableDatabase();
        String [] str = new String[1];
        str[0] = username;
        Cursor c = db.rawQuery("select * from users where username=?",str);
        c.moveToFirst();
        String fullName = c.getString(2);
        c.close();

        return fullName;




    }
}

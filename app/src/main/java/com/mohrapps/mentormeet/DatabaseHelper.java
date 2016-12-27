package com.mohrapps.mentormeet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Katherine on 12/4/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_UNAME = "uname";
    private static final String COLUMN_PASS = "pass";
    private static final String COLUMN_MENTOR = "mentor";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table contacts(id integer primary key not null, " +
            "name text not null, email text not null, uname text not null, pass text not null, mentor integer not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public String searchPass(String username) {
        db = this.getReadableDatabase();
        String b = "not found";
        if (db.isOpen()) {
            String query = "SELECT uname, pass FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            //a is uname that cursor is on; b is pass
            String a;
            if (cursor.moveToFirst()) {
                do {
                    a = cursor.getString(0);
                    if (a.equals(username)) {
                        b = cursor.getString(1);
                        break;
                    }
                } while (cursor.moveToNext());
            }
        }
        return b;
    }

    public boolean searchUnames(String username) {
        db = this.getReadableDatabase();
        boolean isAUniqueUname = true;
        if (db.isOpen()) {
            String query = "SELECT "+COLUMN_UNAME+" FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            //a is uname that cursor is on
            String a;
            if (cursor.moveToFirst()) {
                do {
                    a = cursor.getString(0);
                    if (a.equals(username)) {
                        isAUniqueUname = false;
                        break;
                    }
                } while (cursor.moveToNext());
            }
        }
        return isAUniqueUname;
    }

    public String getAllInfo() {
        db = this.getReadableDatabase();
        String info = "";
        if (db.isOpen()) {
            String query = "SELECT uname, pass FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            //a is uname that cursor is on; b is pass

            if (cursor.moveToFirst()) {
                do {
                    info += " uname: " + cursor.getString(0);
                    info += " pass: " + cursor.getString(1);
                } while (cursor.moveToNext());
            }

        } else {
            info = "db not open";
        }
        return info;
    }

    public void insertContact(Contact c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_PASS, c.getPass());
        values.put(COLUMN_UNAME, c.getUname());
        values.put(COLUMN_MENTOR, c.getMentor());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

}

package com.example.sqlitewithandroiddemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    //This is called the first time the database is accessed. There should be code here tha will generate the table
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    //This is called whenever the database version changes. It prevents previous users from breaking when you change the database design.
    // (Provides for forward/backward compatibility
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

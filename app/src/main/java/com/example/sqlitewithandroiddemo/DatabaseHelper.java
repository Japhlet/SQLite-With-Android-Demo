package com.example.sqlitewithandroiddemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "customer.db";
    public static final String TABLE_NAME = "customer_table";

    //Table columns
    public static final String COLUMN_CUSTOMER_ID ="CUSTOMER_ID";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_IS_ACTIVE_CUSTOMER ="IS_ACTIVE_CUSTOMER";
    public static final String COLUMN_IS_DELETED_CUSTOMER ="IS_DELETED_CUSTOMER";

    private boolean isDeleted = false;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //This is called the first time the database is accessed. There should be code here tha will generate the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INTEGER, " + COLUMN_IS_ACTIVE_CUSTOMER + " BOOL, " + COLUMN_IS_DELETED_CUSTOMER + " BOOL)";

        db.execSQL(createTableStatement);
    }

    //This is called whenever the database version changes. It prevents previous users from breaking when you change the database design.`
    // (Provides for forward/backward compatibility
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOneRecord(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModel.getAge());
        cv.put(COLUMN_IS_ACTIVE_CUSTOMER, customerModel.isActive());
        cv.put(COLUMN_IS_DELETED_CUSTOMER, isDeleted);

        long inserted = db.insert(TABLE_NAME, null, cv);

        if(inserted == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<CustomerModel> getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<CustomerModel> customerList = new ArrayList<>();

        //Get data from the database
        String queryString = "SELECT * FROM " + TABLE_NAME; // + " WHERE " + COLUMN_IS_DELETED_CUSTOMER + " = " + isDeleted;

        //Cursor is a resultset from an SQL Statement
        Cursor cursor =  db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //Loop through the cursor (resultset) and create a new customer object into the returnCustomerList
            do {
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean isCustomerActive = cursor.getInt(3) == 1 ? true : false;

                CustomerModel customerModel = new CustomerModel(customerId, customerName, customerAge, isCustomerActive);
                customerList.add(customerModel);
            } while(cursor.moveToNext());
        } else {
            //Failure. Do not add anything to the customerList
        }

        //Close both the CUrsor and the db when done
        cursor.close();
        db.close();

        return customerList;
    }

    public boolean deleteOneRecord(CustomerModel customerModel) {
        //Find customerModel in the database. If it is found, delete and return true.
        //If it is not found, return false

        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DELETE FROM "+ TABLE_NAME + " WHERE " + COLUMN_CUSTOMER_ID + " = " + customerModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
}

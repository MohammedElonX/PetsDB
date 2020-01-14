package com.example.petsdb.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.petsdb.data.PetsContract.PetEntry;



public class HelperDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 1;

    public HelperDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE(_id INTEGER PRIMARY KEY AUTO INCREMENT,
        // name TEXT NOT NULL, breed TEXT, gender INTEGER NOT NULL, weight INTEGER NOT NULL DEFAULT 0
        String SQl_CREATE_TABLES = "CREATE TABLE" + PetEntry.TABLE_NAME + "(" + PetEntry._ID +
                "INTEGER PRIMARY KEY AUTO INCREMENT" + PetEntry.COLUMN_PET_NAME + "TEXT NOT NULL"
                + PetEntry.COLUMN_PET_BREED + "TEXT" + PetEntry.COLUMN_PET_GENDER + "INTEGER NOT NULL"
                + PetEntry.COLUMN_PET_WEIGHT + "INTEGER NOT NULL DEFAULT 0";

        db.execSQL(SQl_CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

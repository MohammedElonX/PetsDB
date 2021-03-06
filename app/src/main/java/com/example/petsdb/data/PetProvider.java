package com.example.petsdb.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

public class PetProvider extends ContentProvider {
    //URI matcher for table
    private static final int PETS = 100;
    //URI matcher for specific pet in table
    private static final int PETS_ID = 101;

    private HelperDB mHelperDB;

    private static final UriMatcher sUriMAtcher = new UriMatcher(UriMatcher.NO_MATCH);

    //Static initializer, this is run the first time anything is called form this class
    static{
        sUriMAtcher.addURI(PetsContract.CONTENT_AUTHORITY, PetsContract.PATH_PET, PETS);

        sUriMAtcher.addURI(PetsContract.CONTENT_AUTHORITY, PetsContract.PATH_PET + "/#", PETS);

    }

    @Override
    public boolean onCreate() {
        mHelperDB = new HelperDB(getContext());
        return true;
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase = mHelperDB.getReadableDatabase();

        //This cursor will hold the result of the query
        Cursor cursor;

        //Figure out if the URI matcher can match the URI to a specific URI
        int match = sUriMAtcher.match(uri);
        switch (match){
            case PETS:
                cursor = sqLiteDatabase.query(PetsContract.PetEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PETS_ID:
               selection = PetsContract.PetEntry._ID + "=?";
               selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
               cursor = sqLiteDatabase.query(PetsContract.PetEntry.TABLE_NAME, projection, selection, selectionArgs,
                       null, null, sortOrder);
               break;
               default:
                    throw new IllegalArgumentException("cannot query " + uri);
        }
        //set notification uri and updates with changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

       return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert( Uri uri, ContentValues values) {
        final int match = sUriMAtcher.match(uri);
        switch(match){
            case PETS:
                return insertPet(uri, values);
            default:
                throw new IllegalArgumentException("Insertion error for " + uri);
        }
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    private Uri insertPet(Uri uri, ContentValues values) {
        //Check that name is not null
        String name = values.getAsString(PetsContract.PetEntry.COLUMN_PET_NAME);
        if(name == null){
            throw new IllegalArgumentException("Pet requires a name");
        }

        //Check if gender is valid
        Integer weight = values.getAsInteger(PetsContract.PetEntry.COLUMN_PET_WEIGHT);
        if(weight == null){
            throw new IllegalArgumentException("Please add weight");
        }

        //Insert pet into db with given contentValues
        SQLiteDatabase db = mHelperDB.getReadableDatabase();
        db.insert(PetsContract.PetEntry.TABLE_NAME, null, values);

        //Notify listeners of change in data
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, 6);
    }
}

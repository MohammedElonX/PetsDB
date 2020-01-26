package com.example.petsdb;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.petsdb.data.HelperDB;
import com.example.petsdb.data.PetsContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PET_LOADER = 0;

    PetCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView petListView = findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        mCursorAdapter = new PetCursorAdapter(this, null);
        petListView.setAdapter(mCursorAdapter);

        //Set up onClickListener
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                //Forms content Uri for clicked pet
               Uri currentPet = ContentUris.withAppendedId(PetsContract.PetEntry.CONTENT_URI, id);

               //Set uri on data field of intent
                intent.setData(currentPet);

                //Launch to display data
                startActivity(intent);

            }
        });

        //Kicks off manager
        getSupportLoaderManager().initLoader(PET_LOADER, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void insertPets(){

        ContentValues values = new ContentValues();
        values.put(PetsContract.PetEntry.COLUMN_PET_NAME, "Jack");
        values.put(PetsContract.PetEntry.COLUMN_PET_BREED, "Alsatian");
        values.put(PetsContract.PetEntry.COLUMN_PET_GENDER, PetsContract.PetEntry.GENDER_MALE);
        values.put(PetsContract.PetEntry.COLUMN_PET_WEIGHT, 20);

        getContentResolver().insert(PetsContract.PetEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                insertPets();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        //Define projection for columns from table
        String[] projection = {
                PetsContract.PetEntry._ID,
                PetsContract.PetEntry.COLUMN_PET_NAME,
                PetsContract.PetEntry.COLUMN_PET_BREED
        };

        //this Loader executes the ContentProvider query method on a backGround thread
        return new CursorLoader(this, PetsContract.PetEntry.CONTENT_URI,
                projection, null, null, null);
    }
//
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        //Update cursorAdapter with this new cursor
        mCursorAdapter.swapCursor(cursor);
    }
//
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}

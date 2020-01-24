package com.example.petsdb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.petsdb.data.HelperDB;
import com.example.petsdb.data.PetsContract;

public class MainActivity extends AppCompatActivity {
    private HelperDB mHelperDb;

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

        mHelperDb = new HelperDB(this);

        displayDataBaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDataBaseInfo();
    }

    public void displayDataBaseInfo(){
        HelperDB mHelperDB = new HelperDB(this);

        String[] projection = {PetsContract.PetEntry._ID, PetsContract.PetEntry.COLUMN_PET_NAME,
                PetsContract.PetEntry.COLUMN_PET_BREED, PetsContract.PetEntry.COLUMN_PET_WEIGHT};

        //Perform query from ContextResolver with ContentProvider(PetProvider) methods
        Cursor cursor = getContentResolver().query(PetsContract.PetEntry.CONTENT_URI, projection,
                null, null, null);

        ListView petListView = findViewById(R.id.list);

        PetCursorAdapter adapter = new PetCursorAdapter(this, cursor);

        petListView.setAdapter(adapter);
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
                displayDataBaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

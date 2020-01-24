package com.example.petsdb;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.petsdb.data.PetsContract;

public class PetCursorAdapter extends CursorAdapter {

    PetCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item ,parent ,false) ;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = view.findViewById(R.id.name);
        TextView summary = view.findViewById(R.id.summary);

        //Find columns of pet
        int nameColumnIndex = cursor.getColumnIndex(PetsContract.PetEntry.COLUMN_PET_NAME);
        int petBreedIndex = cursor.getColumnIndex(PetsContract.PetEntry.COLUMN_PET_BREED);

        //Read pet attributes from cursor of current pet
        String petName = cursor.getString(nameColumnIndex);
        String petBreed = cursor.getString(petBreedIndex);

        //Update textViews of current pet
        name.setText(petName);
        summary.setText(petBreed);
    }
}

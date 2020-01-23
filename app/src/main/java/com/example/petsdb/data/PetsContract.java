package com.example.petsdb.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class PetsContract {
    public static final String CONTENT_AUTHORITY = "com.example.petsdb";
    public static final String PATH_PET = "pets";

    private PetsContract(){}

    public static final class PetEntry implements BaseColumns {
        public final static String TABLE_NAME = "pets";

        private static final Uri BASE_CONTENT = Uri.parse("content://com.example.petsdb");
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT, PATH_PET);

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PET_NAME = "name";
        public final static String COLUMN_PET_BREED = "breed";
        public final static String COLUMN_PET_GENDER = "gender";
        public final static String COLUMN_PET_WEIGHT = "weight";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;


    }
}

package com.odinarts.android.storagescanner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.odinarts.android.storagescanner.db.FilesContract.FilesEntry;
import com.odinarts.android.storagescanner.db.ExtensionsContract.ExtensionsEntry;

public class ScannerDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "scanner.db";

    private static final String CREATE_TABLE_FILES = "CREATE TABLE " + FilesEntry.TABLE_NAME +
            " ( " + FilesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FilesEntry.COLUMN_NAME + " TEXT NOT NULL, " + FilesEntry.COLUMN_PATH +
            " TEXT NOT NULL, " + FilesEntry.COLUMN_LENGTH + " INTEGER NOT NULL);";

    private static final String CREATE_TABLE_EXTENSIONS = "CREATE TABLE " + ExtensionsEntry.TABLE_NAME +
            " ( " + ExtensionsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ExtensionsEntry.COLUMN_EXTENSION + " TEXT NOT NULL, " + ExtensionsEntry.COLUMN_COUNT +
            " INTEGER NOT NULL);";

    public ScannerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FILES);
        db.execSQL(CREATE_TABLE_EXTENSIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // On upgrade drop older tables.
        db.execSQL("DROP TABLE IF EXISTS " + FilesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ExtensionsEntry.TABLE_NAME);

        // create new tables
        onCreate(db);
    }
}

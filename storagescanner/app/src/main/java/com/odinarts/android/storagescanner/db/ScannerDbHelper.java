package com.odinarts.android.storagescanner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.odinarts.android.storagescanner.db.ExtensionsContract.ExtensionsEntry;
import com.odinarts.android.storagescanner.db.FilesContract.FilesEntry;
import com.odinarts.android.storagescanner.model.Extension;
import com.odinarts.android.storagescanner.model.FileData;

import java.io.File;
import java.util.ArrayList;

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

    /**
     * Delete all data from tables.
     */
    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FilesEntry.TABLE_NAME, null, null);
        db.delete(ExtensionsEntry.TABLE_NAME, null, null);
    }

    /**
     * Insert rows in the loop.
     * TODO: figure out how to optimize this.
     */
    public void insertFileEntries(ArrayList<File> files) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (File file : files) {
            String name = file.getName();
            String path = file.getPath();
            long length = file.length();

            values.put(FilesEntry.COLUMN_NAME, name);
            values.put(FilesEntry.COLUMN_PATH, path);
            values.put(FilesEntry.COLUMN_LENGTH, length);

            db.insert(FilesEntry.TABLE_NAME, null, values);
        }

        db.close();
    }

    /**
     * Get 10 longest files.
     * TODO: parameterize query.
     */
    public ArrayList<FileData> getTopFiles() {
        ArrayList<FileData> results = new ArrayList<FileData>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;

        try {
            cursor = db.rawQuery("select name, path, length from files order by length desc limit 10", null);
            if (cursor != null ) {
                if  (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String path = cursor.getString(cursor.getColumnIndex("path"));
                        long length = cursor.getLong(cursor.getColumnIndex("length"));
                        results.add(new FileData(name, path, length));
                    } while (cursor.moveToNext());
                }
            }
        }
        catch(Exception e) {

        }
        finally {
            db.close();
            return results;
        }
    }

    /**
     * Get list of top 5 most frequently used file extensions.
     * @return
     */
    public ArrayList<Extension> getTopExtensions() {
        ArrayList<Extension> results = new ArrayList<Extension>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;

        try {
            cursor = db.rawQuery("select extension, count from extensions order by count desc limit 5", null);
            if (cursor != null ) {
                if  (cursor.moveToFirst()) {
                    do {
                        String extension = cursor.getString(cursor.getColumnIndex("extension"));
                        int count = cursor.getInt(cursor.getColumnIndex("count"));
                        results.add(new Extension(extension, count));
                    } while (cursor.moveToNext());
                }
            }
        }
        catch(Exception e) {

        }
        finally {
            db.close();
            return results;
        }
    }

    /**
     * Get anverage file length.
     * @return
     */
    public long getAverageFileSize() {
        long result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;

        try {
            cursor = db.rawQuery("select avg(length) from files", null);
            if (cursor != null ) {
                cursor.moveToFirst();
                result = cursor.getLong(0);
            }
        }
        catch(Exception e) {

        }
        finally {
            db.close();
            return result;
        }
    }
}

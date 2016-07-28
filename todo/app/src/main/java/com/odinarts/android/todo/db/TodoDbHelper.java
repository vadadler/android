package com.odinarts.android.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.odinarts.android.todo.db.TodoContract.TodoEntry;

public class TodoDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todo.db";

    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TodoContract.TodoEntry.TABLE_NAME + " ( " +
            TodoContract.TodoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TodoEntry.COLUMN_NAME_TEXT + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

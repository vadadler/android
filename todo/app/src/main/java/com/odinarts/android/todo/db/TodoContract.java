package com.odinarts.android.todo.db;

import android.provider.BaseColumns;

public class TodoContract {
    // To prevent accidental instantiation.
    private TodoContract() {}

    public class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_NAME_TEXT = "text";
    }
}

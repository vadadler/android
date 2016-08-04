package com.odinarts.android.storagescanner.db;

import android.provider.BaseColumns;

public class FilesContract {
    // To prevent accidental instantiation.
    private FilesContract() {}

    public class FilesEntry implements BaseColumns {
        public static final String TABLE_NAME = "files";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_LENGTH = "length";
    }
}

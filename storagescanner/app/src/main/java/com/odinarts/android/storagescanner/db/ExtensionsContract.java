package com.odinarts.android.storagescanner.db;

import android.provider.BaseColumns;

public class ExtensionsContract {
    // To prevent accidental instantiation.
    private ExtensionsContract() {}

    public class ExtensionsEntry implements BaseColumns {
        public static final String TABLE_NAME = "extensions";
        public static final String COLUMN_EXTENSION = "extension";
        public static final String COLUMN_COUNT = "count";
    }
}

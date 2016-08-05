package com.odinarts.android.storagescanner;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.odinarts.android.storagescanner.db.ExtensionsContract.ExtensionsEntry;
import com.odinarts.android.storagescanner.db.FilesContract.FilesEntry;
import com.odinarts.android.storagescanner.db.ScannerDbHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DoWork extends AsyncTask {
    public static final String TAG = "OA.DoWork";

    DoWorkFragment mContainer;

    public DoWork(DoWorkFragment f) {
        mContainer = f;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        ArrayList<File> files = (ArrayList<File>)params[0];

        HashMap<String, Integer> hashExtensions = new HashMap<String, Integer>();

        ScannerDbHelper dbHelper = new ScannerDbHelper(mContainer.getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        // TODO: optimize process of populating db.
        for (int count = 0; count < files.size(); count++) {
            publishProgress(count);

            if (isCancelled() == true) {
                Log.i(TAG, "Work is cancelled");
                break;
            }

            // Insert entry into files table.
            File file = files.get(count);

            String name = file.getName();
            String path = file.getPath();
            long length = file.length();
            String extension = Utils.getFileExtension(file);

            values.put(FilesEntry.COLUMN_NAME, name);
            values.put(FilesEntry.COLUMN_PATH, path);
            values.put(FilesEntry.COLUMN_LENGTH, length);

            db.insert(FilesEntry.TABLE_NAME, null, values);

            values.clear();

            // Populate hashtable of extensions.
            Integer cnt = hashExtensions.get(extension);
            cnt = (cnt == null) ? 1 : ++cnt;

            hashExtensions.put(extension, cnt);
        }

        // Populate extenstions table
        for(String extension : hashExtensions.keySet()) {
            values.clear();
            values.put(ExtensionsEntry.COLUMN_EXTENSION, extension);
            values.put(ExtensionsEntry.COLUMN_COUNT, hashExtensions.get(extension));

            db.insert(ExtensionsEntry.TABLE_NAME, null, values);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(mContainer!=null && mContainer.getActivity()!=null) {
            mContainer.updateProgress(Utils.TASK_COMPLETED);
            mContainer.hideProgressBar();
            mContainer = null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Create db.
        // TODO: figure out how to optimize this and not create on each preexecute.
        if(mContainer != null) {
            ScannerDbHelper dbHelper = new ScannerDbHelper(mContainer.getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Log.i(TAG, dbHelper.getDatabaseName());

            mContainer.showProgressBar();
        }
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        if(mContainer!=null && mContainer.getActivity()!=null) {
            mContainer.updateProgress((Integer)values[0]);
            super.onProgressUpdate(values);
        }
    }
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(mContainer!=null && mContainer.getActivity()!=null) {
            mContainer.updateProgress(Utils.TASK_CANCELLED);
            mContainer.hideProgressBar();
            mContainer = null;
        }
    }

}

package com.odinarts.android.todo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.odinarts.android.todo.db.TodoContract;
import com.odinarts.android.todo.db.TodoContract.TodoEntry;
import com.odinarts.android.todo.db.TodoDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TODO.MainActivity";

    private TodoDbHelper mDbHelper;
    private ListView mTodoListView;
    private TodoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new TodoDbHelper(this);
        mTodoListView = (ListView) findViewById(R.id.list_todo);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_todo:
                final EditText editTextTodo = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.add_dialog_title)
                    .setMessage(R.string.add_dialog_message)
                    .setView(editTextTodo)
                    .setPositiveButton(R.string.add_dialog_positive_button,
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String todo = String.valueOf(editTextTodo.getText());
                            SQLiteDatabase db = mDbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(TodoEntry.COLUMN_NAME_TEXT, todo);
                            db.insertWithOnConflict(TodoEntry.TABLE_NAME,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE);
                            db.close();

                            updateUI();
                        }
                    })
                    .setNegativeButton(R.string.add_dialog_negative_button, null)
                    .create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Delete Todo. NOTE: duplicates are not supported.
     * @param text text of the Todo to be deleted.
     */
    public void deleteTodo(String text) {
        Log.i(TAG, "text=" + text);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(TodoEntry.TABLE_NAME, "text like ?", new String[] {text});
        db.close();
        updateUI();
    }

    /**
     * Refresh UI with data from database.
     */
    private void updateUI() {
        ArrayList<ToDo> todoList = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(TodoContract.TodoEntry.TABLE_NAME,
            new String[]{TodoContract.TodoEntry._ID, TodoEntry.COLUMN_NAME_TEXT},
            null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(TodoEntry.COLUMN_NAME_TEXT);
            todoList.add(new ToDo(cursor.getString(index), false));
        }

        if (mAdapter == null) {
            mAdapter = new TodoAdapter(this, todoList);
            mTodoListView.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(todoList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
}

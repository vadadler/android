package com.odinarts.android.todo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.odinarts.android.todo.db.TodoContract;
import com.odinarts.android.todo.db.TodoContract.TodoEntry;
import com.odinarts.android.todo.db.TodoDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TodoDbHelper mDbHelper;
    private ListView mTodoListView;
    private ArrayAdapter<String> mAdapter;
    private TodoAdapter mAdapter2;

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
     * Refresh UI with data from database.
     */
    private void updateUI() {
        ArrayList<String> todoList = new ArrayList<>();
        ArrayList<ToDo> todoList2 = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(TodoContract.TodoEntry.TABLE_NAME,
            new String[]{TodoContract.TodoEntry._ID, TodoEntry.COLUMN_NAME_TEXT},
            null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(TodoEntry.COLUMN_NAME_TEXT);
            todoList.add(cursor.getString(index));
            todoList2.add(new ToDo(cursor.getString(index), false));
        }

        if (mAdapter2 == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.todo_item,
                    R.id.task_text,
                    todoList);
            //mTodoListView.setAdapter(mAdapter);
            mAdapter2 = new TodoAdapter(this, todoList2);
            mTodoListView.setAdapter(mAdapter2);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(todoList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean isChecked = ((CheckBox) view).isChecked();

        findViewById(R.id.delete_todo_button).setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }
}

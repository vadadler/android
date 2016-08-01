package com.odinarts.android.todo;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

class TodoAdapter extends ArrayAdapter<ToDo> {
    public static final String TAG = "TODO.TodoApapter";

    private class ViewHolder {
        CheckBox check;
        TextView textView;
        ImageButton button;
    }

    LayoutInflater mInflater;
    ViewHolder mViewHolder;
    Context mActivityContext;

    public TodoAdapter(Context context, ArrayList<ToDo> todos) {
        super(context, 0, todos);
        mActivityContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get data for this position.
        ToDo todo = getItem(position);
        ViewHolder viewHolder;

        // Check if existing view is being reused. If not inflate the view.
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.todo_item, null);
            viewHolder = new ViewHolder();
            viewHolder.check = (CheckBox) convertView.findViewById(R.id.task_done);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.task_text);
            viewHolder.textView.setTag(position);
            viewHolder.button = (ImageButton) convertView.findViewById(R.id.delete_todo_button);
            viewHolder.button.setTag(viewHolder);
            viewHolder.check.setTag(viewHolder);

            // Click on checkbox.
            viewHolder.check.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder vh = (ViewHolder)v.getTag();
                    int position = (Integer)vh.textView.getTag();
                    ToDo todo = getItem(position);
                    Log.i(TAG, "position=" + position + " isDone=" + todo.isDone);
                    todo.isDone = todo.isDone ? false : true;
                    vh.button.setVisibility(todo.isDone ? View.VISIBLE : View.GONE);
                    vh.textView.setPaintFlags(todo.isDone ? (vh.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG) :
                            (vh.textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)));
                }
            });

            // Click on delete button.
            viewHolder.button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder vh = (ViewHolder)v.getTag();
                    int position = (Integer)vh.textView.getTag();
                    ToDo todo = getItem(position);
                    todo.isDone = false;
                    vh.button.setVisibility(todo.isDone ? View.VISIBLE : View.GONE);
                    vh.check.setChecked(todo.isDone);
                    ((MainActivity)mActivityContext).deleteTodo(todo.text);
                }
            });

            // Cache viewHolder.
            convertView.setTag(viewHolder);
        }
        else {
            // View is being recycled. Retrieve the viewHolder object from the tag.
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate data into the template view with the data object.
        viewHolder.textView.setText(todo.text);
        viewHolder.check.setChecked(todo.isDone);
        viewHolder.button.setVisibility(todo.isDone ? View.VISIBLE : View.GONE);

        // View to render.
        return convertView;
    }
}

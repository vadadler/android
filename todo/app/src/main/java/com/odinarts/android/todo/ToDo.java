package com.odinarts.android.todo;

/**
 * ToDo model object to represent each row in the ListView.
 */
public class ToDo {
    /** Description of the ToDo */
    public String text;

    /** Flag if the task is done (true) or not (false) */
    public boolean isDone;

    public ToDo(String t, boolean done) {
        text = t;
        isDone  = done;
    }
}

package com.odinarts.android.iq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MemoryLeaksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leaks);
    }
}

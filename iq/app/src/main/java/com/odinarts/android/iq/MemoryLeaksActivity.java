package com.odinarts.android.iq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MemoryLeaksActivity extends AppCompatActivity {
    private static MemoryLeaksActivity sMemoryLeaksActivity;
    private static View sView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leaks);

        Button saButton = (Button)findViewById(R.id.buttonStaticActivities);
        saButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStaticActivity();
                startDummyActivity("Static Activity");
            }
        });

        Button svButton = (Button)findViewById(R.id.buttonStaticViews);
        svButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStaticView();
                startDummyActivity("Static View");
            }
        });

    }

    @Override protected void onDestroy() {
        System.out.println("MemoryLeaksActivity.onDestroy");
        super.onDestroy();
    }

    private void setStaticActivity() {
        sMemoryLeaksActivity = this;
    }

    private void setStaticView() {
        sView = findViewById(R.id.buttonStaticViews);
    }
    /**
     * Force MemoryLeaksActivity to stop. Since DummyActivity covers it.
     */
    private void startDummyActivity(String how) {
        Intent intent = new Intent(this, DumbActivity.class);
        intent.putExtra("how", how);
        startActivity(intent);
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {
            System.out.println("startDummyActivity exception: " + e.getLocalizedMessage());
        }
        finish();
    }
}

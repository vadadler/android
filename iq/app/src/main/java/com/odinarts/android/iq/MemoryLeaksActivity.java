package com.odinarts.android.iq;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MemoryLeaksActivity extends AppCompatActivity {
    private static MemoryLeaksActivity sMemoryLeaksActivity;
    private static View sView;
    private static Object sInnerClass;

    class InnerClass {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leaks);

        // Static activity.
        Button saButton = (Button)findViewById(R.id.buttonStaticActivities);
        saButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStaticActivity();
                startDummyActivity(getResources().getString(R.string.memory_leak_static_activity),
                        getResources().getString(R.string.memory_leak_src_url));
            }
        });

        // Static view.
        Button svButton = (Button)findViewById(R.id.buttonStaticViews);
        svButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStaticView();
                startDummyActivity(getResources().getString(R.string.memory_leak_static_view),
                        getResources().getString(R.string.memory_leak_src_url));
            }
        });

        // Inner class.
        Button icButton = (Button)findViewById(R.id.buttonInnerClasses);
        icButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInstanceOfInnerClass();
                startDummyActivity(getResources().getString(R.string.memory_leak_inner_class),
                        getResources().getString(R.string.memory_leak_src_url));
            }
        });

        // Anonymous class.
        Button acButton = (Button)findViewById(R.id.buttonAnonymousClasses);
        acButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAnonymousClass();
                startDummyActivity(getResources().getString(R.string.memory_leak_anonymous_class),
                        getResources().getString(R.string.memory_leak_src_url));
            }
        });

        // Handler with anonymous Runnable.
        Button hButton = (Button)findViewById(R.id.buttonHandlers);
        hButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHandler();
                startDummyActivity(getResources().getString(R.string.memory_leak_anonymous_runnable),
                        getResources().getString(R.string.memory_leak_src_url));
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

    private void createInstanceOfInnerClass() {
        sInnerClass = new InnerClass();
    }

    // Handler is created on main thread. Until message is processed in 5 minutes in this example
    // the anonymously created runnable will keep reference to the Activitygx   .
    private void createHandler() {
        new Handler() {
            @Override public void handleMessage(Message message) {
                super.handleMessage(message);
            }
        }.postDelayed(new Runnable() {
            @Override public void run() {
                while(true);
            }
        }, 5*60*1000);
    }

    private void createAnonymousClass() {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                while(true) ;
            }
        };
    }

    /**
     * Force MemoryLeaksActivity to stop. Since DummyActivity covers it.
     */
    private void startDummyActivity(String how, String srcUrlMemoryLeaks) {
        Intent intent = new Intent(this, DumbActivity.class);
        intent.putExtra("how", how);
        intent.putExtra("srcUrl", srcUrlMemoryLeaks);
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

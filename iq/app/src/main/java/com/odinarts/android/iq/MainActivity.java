package com.odinarts.android.iq;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    public static final int MAX_STATUS = 100;
    protected ProgressBar mPBar;
    protected int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Cause ANR by looping and sleeping on main UI thread.
        findViewById(R.id.buttonAnr).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                for(int i = 0; i < 1000; i++) {
                    try {
                        Thread.sleep(100);
                    }
                    catch(Exception e) {
                        System.out.println("Exception: " + e.getLocalizedMessage());
                    }
                }
                Toast.makeText(MainActivity.this, "Done with the loop!", Toast.LENGTH_LONG).show();
            }
        });

        // Using AsyncTask to show progress.
        // This apporach is exposed to issue of 
        mPBar = (ProgressBar)findViewById(R.id.progressBar);
        mPBar.setVisibility(View.INVISIBLE);

        findViewById(R.id.buttonDownload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.mPBar.setVisibility(View.VISIBLE);
                MyAsyncTask myTask = new MyAsyncTask();
                myTask.execute();
            }
        });

        // Show progress using a worker thread and Handler.
        findViewById(R.id.buttonBgThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPBar.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(mProgressStatus < MAX_STATUS) {
                            try {
                                Thread.sleep(100);
                                mProgressStatus++;
                            }
                            catch(Exception e) {
                                System.out.println("BGThread exception: " + e.getLocalizedMessage());
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(mProgressStatus == MAX_STATUS) {
                                        mPBar.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        mPBar.setProgress(mProgressStatus);
                                    }
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        // Launch Contacts viewer activity.
        findViewById(R.id.buttonContacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });
    }

    private class MyAsyncTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            for(int i = 0; i < MAX_STATUS; i++) {
                try {
                    Thread.sleep(100);
                    publishProgress(i);
                }
                catch(Exception e) {
                    System.out.println("MyAsyncTask exception: " + e.getLocalizedMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mPBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
            mPBar.setProgress((Integer)values[0]);
        }
    }
}

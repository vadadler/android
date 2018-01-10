package com.odinarts.android.iq;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

//import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    public static final int MAX_STATUS = 100;
    protected ProgressBar mPBar;
    protected int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

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
        // This approach is exposed to the issue of leaking activities.
        // Where AsyncTask keeps invalid reference (due to activity restart i.e. screen rotation)
        // to parent activity.
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

        // Replace AsyncTask with observable.
        findViewById(R.id.buttonDownloadObservable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                MainActivity.this.mPBar.setVisibility(View.VISIBLE);

                // Subscription returns from Observable.subscribe(Subscriber) to allow unsubscribing.
                // Reference to Subscription stores instance of ActionSubscriber<T> which, among
                // other things has three final objects: onNext, onError, onCompleted.
                // Because of onNext, onError and onCompleted fields being final there is no clean
                // way to nullify them. The problem is that calling unsubscribe() on a Subscriber
                // only marks it as unsubscribed. Therefore, memory leak on unsubscibe().
                // https://medium.com/engineering-brainly/how-to-leak-memory-with-subscriptions-in-rxjava-ae0ef01ad361
                Subscription subscription = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (mProgressStatus = 0; mProgressStatus < MAX_STATUS; mProgressStatus++) {
                            try {
                                Thread.sleep(100);
                                subscriber.onNext(mProgressStatus);
                            }
                            catch (Exception e) {
                                subscriber.onError(e);
                            }
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        mPBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(Integer value) {
                        mPBar.setProgress(mProgressStatus);
                    }
                });

                compositeSubscription.add(subscription);
            }
        });

        // Show progress using a worker thread and Handler.
        // Also potential memory leak since Thread is declared and instantiated inside
        // inner anonymous class.
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

        // Launch Memory Leaks activity to demonstrate how memory can be leaked in Android.
        findViewById(R.id.buttonMemoryLeaks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoryLeaksActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        // clear() unsubscribes everything and then clears up the references.
        compositeSubscription.clear();
        super.onDestroy();
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

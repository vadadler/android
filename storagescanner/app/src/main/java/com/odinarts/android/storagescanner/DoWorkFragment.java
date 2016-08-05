package com.odinarts.android.storagescanner;

import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.odinarts.android.storagescanner.db.ScannerDbHelper;

import java.util.ArrayList;

public class DoWorkFragment extends Fragment {
    public static final String TAG = "OA.DoWorkFragment";
    public static final int REQUEST_READWRITE_STORAGE = 1;

    /** Background worker. */
    private DoWork mAsyncTask;


    private View mMainView;
    private ProgressBar mProgressBar;
    private ScannerDbHelper mDbHelper;
    private NotificationManager mNotifyManager;

    /** Number of files on exteranl storage. */
    private int mNumberOfFiles;

    /** Number of processed files on exteranl storage. */
    private int mNumProcessedFiles;

    public DoWorkFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "OnCreate: " + Environment.getExternalStorageDirectory().getAbsolutePath());

        // Hide or show progress bar.
        if(isTaskRunning(mAsyncTask)) {
            showProgressBar();
        }
        else {
            hideProgressBar();
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if(isTaskRunning(mAsyncTask)) {
            showProgressBar();
        }
        else {
            hideProgressBar();
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        mMainView = inflater.inflate(R.layout.fragment_do_work, container, false);
        Button button = (Button) mMainView.findViewById(R.id.button_start_scan);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check permission to access external storage at run time.
                // Per guidance delay asking for permission until it is absolutely necessary.
                int permisisonCheck = ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permisisonCheck != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                        // TODO: Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    }
                    else {
                        requestPermissions(new String[]
                                { android.Manifest.permission.READ_EXTERNAL_STORAGE },
                                REQUEST_READWRITE_STORAGE);
                    }
                }
                else {
                    ArrayList<java.io.File> files = Utils.getAllFiles();
                    if (files != null) {
                        mNumberOfFiles = files.size();
                        Log.i(TAG, "Total number of files=" + mNumberOfFiles);

                        String label = (String) ((Button) v).getText();
                        String strStart = getResources().getString(R.string.button_start_scan_label);
                        if (label.compareToIgnoreCase(strStart) == 0) {
                            mAsyncTask = new DoWork(DoWorkFragment.this);
                            mAsyncTask.execute(files);
                        } else {
                            if (mAsyncTask != null) {
                                mAsyncTask.cancel(true);
                                hideProgressBar();
                            }
                        }
                    }
                }
            }
        });

        mProgressBar = (ProgressBar)mMainView.findViewById(R.id.progress_bar);

        setRetainInstance(true);
        return mMainView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO: figure out how to gracefully deal with denied permission.

        switch (requestCode) {
            case REQUEST_READWRITE_STORAGE:
                if(grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // If user denied permission show toast and quit.
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.permission_denied),
                            Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
                else {  // Permission granted.

                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public void showProgressBar() {
        //ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.progress_bar);
        mProgressBar.setMax(mNumberOfFiles);
        mProgressBar.setProgress(mNumProcessedFiles);
        mProgressBar.setVisibility(View.VISIBLE);
        Button button = (Button) mMainView.findViewById(R.id.button_start_scan);
        button.setText(R.string.button_stop_scan_label);
        //progress.setIndeterminate(true);
/*
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(MainActivity.this);
        mBuilder.setContentTitle("Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_download);

        new Downloader().execute();
*/
        }

    public void hideProgressBar() {
        //ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.progress_bar);
        if(mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
            Button button = (Button) mMainView.findViewById(R.id.button_start_scan);
            button.setText(R.string.button_start_scan_label);
        }
    }

    public void populateResult(String s) {
        //TextView resultView = (TextView)getActivity().findViewById(R.id.textUrlContent);
        //resultView.setText(s);
    }

    public void updateProgress(int value) {
        if(mProgressBar != null) {
            //Log.i(TAG, "progress:" + value);
            mProgressBar.setProgress(value);
        }
    }

    /**
     * Helper method to determine if async task is running.
     * @param task
     * @return
     */
    protected boolean isTaskRunning(DoWork task) {
        Log.i(TAG, "isTaskRunning");

        if(task == null ) {
            return false;
        }
        else if(task.getStatus() == DoWork.Status.FINISHED){
            return false;
        }
        else {
            return true;
        }
    }
}

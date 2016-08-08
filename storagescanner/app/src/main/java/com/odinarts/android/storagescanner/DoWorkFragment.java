package com.odinarts.android.storagescanner;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.odinarts.android.storagescanner.chart.HorizontalBarChartActivity;
import com.odinarts.android.storagescanner.chart.PieChartActivity;
import com.odinarts.android.storagescanner.db.ScannerDbHelper;
import com.odinarts.android.storagescanner.model.Extension;
import com.odinarts.android.storagescanner.model.FileData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoWorkFragment extends Fragment {
    public static final String TAG = "OA.DoWorkFragment";
    public static final int REQUEST_READWRITE_STORAGE = 1;
    public static final int NOTIFICATION_ID = 1;

    /** Background worker. */
    private DoWork mAsyncTask;

    ArrayList<FileData> mFileData;
    ArrayList<Extension> mExtensionsData;
    long mAverageFileSize;

    private View mMainView;
    private ProgressBar mProgressBar;
    private ScannerDbHelper mDbHelper;

    /** Number of files on exteranl storage. */
    private int mNumberOfFiles;

    /** Number of processed files on exteranl storage. */
    private int mNumProcessedFiles;

    /** Notifications related members. */
    private NotificationManager mNotificationManager;
    private Builder mBuilder;

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
                        // Purge db if exists.
                        if(mDbHelper != null) {
                            mDbHelper.deleteData();
                        }

                        mNumberOfFiles = files.size();
                        Log.i(TAG, "Total number of files=" + mNumberOfFiles);

                        String label = (String) ((Button) v).getText();
                        String strStart = getResources().getString(R.string.button_start_scan_label);
                        if (label.compareToIgnoreCase(strStart) == 0) {
                            // Setup notification. Return to activity when notification is clicked.
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            PendingIntent pendingIntent =
                                PendingIntent.getActivity(
                                        getContext(),
                                        0,
                                        intent,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                );

                            mNotificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            mBuilder = new NotificationCompat.Builder(getActivity());
                            mBuilder.setContentTitle(getString(R.string.scan_notification_title))
                                    .setContentText(getString(R.string.scan_notification_text))
                                    .setSmallIcon(R.drawable.cast_ic_notification_connecting)
                                    .setContentIntent(pendingIntent);

                            mAsyncTask = new DoWork(DoWorkFragment.this);
                            mAsyncTask.execute(files);

                        } else {    // Stop process.
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
        mDbHelper = new ScannerDbHelper(getActivity());

        //hideButtons();
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
        mProgressBar.setMax(mNumberOfFiles);
        mProgressBar.setProgress(mNumProcessedFiles);
        mProgressBar.setVisibility(View.VISIBLE);
        Button button = (Button) mMainView.findViewById(R.id.button_start_scan);
        button.setText(R.string.button_stop_scan_label);
    }

    public void hideProgressBar() {
        if(mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
            Button button = (Button) mMainView.findViewById(R.id.button_start_scan);
            button.setText(R.string.button_start_scan_label);
        }
    }

    /**
     * TODO: put buttons inside a container to manage visibility only for one element.
     */
    public void showButtons() {
        ((Button)mMainView.findViewById(R.id.button_share_results)).setVisibility(View.VISIBLE);
        ((Button)mMainView.findViewById(R.id.button_largest_files)).setVisibility(View.VISIBLE);
        ((Button)mMainView.findViewById(R.id.button_extensions)).setVisibility(View.VISIBLE);
        ((Button)mMainView.findViewById(R.id.button_file_average_size)).setVisibility(View.VISIBLE);
    }

    public void hideButtons() {
        ((Button)mMainView.findViewById(R.id.button_share_results)).setVisibility(View.GONE);
        ((Button)mMainView.findViewById(R.id.button_largest_files)).setVisibility(View.GONE);
        ((Button)mMainView.findViewById(R.id.button_extensions)).setVisibility(View.GONE);
        ((Button)mMainView.findViewById(R.id.button_file_average_size)).setVisibility(View.GONE);
    }
    /**
     * Update progress. Called by async task. Update progress bar and notification.
     *
     * * @param value progress value. Utils.TASK_COMPLETED indicates end of processing.
     *          Utils.TASK_CANCELLED indicates scan had been cancelled.
     */
    public void updateProgress(int value) {
        if(mProgressBar != null) {
            //Log.i(TAG, "progress:" + value);
            if(value == Utils.TASK_COMPLETED) {
                // Scan is complete. Change notification text and remove progress bar.
                mBuilder.setContentText(getString(R.string.scan_notification_done_text))
                        .setSmallIcon(R.drawable.cast_ic_notification_on)
                        .setProgress(0, 0, false);

                buildReportDataset();
            }
            else if(value == Utils.TASK_CANCELLED) {
                // Scan cancelled. Update notification bar. Delete all data.
                mBuilder.setContentText(getString(R.string.scan_notification_cancelled_text))
                        .setSmallIcon(R.drawable.cast_ic_notification_disconnect)
                        .setProgress(0, 0, false);
                mDbHelper.deleteData();
            }
            else {
                mProgressBar.setProgress(value);
                mBuilder.setProgress(mNumberOfFiles, value, false);
            }

            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
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

    /**
     * Stop scanning when Back button is pressed.
     */
    public void backButtonWasPressed() {
        Log.i(TAG, "Back button was pressed. Stop scanning.");
        if(mAsyncTask != null) {
            mAsyncTask.cancel(true);
            hideProgressBar();
        }
    }

    public void onLargestFilesClick(View v) {
        // TODO: temp call
        buildReportDataset();
        Intent i = new Intent(getActivity(), HorizontalBarChartActivity.class);
        i.putParcelableArrayListExtra("data", mFileData);
        startActivity(i);
    }

    public void onExtensionsClick(View v) {
        // TODO: temp call
        buildReportDataset();
        Intent i = new Intent(getActivity(), PieChartActivity.class);
        i.putParcelableArrayListExtra("data", mExtensionsData);
        startActivity(i);
    }

    /**
     * Show toast with average file size.
     * @param v
     */
    public void onFileAverageSizeClick(View v) {
        // TODO: temp call
        buildReportDataset();
        Toast.makeText(getActivity(),
                getResources().getString(R.string.average_length) + mAverageFileSize,
                Toast.LENGTH_LONG).show();

    }

    /**
     * Use Android chooser to let user pick which app to use
     * to share results.
     * Results are shared as stringified JSON.
     */
    public void onShareResultsClick(View v) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArrayFileData = new JSONArray();
        JSONArray jsonArrayExtensionsData = new JSONArray();

        try {
            if (mExtensionsData != null) {
                for (int i = 0; i < mExtensionsData.size(); i++) {
                    jsonArrayExtensionsData.put(mExtensionsData.get(i).getJSONObject());
                }
            }

            if (mFileData != null) {
                for (int i = 0; i < mFileData.size(); i++) {
                    jsonArrayFileData.put(mFileData.get(i).getJSONObject());
                }
            }

            if (mAverageFileSize != 0) {
                jsonObject.put("average_length", mAverageFileSize);
            }

            jsonObject.put("extensions_data", jsonArrayExtensionsData);
            jsonObject.put("files_data", jsonArrayFileData);

            Log.i(TAG, jsonObject.toString());
        }
        catch (Exception e) {

        }
        
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, jsonObject.toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_results)));
    }

    /**
     * Query database and store results in ArrayLists and long.
     */
    private void buildReportDataset() {
        if(mDbHelper != null) {
            mFileData = mDbHelper.getTopFiles();
            mExtensionsData = mDbHelper.getTopExtensions();
            mAverageFileSize = mDbHelper.getAverageFileSize();
        }
    }
}

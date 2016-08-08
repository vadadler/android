package com.odinarts.android.storagescanner;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends FragmentActivity {
    public static final String TAG = "OA.MainActivity";

    private DoWorkFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "OnCreate:" + ((savedInstanceState == null) ? "" : savedInstanceState.toString()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            mFragment = new DoWorkFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mFragment, "DoWorkFragment").commit();
        }
        else {
            mFragment = (DoWorkFragment) getSupportFragmentManager().findFragmentByTag("DoWorkFragment");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFragment.backButtonWasPressed();
    }

    public void onLargestFilesClick(View v) {
        mFragment.onLargestFilesClick(v);
    }

    public void onExtensionsClick(View v) {
        mFragment.onExtensionsClick(v);
    }

    public void onFileAverageSizeClick(View v) {
        mFragment.onFileAverageSizeClick(v);
    }

    public void onShareResultsClick(View v) {
        mFragment.onShareResultsClick(v);
    }

}

package com.odinarts.android.storagescanner;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
    public static final String TAG = "OA.MainActivity";

    private DoWorkFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragment = new DoWorkFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mFragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFragment.backButtonWasPressed();
    }
}

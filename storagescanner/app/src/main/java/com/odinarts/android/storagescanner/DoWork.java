package com.odinarts.android.storagescanner;


import android.os.AsyncTask;
import android.util.Log;

public class DoWork extends AsyncTask {
    public static final String TAG = "OA.DoWork";

    DoWorkFragment mContainer;
    public DoWork(DoWorkFragment f) {
        this.mContainer = f;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            for (int count = 0; count <= (Integer)params[0]; count++) {
                // For demo purposes only. Slow down the progress.
                Thread.sleep(1);

                publishProgress(count);

                if (isCancelled() == true) {
                    Log.i(TAG, "Work is cancelled");
                    break;
                }
            }
        }
        catch(InterruptedException ie) {
            if (isCancelled() == true) {
                Log.i(TAG, "In InterruptedException: Work is cancelled");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(mContainer!=null && mContainer.getActivity()!=null) {
            mContainer.populateResult((String)o);
            mContainer.hideProgressBar();
            mContainer = null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mContainer.showProgressBar();
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        if(mContainer!=null && mContainer.getActivity()!=null) {
            mContainer.updateProgress((Integer)values[0]);
            super.onProgressUpdate(values);
        }
    }
}

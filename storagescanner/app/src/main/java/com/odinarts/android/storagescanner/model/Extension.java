package com.odinarts.android.storagescanner.model;

public class Extension {
    private int mId;
    private String mExtension;
    private int mCount;

    /** Constructors. */
    public Extension() {}
    public  Extension(int id, String extension, int count) {
        mId = id;
        mExtension = extension;
        mCount = count;
    }

    /** Setters. */
    public void setId(int id) {
        mId = id;
    }

    public void setExtension(String extension) {
        mExtension = extension;
    }

    public void setCount(int count) {
        mCount = count;
    }

    /** Getters. */
    public int getId() {
        return mId;
    }

    public String getExtension() {
        return mExtension;
    }

    public int getCount() {
        return mCount;
    }
}

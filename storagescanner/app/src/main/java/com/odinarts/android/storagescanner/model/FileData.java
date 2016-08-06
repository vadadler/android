package com.odinarts.android.storagescanner.model;

public class FileData {
    private int mId;
    private String mName;
    private String mPath;
    private long mLength;

    /** Constructors. */
    public FileData() {}
    public FileData(int id, String name, String path, long length) {
        mId = id;
        mName = name;
        mPath = path;
        mLength = length;
    }
    public FileData(String name, String path, long length) {
        mName = name;
        mPath = path;
        mLength = length;
    }


    /** Setters. */
    public void setId(int id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public void setLength(long length) {
        mLength = length;
    }

    /** Getters. */
    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPath() {
        return mPath;
    }

    public long getLength() {
        return mLength;
    }
}

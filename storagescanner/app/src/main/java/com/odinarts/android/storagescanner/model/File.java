package com.odinarts.android.storagescanner.model;

public class File {
    private int mId;
    private String mName;
    private String mPath;
    private int mLength;

    /** Constructors. */
    public File() {}
    public  File(int id, String name, String path, int length) {
        mId = id;
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

    public void setLength(int length) {
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

    public int getLength() {
        return mLength;
    }
}

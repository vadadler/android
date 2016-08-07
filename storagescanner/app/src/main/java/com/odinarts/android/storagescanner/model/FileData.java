package com.odinarts.android.storagescanner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FileData implements Parcelable {
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

    // Parcelable implementation.
    public static final Parcelable.Creator<FileData> CREATOR = new Creator<FileData>() {
        public FileData createFromParcel(Parcel source) {
            FileData mFileData = new FileData();
            mFileData.mName = source.readString();
            mFileData.mPath = source.readString();
            mFileData.mLength = source.readLong();
            return mFileData;
        }

        public FileData[] newArray(int size) {
            return new FileData[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mName);
        parcel.writeString(mPath);
        parcel.writeLong(mLength);
    }
}

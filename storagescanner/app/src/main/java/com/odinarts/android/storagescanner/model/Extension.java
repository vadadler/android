package com.odinarts.android.storagescanner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Extension implements Parcelable {
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
    public  Extension(String extension, int count) {
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

    // Parcelable implementation.
    public static final Parcelable.Creator<Extension> CREATOR = new Creator<Extension>() {
        public Extension createFromParcel(Parcel source) {
            Extension mExtension = new Extension();
            mExtension.mExtension = source.readString();
            mExtension.mCount = source.readInt();
            return mExtension;
        }

        public Extension[] newArray(int size) {
            return new Extension[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mExtension);
        parcel.writeInt(mCount);
    }

}

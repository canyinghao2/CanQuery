package com.canyinghao.canquery.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yangjian on 15/4/16.
 */
public class MainInfo implements Parcelable {

    private int id;

    private String key;
    private String string;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public MainInfo(String key, String string) {
        this.key = key;
        this.string = string;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.key);
        dest.writeString(this.string);
    }

    public MainInfo() {
    }

    private MainInfo(Parcel in) {
        this.id = in.readInt();
        this.key = in.readString();
        this.string = in.readString();
    }

    public static final Parcelable.Creator<MainInfo> CREATOR = new Parcelable.Creator<MainInfo>() {
        public MainInfo createFromParcel(Parcel source) {
            return new MainInfo(source);
        }

        public MainInfo[] newArray(int size) {
            return new MainInfo[size];
        }
    };
}

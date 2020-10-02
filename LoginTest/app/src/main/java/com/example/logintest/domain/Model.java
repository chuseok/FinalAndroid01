package com.example.logintest.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {

    private String id;
    private String title;
    private int desc;

    public Model(String title, int desc, String id) {
        this.title = title;
        this.desc = desc;
        this.id = id;

    }

    public Model(String id, String title) {
        this.id = id;
        this.title = title;
    }

    protected Model(Parcel in) {
        id = in.readString();
        title = in.readString();
        desc = in.readInt();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeInt(desc);
    }
}

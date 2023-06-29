package com.example.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Audio implements Serializable {
    // serializable to pass list of audio as extra in intent
    String title;
    String duration;
    String path;
    public Audio(String path, String title, String duration) {
        this.path = path;
        this.title = title;
        this.duration = duration;
    }
    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


}

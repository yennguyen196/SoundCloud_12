package com.framgia.yen.mymusic.data.model;


public class Artist {
    private String mName;

    public Artist() {
    }

    public Artist(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}

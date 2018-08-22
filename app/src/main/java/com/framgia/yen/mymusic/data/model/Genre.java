package com.framgia.yen.mymusic.data.model;

public class Genre {
    private String mName;
    private int mImageResource;

    private Genre(String name, int imageResource) {
        mName = name;
        mImageResource = imageResource;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public void setImageResource(int imageResource) {
        mImageResource = imageResource;
    }
}

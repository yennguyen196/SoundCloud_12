package com.framgia.yen.mymusic.data.model;

public class Genre {
    private String mName;
    private int mImageResource;

    public Genre(String name, int imageResource) {
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

    public static class GenreEntity{
        public static final String ALL_MUSIC = "all_music";
        public static final String ALL_AUDIO = "all_audio";
        public static final String ALTERNATIVE_ROOC = "alternative_rooc";
        public static final String AMBIENT = "ambient";
        public static final String CLASSICAL = "classical";
        public static final String COUNTRY = "country";
    }
}

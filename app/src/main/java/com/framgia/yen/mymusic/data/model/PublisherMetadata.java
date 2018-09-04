package com.framgia.yen.mymusic.data.model;

public class PublisherMetadata {
    private int mId;
    private String mArtist;

    public PublisherMetadata() {
    }

    public PublisherMetadata(int id, String artist) {
        mId = id;
        mArtist = artist;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }
}

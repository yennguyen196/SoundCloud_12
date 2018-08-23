package com.framgia.yen.mymusic.data.model;

public class Collection {
    private Track mTrack;
    private int mScore;

    public Collection() {
    }

    public Collection(Track track, int score) {
        mTrack = track;
        mScore = score;
    }

    public Track getTrack() {
        return mTrack;
    }

    public void setTrack(Track track) {
        mTrack = track;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }
}

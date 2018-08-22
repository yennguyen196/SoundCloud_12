package com.framgia.yen.mymusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {
    private int mId;
    private int mDuration;
    private String mTitile;
    private String mArtWorkUrl;
    private String mDownloadUrl;
    private int mLikeCount;
    private Artist mArtist;
    private boolean mIsDownloadable;
    private String mUri;

    public Track() {

    }

    private Track(Parcel in) {
        mId = in.readInt();
        mDuration = in.readInt();
        mTitile = in.readString();
        mArtWorkUrl = in.readString();
        mDownloadUrl = in.readString();
        mLikeCount = in.readInt();
        mIsDownloadable = in.readByte() !=0;
        mUri = in.readString();
        mArtist.setName(in.readString());
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mDuration);
        dest.writeString(mTitile);
        dest.writeString(mArtWorkUrl);
        dest.writeString(mDownloadUrl);
        dest.writeInt(mLikeCount);
        dest.writeByte((byte) (mIsDownloadable ? 1 :0));
        dest.writeString(mArtist.getName());
        dest.writeString(mUri);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getTitile() {
        return mTitile;
    }

    public void setTitile(String titile) {
        mTitile = titile;
    }

    public String getArtWorkUrl() {
        return mArtWorkUrl;
    }

    public void setArtWorkUrl(String artWorkUrl) {
        mArtWorkUrl = artWorkUrl;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getLikeCount() {
        return mLikeCount;
    }

    public void setLikeCount(int likeCount) {
        mLikeCount = likeCount;
    }

    public Artist getArtist() {
        return mArtist;
    }

    public void setArtist(Artist artist) {
        mArtist = artist;
    }

    public boolean isDownloadable() {
        return mIsDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mIsDownloadable = downloadable;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public static class TrackEntity{
        public static final String TABLE_NAME = "TRACK";
        public static final String ARTWORK_URL = "mArtWorkUrl";
        public static final String DOWNLOAD_URL = "mDownloadUrl";
        public static final String ID = "mId";
        public static final String DURATION = "mDuration";
        public static final String TITLE = "mTitle";
        public static final String LIKE_COUNT = "mLikeCount";
        public static final String ARTIST = "mArtist";
        public static final String DOWNLOADABLE = "mDownloadable";
    }
}

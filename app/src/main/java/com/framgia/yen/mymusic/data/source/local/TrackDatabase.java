package com.framgia.yen.mymusic.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.framgia.yen.mymusic.data.model.Track;


public class TrackDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MYMUSIC";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TRACK = "CREATE TABLE "
            + Track.TrackEntity.TABLE_NAME+ "( "
            + Track.TrackEntity.ID + " INTEGER NOT NULL PRIMARY KEY, "
            + Track.TrackEntity.TITLE + " TEXT, "
            + Track.TrackEntity.ARTIST + " TEXT, "
            + Track.TrackEntity.DURATION + " INTEGER,"
            + Track.TrackEntity.LIKE_COUNT + " INTEGER, "
            + Track.TrackEntity.ARTWORK_URL + " TEXT, "
            + Track.TrackEntity.DOWNLOADABLE + " INTEGER, "
            + Track.TrackEntity.DOWNLOAD_URL + " TEXT )";

    public TrackDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TRACK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

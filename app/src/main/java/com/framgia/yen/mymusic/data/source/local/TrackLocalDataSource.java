package com.framgia.yen.mymusic.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.source.TrackDataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    private static final String QUERY_DIRECTORY_NAME = "%SoundCloud12%";
    private static final String DB_SORT_COLUMN_ASC = "%s ASC";
    private static final String DB_QUERY_EQUAL_SELECTION = " %s = ?";
    private static TrackLocalDataSource sTrackLocalDataSource;
    private static TrackDatabase sTrackDatabase;
    private Context mContext;

    private TrackLocalDataSource(Context context) {
        this.mContext = context;
        sTrackDatabase = TrackDatabase.getInstance(context);
    }

    public static void init(Context context) {
        if (sTrackLocalDataSource == null) {
            sTrackLocalDataSource = new TrackLocalDataSource(context);
        }
    }

    public static TrackLocalDataSource getInstance() {
        return sTrackLocalDataSource;
    }

    @Override
    public void getTrackLocal(TrackDataSource.OnFetchDataListener<Track> listener) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.DATA + " LIKE ?";
        String[] selectionArgs = new String[]{QUERY_DIRECTORY_NAME};
        List<Track> tracks = readData(uri, selection, selectionArgs);
        if (tracks == null) {
            listener.onFetchDataFail(new Exception(mContext.getString(R.string.msg_load_failed)));
            return;
        }
        listener.onFetchDataSuccess(tracks);
    }

    @Override
    public void getTrackOfflineInFolder(String folderName, TrackDataSource.OnFetchDataListener<Track> listener) {
        List<Track> tracks = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
              MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };
        String sortByTitleAscending = String.format(DB_SORT_COLUMN_ASC,
                MediaStore.Audio.Media.TITLE);
        Cursor cursor = contentResolver.query(uri, projection, null, null, sortByTitleAscending);

        if(cursor == null){
            listener.onFetchDataFail(new Exception(mContext.getString(Integer.parseInt("Cant load track"))));
            return;
        }
        while (cursor.moveToNext()){
            Track track = getTrackFromCusor(cursor);
            tracks.add(track);
        }
        cursor.close();
        listener.onFetchDataSuccess(tracks);
    }

    private Track getTrackFromCusor(Cursor cursor) {
        Track track = new Track();
        track.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
        track.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
        track.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
        track.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
        track.setUri(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
        return track;
    }

    @Override
    public boolean deleteTrack(Track track) {
        SQLiteDatabase database = sTrackDatabase.getWritableDatabase();
        String where = String.format(DB_QUERY_EQUAL_SELECTION, Track.TrackEntity.ID);
        String[] selectionArgs = new String[]{String.valueOf(track.getId())};
        database.delete(Track.TrackEntity.TABLE_NAME, where, selectionArgs);
        database.close();
        return true;
    }

    @Override
    public boolean deleteOfflineTrack(Track track) {
        File file = new File(track.getUri());
        return file.delete();
    }

    @Override
    public void addTrackToFavorite(Track track) {
        SQLiteDatabase database = sTrackDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Track.TrackEntity.ID, track.getId());
        contentValues.put(Track.TrackEntity.TITLE, track.getTitle());
        contentValues.put(Track.TrackEntity.ARTIST, track.getArtist());
        contentValues.put(Track.TrackEntity.ARTWORK_URL, track.getArtWorkUrl());
        contentValues.put(Track.TrackEntity.DURATION, track.getDuration());
        database.insert(Track.TrackEntity.TABLE_NAME, null, contentValues);
        database.close();
    }

    @Override
    public List<Track> getTracksFavorite() {
        //TODO
        return null;
    }

    @Override
    public void removeTrackFromFavorite(Track track) {
        SQLiteDatabase database = sTrackDatabase.getWritableDatabase();
        String where = String.format(DB_QUERY_EQUAL_SELECTION, Track.TrackEntity.ID);
        String[] selectionArgs = new String[]{String.valueOf(track.getId())};
        database.delete(Track.TrackEntity.TABLE_NAME, where, selectionArgs);
    }

    @Override
    public List<Track> getTracks() {
        return null;
    }

    private List<Track> readData(Uri uri, String selection, String[] selectionArgs) {
        List<Track> tracks = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(uri, new String[]{MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.ARTIST},
                selection, selectionArgs, null);
        if (cursor == null) {
            return null;
        }
        int columnTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int columnDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int columnId = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int columnFilePath = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int columnArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Track track = new Track();
            track.setId(cursor.getInt(columnId));
            track.setArtist(cursor.getString(columnArtist));
            track.setDuration(cursor.getInt(columnDuration));
            track.setUri(cursor.getString(columnFilePath));
            track.setTitle(cursor.getString(columnTitle));
            tracks.add(track);
            cursor.moveToNext();
        }
        return tracks;
    }
}

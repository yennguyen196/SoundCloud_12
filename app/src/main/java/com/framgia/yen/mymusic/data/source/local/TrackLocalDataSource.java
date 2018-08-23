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
    private static final String QUERY_DIRECTORY_NAME = "%MySoundCloud%";
    private static final String VOLUMN_NAME_EXTERNAL = "external";
    private static final String DB_QUERY_EQUAL_SELECTION = " %s = ?";
    private static TrackLocalDataSource sTrackLocalDataSource;
    private static TrackDatabase sTrackDatabase;
    private Context mContext;


    public TrackLocalDataSource(Context context) {
        mContext = context;
    }
    public static void init(Context context){
        if(sTrackLocalDataSource == null){
            sTrackLocalDataSource = new TrackLocalDataSource(context);
        }
    }
    public static TrackLocalDataSource getInstance() {
        return sTrackLocalDataSource;
    }

    @Override
    public void getTrackLocal(TrackDataSource.onFetchDataListener<Track> listener) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.DATA;
        String[] selectionArgs = new String[]{QUERY_DIRECTORY_NAME};
        List<Track> tracks = readData(uri, selection, selectionArgs);
        if (tracks == null) {
            listener.onFetchDataFail(new Exception(mContext.getString(R.string.mgs_load_failed)));
            return;
        }
        listener.onFetchDataSuccess(tracks);
}
    @Override
    public boolean deleteTrack(Track track) {
        SQLiteDatabase database = sTrackDatabase.getWritableDatabase();
        String where = String.format(DB_QUERY_EQUAL_SELECTION, Track.TrackEntity.ID);
        String[] selectionArgs = new String[]{String.valueOf(track.getId())};
        database.delete(Track.TrackEntity.TABLE_NAME, where, selectionArgs );
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
        database.insert(Track.TrackEntity.TABLE_NAME, null, contentValues);
        database.close();
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
        Cursor cursor = contentResolver.query(uri, new String[]{MediaStore.Audio.Media.ALBUM_ID},
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

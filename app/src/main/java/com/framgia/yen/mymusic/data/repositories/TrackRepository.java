package com.framgia.yen.mymusic.data.repositories;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.source.TrackDataSource;
import com.framgia.yen.mymusic.data.source.TrackDataSource.LocalDataSource;
import com.framgia.yen.mymusic.data.source.TrackDataSource.RemoteDataSource;
import com.framgia.yen.mymusic.data.source.local.TrackLocalDataSource;
import com.framgia.yen.mymusic.data.source.remote.TrackRemoteDataSource;

import java.util.List;

public class TrackRepository implements TrackDataSource.LocalDataSource, TrackDataSource.RemoteDataSource {
    private static TrackRepository sTrackRepository;
    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;
    private List<Track> mTracks;

    private TrackRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static TrackRepository getInstance() {
        if (sTrackRepository == null) {
            sTrackRepository = new TrackRepository(TrackLocalDataSource.getInstance(),
                    TrackRemoteDataSource.getInstance());
        }
        return sTrackRepository;
    }

    @Override
    public void getTrackLocal(TrackDataSource.OnFetchDataListener<Track> listener) {
<<<<<<< HEAD
=======

>>>>>>> update download screen
            mLocalDataSource.getTrackLocal(listener);
    }

    @Override
    public void getTrackOfflineInFolder(String folderName, TrackDataSource.OnFetchDataListener<Track> listener) {
        if (mLocalDataSource != null ) {
            mLocalDataSource.getTrackOfflineInFolder(folderName, listener);
        }
    }

    @Override
    public void getTrackOfflineInFolder(String folderName, TrackDataSource.OnFetchDataListener<Track> listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.getTrackOfflineInFolder(folderName, listener);
        }
    }

    @Override
    public void getTrackOfflineInFolder(String folderName, TrackDataSource.OnFetchDataListener<Track> listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.getTrackOfflineInFolder(folderName, listener);
        }
    }

    @Override
    public boolean deleteTrack(Track track) {
        return false;
    }

    @Override
    public boolean deleteOfflineTrack(Track track) {
        return false;
    }

    @Override
    public void addTrackToFavorite(Track track) {
        mLocalDataSource.addTrackToFavorite(track);
    }

    @Override
    public List<Track> getTracksFavorite() {
        return mLocalDataSource.getTracksFavorite();
    }

    @Override
    public void removeTrackFromFavorite(Track track) {

    }
    @Override
    public List<Track> getTracks() {
        return null;
    }

    @Override
    public void getTrackRemote(String genre, int limit, int offset, TrackDataSource.OnFetchDataListener<Track> listener) {
        TrackRemoteDataSource.getInstance().getTrackRemote(genre, limit, offset, listener);
    }

    @Override
    public void serchTrackRemote(String name, int offset, TrackDataSource.OnFetchDataListener<Track> listener) {

    }
}

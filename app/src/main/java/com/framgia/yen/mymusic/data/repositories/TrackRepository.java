package com.framgia.yen.mymusic.data.repositories;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.source.TrackDataSource;
import com.framgia.yen.mymusic.data.source.TrackDataSource.LocalDataSource;
import com.framgia.yen.mymusic.data.source.TrackDataSource.RemoteDataSource;

import java.util.List;

public class TrackRepository implements TrackDataSource.LocalDataSource, TrackDataSource.RemoteDataSource {
    private static TrackRepository sTrackRepository;
    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;

    private TrackRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    private TrackRepository getInstance() {
        if (sTrackRepository == null) {
            sTrackRepository = new TrackRepository(mLocalDataSource, mRemoteDataSource);
        }
        return sTrackRepository;
    }

    @Override
    public void getTrackLocal(TrackDataSource.onFetchDataListener<Track> listener) {

    }

    @Override
    public boolean deleteTrack(Track track) {
        return false;
    }

    @Override
    public List<Track> getTracks() {
        return null;
    }

    @Override
    public void getTrackRemote(String genre, int limit, int offset, TrackDataSource.onFetchDataListener<Track> listener) {

    }

    @Override
    public void serchTrackRemote(String name, int offset, TrackDataSource.onFetchDataListener<Track> listener) {

    }
}

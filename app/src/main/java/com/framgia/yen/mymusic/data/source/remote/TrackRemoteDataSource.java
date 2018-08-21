package com.framgia.yen.mymusic.data.source.remote;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.source.TrackDataSource;

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {
    private static TrackRemoteDataSource sTrackRemoteDataSource;
    public TrackRemoteDataSource(){

    }
    private TrackRemoteDataSource getInstance(){
        if(sTrackRemoteDataSource == null){
            sTrackRemoteDataSource = new TrackRemoteDataSource();
        }
        return sTrackRemoteDataSource;
    }

    @Override
    public void getTrackRemote(String genre, int limit, int offset, TrackDataSource.onFetchDataListener<Track> listener) {

    }

    @Override
    public void serchTrackRemote(String name, int offset, TrackDataSource.onFetchDataListener<Track> listener) {

    }
}

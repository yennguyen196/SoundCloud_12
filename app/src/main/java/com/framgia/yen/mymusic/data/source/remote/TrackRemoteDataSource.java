package com.framgia.yen.mymusic.data.source.remote;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.source.TrackDataSource;
import com.framgia.yen.mymusic.utils.StringUtil;

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {
    private static TrackRemoteDataSource sTrackRemoteDataSource;
    private TrackRemoteDataSource() {
    }

    public static TrackRemoteDataSource getInstance() {
        if (sTrackRemoteDataSource == null) {
            sTrackRemoteDataSource = new TrackRemoteDataSource();
        }
        return sTrackRemoteDataSource;
    }

    @Override
    public void getTrackRemote(String genre, int limit, int offset,
                               TrackDataSource.OnFetchDataListener<Track> listener) {
        new BaseFetchTrackFromUrl(listener).execute(StringUtil.formatTrackUrl(genre, limit, offset));
    }

    @Override
    public void serchTrackRemote(String name, int offset, TrackDataSource.OnFetchDataListener<Track> listener) {

    }
}

package com.framgia.yen.mymusic.data.source;

import com.framgia.yen.mymusic.data.model.Track;

import java.util.List;

public interface TrackDataSource {
    /**
     * local data
     */
    interface LocalDataSource {
        void getTrackLocal(onFetchDataListener<Track> listener);

        void searchTrackLocal(String name, onFetchDataListener<Track> listener);

        boolean deleteTrack(Track track);

        List<Track> getTracks();
    }

    /**
     * remote data
     */
    interface RemoteDataSource {
        void getTrackRemote(String genre, int limit, int offset, onFetchDataListener<Track> listener);
        void serchTrackRemote(String name, int offset, onFetchDataListener<Track> listener);
    }

    interface onFetchDataListener<T>{
        void onFetchDataSuccess(List<T> data);
        void onFetchDataFail(String message);
    }
}

package com.framgia.yen.mymusic.data.source;

import com.framgia.yen.mymusic.data.model.Track;

import java.util.List;

public interface TrackDataSource {
    /**
     * local data
     */
    interface LocalDataSource {
        void getTrackLocal(OnFetchDataListener<Track> listener);
        void getTrackOfflineInFolder(String folderName, OnFetchDataListener<Track> listener);
        boolean deleteTrack(Track track);
        boolean deleteOfflineTrack(Track track);
        void addTrackToFavorite(Track track);
        List<Track> getTracksFavorite();
        void removeTrackFromFavorite(Track track);
        List<Track> getTracks();
    }

    /**
     * remote data
     */
    interface RemoteDataSource {
        void getTrackRemote(String genre, int limit, int offset, OnFetchDataListener<Track> listener);
        void serchTrackRemote(String name, int offset, OnFetchDataListener<Track> listener);
    }

    interface OnFetchDataListener<T>{
        void onFetchDataSuccess(List<T> data);
        void onFetchDataFail(Exception exption);
    }
}

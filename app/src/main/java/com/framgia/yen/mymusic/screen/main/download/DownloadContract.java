package com.framgia.yen.mymusic.screen.main.download;

import com.framgia.yen.mymusic.data.model.Track;

import java.util.List;

public interface DownloadContract {
    interface Presenter {
        void getOfflineTracks();
    }
    interface View {
        void showTracks(List<Track> tracks);
        void showNoTrack();
        void showError(String error);
    }
}

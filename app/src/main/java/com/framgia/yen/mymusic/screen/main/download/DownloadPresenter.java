package com.framgia.yen.mymusic.screen.main.download;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.repositories.TrackRepository;
import com.framgia.yen.mymusic.data.source.TrackDataSource;

import java.util.List;

public class DownloadPresenter implements DownloadContract.Presenter, TrackDataSource.OnFetchDataListener {
    private static final String DOWNLOAD_DIRECTORY = "SoundCloud12";
    private DownloadContract.View mView;
    private TrackRepository mTrackRepository;
    private TrackDataSource.OnFetchDataListener<Track> mListener;

    public DownloadPresenter(DownloadContract.View view, TrackRepository trackRepository) {
        mView = view;
        mTrackRepository = trackRepository;
    }

    @Override
    public void getOfflineTracks() {
        mTrackRepository.getTrackOfflineInFolder(DOWNLOAD_DIRECTORY,
                new TrackDataSource.OnFetchDataListener<Track>() {
            @Override
            public void onFetchDataSuccess(List<Track> data) {
                mView.showTracks(data);
            }

            @Override
            public void onFetchDataFail(Exception exption) {
            }
        });
    }

    @Override
    public void onFetchDataSuccess(List data) {
        if (data == null) {
            mView.showNoTrack();
        } else mView.showTracks(data);
    }

    @Override
    public void onFetchDataFail(Exception exption) {
        mView.showError(exption.getMessage());
    }
}

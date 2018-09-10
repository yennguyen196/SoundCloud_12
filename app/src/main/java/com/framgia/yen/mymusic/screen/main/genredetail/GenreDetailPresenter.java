package com.framgia.yen.mymusic.screen.main.genredetail;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.repositories.TrackRepository;
import com.framgia.yen.mymusic.data.source.TrackDataSource.OnFetchDataListener;

import java.util.List;

public class GenreDetailPresenter implements GenreDetailContract.Presenter, OnFetchDataListener<Track> {
    private GenreDetailContract.View mView;
    private TrackRepository mTrackRepository;
    private OnFetchDataListener<Track> mListener;

    public GenreDetailPresenter(GenreDetailContract.View view, TrackRepository trackRepository) {
        this.mView = view;
        this.mTrackRepository = trackRepository;
    }

    @Override
    public void loadTrack(String genre, int limit, int offset) {
        mTrackRepository.getTrackRemote(genre, limit, offset, this);
    }

    @Override
    public void dowloadTrack(Track track) {
        mTrackRepository.getTrackLocal(mListener);
    }

    @Override
    public void addFavorite(Track track) {
        mTrackRepository.addTrackToFavorite(track);
    }

    @Override
    public void onFetchDataSuccess(List<Track> data) {
        if (data.isEmpty()) {
            mView.noTrackAvailable();
        } else {
            mView.showTracks(data);
        }
    }

    @Override
    public void onFetchDataFail(Exception exption) {
        mView.showLoadingTrackError();
    }
}

package com.framgia.yen.mymusic.screen.main.favorite;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.repositories.TrackRepository;

import java.util.List;

public class FavoritePresenter implements FavoriteContract.Presenter {
    private FavoriteContract.View mView;
    private TrackRepository mTrackRepository;

    public FavoritePresenter(FavoriteContract.View view){
        mView = view;
        mTrackRepository = TrackRepository.getInstance();
    }


    @Override
    public void getAllFavoriteTracks() {
        List<Track> tracks = mTrackRepository.getTracksFavorite();
        mView.showFavoriteTracks(tracks);
    }
}

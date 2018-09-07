package com.framgia.yen.mymusic.screen.main.favorite;

import com.framgia.yen.mymusic.data.model.Track;

import java.util.List;

public interface FavoriteContract {
    interface Presenter {
        void getAllFavoriteTracks();
    }
    interface View {
       void showFavoriteTracks(List<Track> tracks);
    }
}

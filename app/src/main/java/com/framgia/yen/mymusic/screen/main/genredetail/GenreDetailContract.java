package com.framgia.yen.mymusic.screen.main.genredetail;

import com.framgia.yen.mymusic.data.model.Track;

import java.util.List;

public interface GenreDetailContract  {
    interface Presenter{
        void loadTrack(String genre, int limit, int offset);
    }
    interface View{
        void showTracks(List<Track> tracks);
        void noTrackAvailable();
        void showLoadingTrackError();
    }
}

package com.framgia.yen.mymusic.screen.main.genredetail;

import com.framgia.yen.mymusic.data.model.Track;

import java.util.List;

public interface GenreDetailContract  {
    interface Presenter{
        void loadTrack(String genre, int limit, int offset);

        void dowloadTrack(Track track);

        void addFavorite(Track track);

    }
    interface View{
        void showTracks(List<Track> tracks);
        void updateTrackInfor(Track track);
        void updateStateInfor(int state);
        void noTrackAvailable();
        void showLoadingTrackError();

    }
}

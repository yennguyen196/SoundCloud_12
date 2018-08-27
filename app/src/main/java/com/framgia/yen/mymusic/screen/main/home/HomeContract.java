package com.framgia.yen.mymusic.screen.main.home;

import com.framgia.yen.mymusic.data.model.Genre;

import java.util.List;

public interface HomeContract {
    interface Presenter {
        void loadGenres();
    }

    interface View {
        void showGenre(List<Genre> genres);
    }
}

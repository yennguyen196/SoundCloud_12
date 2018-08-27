package com.framgia.yen.mymusic.screen.main.home;

import com.framgia.yen.mymusic.data.model.Genre;
import com.framgia.yen.mymusic.data.repositories.GenresRepository;
import com.framgia.yen.mymusic.data.source.GenresDataSource;
import com.framgia.yen.mymusic.data.source.local.GenresLocalDataSource;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private GenresDataSource mGenreLocalDataSource;
    private GenresRepository mGenresRepository;

    public HomePresenter(HomeContract.View view, GenresDataSource genreLocalDataSource) {
        mView = view;
        mGenreLocalDataSource = genreLocalDataSource;
    }

    @Override
    public void loadGenres() {
        mGenreLocalDataSource = new GenresLocalDataSource();
        List<Genre> genres = mGenreLocalDataSource.getGenres();
        mView.showGenre(genres);
    }
}

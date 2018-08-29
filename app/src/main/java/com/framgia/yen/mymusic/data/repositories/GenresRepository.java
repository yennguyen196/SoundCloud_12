package com.framgia.yen.mymusic.data.repositories;

import com.framgia.yen.mymusic.data.model.Genre;
import com.framgia.yen.mymusic.data.source.GenresDataSource;

import java.util.List;

public class GenresRepository implements GenresDataSource {
    private static GenresRepository sInstance;
    private GenresDataSource mGenresLocalDataSource;

    private GenresRepository(GenresDataSource genresLocalDataSource) {
        mGenresLocalDataSource = genresLocalDataSource;
    }

    public static GenresRepository getInstance(GenresDataSource genresLocalDataSource) {
        if (sInstance == null) {
            sInstance = new GenresRepository(genresLocalDataSource);
        }
        return sInstance;
    }

    @Override
    public List<Genre> getGenres() {
        return mGenresLocalDataSource.getGenres();
    }
}

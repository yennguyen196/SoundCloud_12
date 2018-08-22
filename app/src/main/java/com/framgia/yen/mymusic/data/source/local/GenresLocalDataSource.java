package com.framgia.yen.mymusic.data.source.local;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Genre;
import com.framgia.yen.mymusic.data.source.GenresDataSource;

import java.util.ArrayList;
import java.util.List;

public class GenresLocalDataSource implements GenresDataSource {

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(Genre.GenreEntity.ALL_MUSIC, R.raw.all_music));
        genres.add(new Genre(Genre.GenreEntity.ALL_AUDIO, R.raw.all_audio));
        genres.add(new Genre(Genre.GenreEntity.ALTERNATIVE_ROOC,R.raw.alternative_rock));
        genres.add(new Genre(Genre.GenreEntity.AMBIENT, R.raw.ambient));
        genres.add(new Genre(Genre.GenreEntity.CLASSICAL, R.raw.classical));
        genres.add(new Genre(Genre.GenreEntity.COUNTRY, R.raw.country));
        return genres;
    }
}

package com.framgia.yen.mymusic.data.source.local;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Genre;
import com.framgia.yen.mymusic.data.source.GenresDataSource;

import java.util.ArrayList;
import java.util.List;

public class GenresLocalDataSource implements GenresDataSource {
    private static final String ALL_MUSIC = "ALL-MUSIC";
    private static final String ALL_AUDIO = "ALL-AUDIO";
    private static final String ALTERNATIVE_ROCK = "ALTERNATIVEROCK";
    private static final String AMBIENT = "AMBIENT";
    private static final String CLASSICAL = "CLASSICAL";
    private static final String COUNTRY = "COUNTRY";

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(ALL_MUSIC, R.raw.all_music));
        genres.add(new Genre(ALL_AUDIO, R.raw.all_audio));
        genres.add(new Genre(ALTERNATIVE_ROCK, R.raw.alternative_rock));
        genres.add(new Genre(AMBIENT, R.raw.ambient));
        genres.add(new Genre(CLASSICAL, R.raw.classical));
        genres.add(new Genre(COUNTRY, R.raw.country));
        return genres;
    }
}

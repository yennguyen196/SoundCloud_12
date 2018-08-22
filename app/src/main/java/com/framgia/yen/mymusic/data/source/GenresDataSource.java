package com.framgia.yen.mymusic.data.source;

import com.framgia.yen.mymusic.data.model.Genre;

import java.util.List;

public interface GenresDataSource {
    List<Genre> getGenres();
}

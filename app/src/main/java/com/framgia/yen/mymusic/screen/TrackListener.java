package com.framgia.yen.mymusic.screen;

import com.framgia.yen.mymusic.data.model.Track;

public interface TrackListener {
    void download(Track track);
    void addToFavorite(Track track);
}

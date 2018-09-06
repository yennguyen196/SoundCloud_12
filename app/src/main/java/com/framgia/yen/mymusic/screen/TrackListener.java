package com.framgia.yen.mymusic.screen;

import com.framgia.yen.mymusic.data.model.Track;

import java.util.List;

public interface TrackListener {
    void download(Track track);
    void addToFavorite(Track track);
    void onPlayTrack(int position, List<Track> tracks);
}

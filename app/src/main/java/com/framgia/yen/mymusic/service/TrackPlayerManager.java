package com.framgia.yen.mymusic.service;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.utils.LoopType;
import com.framgia.yen.mymusic.utils.ShuffleMode;

import java.util.List;

public interface TrackPlayerManager {
    int getCurrentState();

    void playNextTrack();

    void playPreviousTrack();

    void changeTrackState();

    void release();

    void seekTo(int position);

    Track getCurrentTrack();

    void addToNextUp(Track track);

    void setTrackInfoListener(TrackPlayerController.TrackInfoListener listener);

    int getCurrentTrackPosition();

    List<Track> getListTracks();

    void playTrackAtPosition(int position, Track... tracks);

    @LoopType
    int getLoopType();

    @ShuffleMode
    int getShuffleMode();

    void changeLoopType();

    void changeShuffleState();

    int getCurrentPosition();

    int getDuration();
}

package com.framgia.yen.mymusic;

import android.app.Application;

import com.framgia.yen.mymusic.data.source.local.TrackLocalDataSource;

public class MusicApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TrackLocalDataSource.init(this);
    }
}

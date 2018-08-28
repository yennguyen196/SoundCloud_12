package com.framgia.yen.mymusic.screen.main.playmusic;

public class PlayMusicPresenter implements PlayMusicContract.Presenter {
    private PlayMusicContract.View mView;

    public PlayMusicPresenter(PlayMusicContract.View view){
        mView = view;
    }

    @Override
    public void start() {
        mView.setPresenter(this);
    }
}

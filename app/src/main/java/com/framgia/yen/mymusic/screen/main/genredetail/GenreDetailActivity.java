package com.framgia.yen.mymusic.screen.main.genredetail;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.repositories.TrackRepository;
import com.framgia.yen.mymusic.screen.TrackListener;
import com.framgia.yen.mymusic.screen.main.playmusic.PlayTrackActivity;
import com.framgia.yen.mymusic.service.TrackPlayerController;
import com.framgia.yen.mymusic.service.TrackService;

import java.util.List;

public class GenreDetailActivity extends
        AppCompatActivity implements GenreDetailContract.View, TrackListener {
    private static final String EXTRA_GENRE_TYPE = "EXTRA_GENRE_TYPE";
    private static final int LIMIT = 10;
    private static final int OFFSET = 10;
    private TrackAdapter mGenreDetailAdapter;
    private TrackListener mTrackListener;
    private List<Track> mTracks;
    private TrackService mService;
    private Boolean mBound;
    private GenreDetailPresenter mGenreDetailPresenter;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private TextView mTextViewGenreName;
    private Context mContext;

    public static Intent newInstance(Context context, String genre) {
        Intent intent = new Intent(context, GenreDetailActivity.class);
        intent.putExtra(EXTRA_GENRE_TYPE, genre);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_detail);
        mRecyclerView = findViewById(R.id.recycler_genres);
        mToolbar = findViewById(R.id.tool_bar);
        mTextViewGenreName = findViewById(R.id.text_genre_name);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String genre = getIntent().getStringExtra(EXTRA_GENRE_TYPE);
        mTextViewGenreName.setText(genre);
        mGenreDetailAdapter = new TrackAdapter(this, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mGenreDetailAdapter);
        mGenreDetailPresenter = new GenreDetailPresenter(this, TrackRepository.getInstance());

        mRecyclerView.setAdapter(mGenreDetailAdapter);
        mGenreDetailPresenter.loadTrack(genre, LIMIT, OFFSET);

    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, TrackService.class),
                mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

    @Override
    protected void onDestroy() {
        if (mService != null) mService.setTrackInfoListener(null);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showTracks(List<Track> tracks) {
        mGenreDetailAdapter.updateListTrack(tracks);
    }

    @Override
    public void updateTrackInfor(Track track) {
    }

    @Override
    public void updateStateInfor(int state) {

    }

    @Override
    public void noTrackAvailable() {
    }

    @Override
    public void showLoadingTrackError() {
    }

    @Override
    public void download(Track track) {

    }

    @Override
    public void addToFavorite(Track track) {
        mGenreDetailPresenter.addFavorite(track);
    }

    @Override
    public void onPlayTrack(int position, List<Track> tracks) {
        if (tracks == null) return;
        handlePlayTracks(position, tracks.toArray(new Track[tracks.size()]));
    }

    private void handlePlayTracks(int position, Track... tracks) {
        if (mService == null) return;
        mService.playTrackAtPosition(position, tracks);
        startService(new Intent(this, TrackService.class));
        Intent intent = new Intent(this, PlayTrackActivity.class);
        startActivity(intent);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            mService = ((TrackService.LocalBinder) service).getService();
            mService.setTrackInfoListener(new TrackPlayerController.TrackInfoListener() {
                @Override
                public void onTrackChanged(Track track) {
                    updateTrackInfor(track);
                }

                @Override
                public void onStateChanged(int state) {
                    updateStateInfor(state);
                }

                @Override
                public void onLoopChanged(int loopType) {
                }

                @Override
                public void onShuffleChanged(int shuffleMode) {
                }
            });
            updateStateInfor(mService.getMediaState());
            updateTrackInfor(mService.getCurrentTrack());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            mService.setTrackInfoListener(null);
        }
    };
}

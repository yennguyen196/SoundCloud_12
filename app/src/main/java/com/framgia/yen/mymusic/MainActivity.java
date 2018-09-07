package com.framgia.yen.mymusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.screen.TrackListener;
import com.framgia.yen.mymusic.screen.main.MainPagerAdapter;
import com.framgia.yen.mymusic.screen.main.genredetail.GenreDetailContract;
import com.framgia.yen.mymusic.screen.main.playmusic.PlayTrackActivity;
import com.framgia.yen.mymusic.service.TrackPlayerController;
import com.framgia.yen.mymusic.service.TrackService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        View.OnClickListener, SearchView.OnQueryTextListener, TrackListener, GenreDetailContract.View {
    private static int OFF_SCREEN_LIMIT = 3;
    private ConstraintLayout mConstraintLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mImageViewTrack;
    private TextView mTextViewTitle;
    private TextView mTextViewArtist;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonChangeState;
    private ImageButton mButtonNext;
    private TextView mToolBarTitle;
    private MainPagerAdapter mMainPagerAdapter;
    private TrackService mService;
    private boolean mBound;
    private TrackPlayerController.TrackInfoListener mTrackInfoListener;
    private Track mCurrentTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setupUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TrackService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public ServiceConnection mConnection = new ServiceConnection() {
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int tabIconColor = ContextCompat.getColor(this, R.color.color_accent);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int tabIconColor = ContextCompat.getColor(this, R.color.color_white);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    public void init() {
        mToolbar = findViewById(R.id.tool_bar);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager_genres);
        mImageViewTrack = findViewById(R.id.image_track);
        mTextViewTitle = findViewById(R.id.text_title);
        mTextViewArtist = findViewById(R.id.text_artist);
        mButtonPrevious = findViewById(R.id.button_previous);
        mButtonChangeState = findViewById(R.id.button_play_pause);
        mButtonNext = findViewById(R.id.button_option);

        mButtonNext.setOnClickListener(this);
        mButtonChangeState.setOnClickListener(this);
        mButtonPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_option:
                //todo
            case R.id.button_play_pause:
                //todo
            case R.id.button_previous:
                //todo
        }
    }

    public void setupUI() {
        setSupportActionBar(mToolbar);
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mMainPagerAdapter);
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(OFF_SCREEN_LIMIT);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void gotoPlayTrackActivity() {
        Intent intent = new Intent(this, PlayTrackActivity.class);
        startActivity(intent);
    }

    @Override
    public void download(Track track) {

    }

    @Override
    public void addToFavorite(Track track) {

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
        startActivity(new Intent(this, PlayTrackActivity.class));
    }

    @Override
    public void showTracks(List<Track> tracks) {
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
}

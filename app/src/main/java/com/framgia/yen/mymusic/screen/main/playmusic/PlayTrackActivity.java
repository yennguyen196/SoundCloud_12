package com.framgia.yen.mymusic.screen.main.playmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.repositories.TrackRepository;
import com.framgia.yen.mymusic.data.source.remote.DownloadTrackManager;
import com.framgia.yen.mymusic.service.TrackPlayerController;
import com.framgia.yen.mymusic.service.TrackService;
import com.framgia.yen.mymusic.utils.LoopType;
import com.framgia.yen.mymusic.utils.ShuffleMode;
import com.framgia.yen.mymusic.utils.State;
import com.framgia.yen.mymusic.utils.StringUtil;

public class PlayTrackActivity extends AppCompatActivity implements PlayMusicContract.View,
        TrackPlayerController.TrackInfoListener, View.OnClickListener {
    private boolean mBound = false;
    private int mUserSelectPosition = 0;
    private static final int DELAY_MILLIS = 500;
    private ImageView mImageViewTrack;
    private TextView mTextViewTitle;
    private TextView mTextViewArtist;
    private ImageView mImageViewDownload;
    private ImageView mImageViewFavorite;
    private TextView mTextViewCurrentTime;
    private TextView mTextViewEndTime;
    private SeekBar mSeekBar;
    private ImageView mImageViewShuffle;
    private ImageView mImageViewLoop;
    private ImageButton mImageButtonPrevious;
    private ImageButton mImageButtonStateChange;
    private ImageButton mImageButtonNext;
    private ImageView mImageBack;
    private TrackRepository mTrackRepository;
    private Track mCurrentTrack;
    private TrackService mService;
    private PlayMusicContract.Presenter mPresenter;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TrackService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_track);
        mPresenter = new PlayMusicPresenter(this);
        init();
        setupEven();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateTrackInfo() {
        if (mCurrentTrack == null || mService == null) return;
        mTextViewTitle.setText(mCurrentTrack.getTitle());
        mTextViewArtist.setText(mCurrentTrack.getArtist());
        mTextViewEndTime.setText(StringUtil.convertMilisecondToTimer(mCurrentTrack.getDuration()));
        if (mCurrentTrack.getArtWorkUrl() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mImageViewTrack.setImageDrawable(getDrawable(R.drawable.itunes));
            }
        } else {
            Glide.with(getApplicationContext()).load(mCurrentTrack.getArtWorkUrl()).into(mImageViewTrack);
        }
    }

    public void updateStateInfo(@State int state) {
        switch (state) {
            case State.PREPARE:
                stopUpdate();
            case State.PLAYING:
                mImageButtonStateChange.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                updateSeekBar();
                break;
            case State.PAUSE:
                mImageButtonStateChange.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
                break;
            default:
                break;
        }
    }

    private void stopUpdate() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public void setPresenter(PlayMusicContract.Presenter presenter) {

    }

    @Override
    public void onTrackChanged(Track track) {
        mCurrentTrack = track;
        updateTrackInfo();
    }

    @Override
    public void onStateChanged(int state) {
        updateStateInfo(state);
    }

    @Override
    public void onLoopChanged(int loopType) {
        updateLoopState(loopType);
    }

    private void updateLoopState(@LoopType int loopType) {
        switch (loopType) {
            case LoopType.NO_LOOP:
                mImageViewLoop.setImageResource(R.drawable.ic_repeat);
                break;
            case LoopType.LOOP_ONE:
                mImageViewLoop.setImageResource(R.drawable.ic_repeat_one);
                break;
            case LoopType.LOOP_LIST:
                mImageViewLoop.setImageResource(R.drawable.ic_repeat_black_24dp);
                break;
        }
    }

    @Override
    public void onShuffleChanged(int shuffleMode) {
        updateShuffleState(shuffleMode);
    }

    private void updateShuffleState(@ShuffleMode int shuffleMode) {
        switch (shuffleMode) {
            case ShuffleMode.OFF:
                mImageViewShuffle.setImageResource(R.drawable.ic_shuffle);
                break;
            case ShuffleMode.ON:
                mImageViewShuffle.setImageResource(R.drawable.ic_shuffle_black_24dp);
        }
    }

    private void init() {
        mImageViewTrack = findViewById(R.id.image_track);
        mTextViewTitle = findViewById(R.id.text_tittle);
        mTextViewArtist = findViewById(R.id.text_artist);
        mImageViewDownload = findViewById(R.id.image_download);
        mImageViewFavorite = findViewById(R.id.image_favorite);
        mTextViewCurrentTime = findViewById(R.id.text_track_start);
        mTextViewEndTime = findViewById(R.id.text_time);
        mSeekBar = findViewById(R.id.seekBar);
        mImageViewShuffle = findViewById(R.id.image_shuffle);
        mImageViewLoop = findViewById(R.id.image_loop);
        mImageButtonPrevious = findViewById(R.id.button_previous);
        mImageButtonStateChange = findViewById(R.id.button_state_change);
        mImageButtonNext = findViewById(R.id.button_next);
        mImageBack = findViewById(R.id.image_back);
    }

    private void setupEven() {
        mImageButtonPrevious.setOnClickListener(this);
        mImageButtonStateChange.setOnClickListener(this);
        mImageButtonNext.setOnClickListener(this);
        mImageViewLoop.setOnClickListener(this);
        mImageViewShuffle.setOnClickListener(this);
        mImageViewDownload.setOnClickListener(this);
        mImageViewFavorite.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            mService = ((TrackService.LocalBinder) service).getService();
            mService.setTrackInfoListener(PlayTrackActivity.this);
            mCurrentTrack = mService.getCurrentTrack();

            updateTrackInfo();
            updateStateInfo(mService.getMediaState());
            updateLoopState(mService.getLoopType());
            updateShuffleState(mService.getShuffleMode());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            mService.setTrackInfoListener(null);
        }
    };

    @Override
    public void onClick(View v) {
        if (mService == null) return;
        switch (v.getId()) {
            case R.id.button_previous:
                mService.playPrevious();
                break;
            case R.id.button_state_change:
                mService.changeTrackState();
                break;
            case R.id.button_next:
                mService.playNext();
                break;
            case R.id.image_loop:
                mService.changeLoopType();
                break;
            case R.id.image_shuffle:
                mService.changeShuffleState();
                break;
            case R.id.image_download:
                handleDownload();
                break;
            case R.id.image_favorite:
                handleAddToFavorite();
                break;
            case R.id.image_back:
                onBackPressed();
                break;
        }
    }

    private void handleAddToFavorite() {
        if (mCurrentTrack == null) return;
        mTrackRepository = TrackRepository.getInstance();
        mTrackRepository.addTrackToFavorite(mCurrentTrack);
    }

    private void handleDownload() {
        new DownloadTrackManager(this, new DownloadTrackManager.TrackDownloadListener() {
            @Override
            public void onDownloadError(String error) {
                Toast.makeText(PlayTrackActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloading() {
                Toast.makeText(PlayTrackActivity.this, getString(R.string.msg_downloading), Toast.LENGTH_SHORT).show();
            }
        }).downloadTrack(mCurrentTrack);
    }

    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mUserSelectPosition = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mBound) {
                mService.actionSeekTo(mUserSelectPosition);
            }
        }
    };

    public void updateSeekBar() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mSeekBar.setProgress((int) ((double) mService.getCurrentPosition() / mService.getDuration() * 100));
                mTextViewCurrentTime.setText(StringUtil.convertMilisecondToTimer(mService.getCurrentPosition()));
                mHandler.postDelayed(this, DELAY_MILLIS);
            }
        };
        mHandler.post(mRunnable);
    }
}

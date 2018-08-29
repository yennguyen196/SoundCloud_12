package com.framgia.yen.mymusic.screen.main.genredetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.repositories.TrackRepository;

import java.util.List;

public class GenreDetailActivity extends AppCompatActivity implements GenreDetailContract.View {
    private static final String EXTRA_GENRE_TYPE = "EXTRA_GENRE_TYPE";
    private static final int LIMIT = 10;
    private static final int OFFSET = 10;
    private TrackAdapter mGenDetailAdapter;
    private List<Track> mTracks;
    private GenreDetailPresenter mGenreDetailPresenter;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String genre = getIntent().getStringExtra(EXTRA_GENRE_TYPE);
        mGenDetailAdapter = new TrackAdapter(this, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mGenDetailAdapter);
        mGenreDetailPresenter = new GenreDetailPresenter(this, TrackRepository.getInstance());
        mGenreDetailPresenter.loadTrack(genre, LIMIT, OFFSET);
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
        mGenDetailAdapter.updateListTrack(tracks);
    }

    @Override
    public void noTrackAvailable() {
    }

    @Override
    public void showLoadingTrackError() {
    }
}

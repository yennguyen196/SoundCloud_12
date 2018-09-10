package com.framgia.yen.mymusic.screen.main.genredetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;

import java.util.List;

public class GenreDetailFragment extends Fragment implements GenreDetailContract.View {
    private static final String BUNDLE_GENRE_TYPE = "BUNDLE_GENRE_TYPE";
    private GenreDetailContract.Presenter mPresenter;
    private TrackAdapter mTrackAdapter;

    public static GenreDetailFragment getInstance(String genre) {
        GenreDetailFragment mGenreDetailFragment = new GenreDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_GENRE_TYPE, genre);
        mGenreDetailFragment.setArguments(bundle);
        return mGenreDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_genre_detail, null, false);
        return view;
    }

    @Override
    public void showTracks(List<Track> tracks) {
        mTrackAdapter.updateListTrack(tracks);
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

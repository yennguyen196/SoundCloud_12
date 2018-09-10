package com.framgia.yen.mymusic.screen.main.favorite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.screen.TrackListener;

import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteContract.View {
    private FavoriteContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewFavorite;
    private FavoriteAdapter mFavoriteAdapter;
    private TrackListener mTrackListener;
    public FavoriteFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupComponent(view);
        mPresenter = new FavoritePresenter((FavoriteContract.View) this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TrackListener) {
            mTrackListener = (TrackListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTrackListener = null;
    }

    @Override
    public void showFavoriteTracks(List<Track> tracks) {
        mFavoriteAdapter.setTracks(tracks);
        mFavoriteAdapter.notifyDataSetChanged();
    }

    private void setupComponent(View view) {
        mRecyclerViewFavorite = view.findViewById(R.id.recycler_favorite);
        mFavoriteAdapter = new FavoriteAdapter(getContext(), mTrackListener);
        mRecyclerViewFavorite.setAdapter(mFavoriteAdapter);
        mRecyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}

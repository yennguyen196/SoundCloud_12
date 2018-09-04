package com.framgia.yen.mymusic.screen.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Genre;
import com.framgia.yen.mymusic.data.repositories.GenresRepository;
import com.framgia.yen.mymusic.data.source.local.GenresLocalDataSource;
import com.framgia.yen.mymusic.screen.main.genredetail.GenreDetailActivity;
import com.framgia.yen.mymusic.screen.main.genredetail.GenreDetailFragment;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View, GenreAdapter.OnGenreClickListener {
    private Context mContext;
    private HomeContract.Presenter mPresent;
    private GenreAdapter mGenreAdapter;
    private static HomeFragment sInstance;
    private RecyclerView mRecyclerView;
    private GenreDetailFragment mDetailFragment;
    private Toolbar mToolbar;
    private ImageButton mImageButtonSearch;

    public HomeFragment() {
    }

    /**
     * singleton pattern
     *
     * @return
     */
    public static HomeFragment getInstance() {
        if (sInstance == null) {
            sInstance = new HomeFragment();
        }
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, null, false);
        mRecyclerView = view.findViewById(R.id.recycler_genres);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
        mGenreAdapter = new GenreAdapter(getContext(), null);
        mRecyclerView.setAdapter(mGenreAdapter);
        mGenreAdapter.setOnGenreClickListener(this);
        mPresent = new HomePresenter(this, GenresRepository.getInstance(new GenresLocalDataSource()));
        mPresent.loadGenres();
        return view;
    }

    @Override
    public void showGenre(List<Genre> genres) {
        mGenreAdapter.setGenre(genres);
    }

    @Override
    public void gotoDetailFragment(String genre) {
        if (mDetailFragment == null) {
            mDetailFragment = GenreDetailFragment.getInstance(genre);
        }
        getContext().startActivity(GenreDetailActivity.newInstance(getContext(), genre));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        mImageButtonSearch = (ImageButton) menu.findItem(R.id.action_search);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        mImageButtonSearch = (ImageButton) menu.findItem(R.id.action_search);
        super.onPrepareOptionsMenu(menu);
    }

}

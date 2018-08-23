package com.framgia.yen.mymusic.screen.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Genre;
import com.framgia.yen.mymusic.data.repositories.GenresRepository;
import com.framgia.yen.mymusic.data.source.local.GenresLocalDataSource;
import com.framgia.yen.mymusic.screen.main.genredetail.GenreDetailFragment;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View, GenreAdapter.OnGenreClickListener {
    private Context mContext;
    private HomeContract.Presenter mPresent;
    private GenreAdapter mGenreAdapter;
    private static HomeFragment mInstance;
    private RecyclerView mRecyclerView;
    private GenreDetailFragment detailFragment;

    public HomeFragment(){}

    /**
     * singleton pattern
     * @return
     */
    public static HomeFragment getInstance(){
        if(mInstance == null){
            mInstance = new HomeFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, null, false);
        mRecyclerView = view.findViewById(R.id.recycler_genres);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
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
        if (detailFragment == null) {
            detailFragment = GenreDetailFragment.getInstance(genre);
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.recycler_genres, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}

package com.framgia.yen.mymusic.screen.main.download;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.repositories.TrackRepository;
import com.framgia.yen.mymusic.screen.TrackListener;

import java.util.List;

public class DownloadFragment extends Fragment implements DownloadContract.View, TrackListener {
    private static final int PERMISSION_REQUEST_STORAGE = 1;
    private DownloadContract.Presenter mPresenter;
    private TrackListener mTrackListener;
    private DownloadAdapter mDownloadAdapter;
    private RecyclerView mRecyclerView;

    public DownloadFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_download, null, false);
        mRecyclerView = view.findViewById(R.id.recycler_download);
        mDownloadAdapter = new DownloadAdapter(getContext(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mDownloadAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new DownloadPresenter(this, TrackRepository.getInstance());
        requestPermissionStorage();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.getOfflineTracks();
                    break;
                }
        }
    }

    private void requestPermissionStorage() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);
        } else {
            mPresenter.getOfflineTracks();
        }
    }

    @Override
    public void showTracks(List<Track> tracks) {
        mDownloadAdapter.setTracks(tracks);
    }

    @Override
    public void showNoTrack() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void download(Track track) {

    }

    @Override
    public void addToFavorite(Track track) {

    }

    @Override
    public void onPlayTrack(int position, List<Track> tracks) {
        mTrackListener.onPlayTrack(position, tracks);
    }
}

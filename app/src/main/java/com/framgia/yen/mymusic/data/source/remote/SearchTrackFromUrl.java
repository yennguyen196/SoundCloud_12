package com.framgia.yen.mymusic.data.source.remote;

import android.os.AsyncTask;

import com.framgia.yen.mymusic.data.model.Track;

import java.util.List;

public class SearchTrackFromUrl extends AsyncTask<String, Void, List<Track>> {
    @Override
    protected List<Track> doInBackground(String... strings) {
        /**
         * TODO
         */
        return null;
    }

    @Override
    protected void onPostExecute(List<Track> tracks) {
        super.onPostExecute(tracks);
        /**
         * TODO
         */
    }
}

package com.framgia.yen.mymusic.data.source.remote;

import android.os.AsyncTask;

import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.data.model.Track.TrackEntity;
import com.framgia.yen.mymusic.data.source.TrackDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BaseFetchTrackFromUrl extends AsyncTask<String, Void, List<Track>> {
    private static final String GET = "GET";
    private Exception mException;
    private TrackDataSource.onFetchDataListener<Track> mListener;

    private BaseFetchTrackFromUrl(TrackDataSource.onFetchDataListener<Track> listener) {
        mListener = listener;
    }

    @Override
    protected List<Track> doInBackground(String... strings) {
        String url = strings[0];
        String data;
        try {
            data = getJsonStringFromUrl(url);
            return getTrackFromJSon(data);
        } catch (IOException e) {
            mException = e;
        } catch (JSONException e) {
            mException = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Track> tracks) {
        if (mListener == null) return;
        if (mException == null) {
            mListener.onFetchDataSuccess(tracks);
            return;
        }
        mListener.onFetchDataFail(mException);
        super.onPostExecute(tracks);
    }

    public List<Track> getTrackFromJSon(String data) throws JSONException {
        List<Track> tracks = new ArrayList<>();

        JSONObject object = new JSONObject(data);
        JSONArray jsonArray = object.getJSONArray(TrackEntity.COLLECTION);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject(TrackEntity.TRACK);

            String artworkUrl = jsonObject.optString(TrackEntity.ARTWORK_URL);
            boolean downloadable = jsonObject.getBoolean(TrackEntity.DOWNLOADABLE);
            String downloadUrl = jsonObject.optString(TrackEntity.DOWNLOAD_URL);
            int duration = jsonObject.getInt(TrackEntity.DURATION);
            String genre = jsonObject.optString(TrackEntity.GENRE);
            int id = jsonObject.getInt(TrackEntity.ID);
            int likeCount = jsonObject.getInt(TrackEntity.LIKE_COUNT);
            String title = jsonObject.optString(TrackEntity.TITLE);
            String uri = jsonObject.optString(TrackEntity.URI);
            int download_count = jsonObject.optInt(TrackEntity.DOWNLOAD_COUNT);
            JSONObject metadataObject = jsonObject.optJSONObject(TrackEntity.PUBLISHER_METADATA);
            String artist = metadataObject.optString(TrackEntity.ARTIST);
            Track track = new Track(id, duration, title, artworkUrl, downloadUrl, likeCount, downloadable, uri, genre, artist, download_count);
            tracks.add(track);
        }
        return tracks;
    }

    private String getJsonStringFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        connection.disconnect();
        return stringBuilder.toString();
    }
}

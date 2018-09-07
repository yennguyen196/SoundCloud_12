package com.framgia.yen.mymusic.data.source.remote;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.utils.StringUtil;

public class DownloadTrackManager {
    private static final String FILE_EXTENSION = ".mp3";
    private static final String TRACK_DIRECTORY = "file:///mnt/sdcard/SoundCloud12/";
    private static final String DOWNLOAD_SERVICE = "download";

    private Context mContext;
    private TrackDownloadListener mListener;

    public DownloadTrackManager(Context context, TrackDownloadListener listener){
        mContext = context;
        mListener = listener;
    }

    public void downloadTrack(Track track){
        if(mListener == null) return;
        if(track == null){
            mListener.onDownloadError(String.valueOf(R.string.msg_download_failed));
            return;
        }
        DownloadManager downloadManager =
                (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(StringUtil.formatTrackStreamURL(track.getUri())));
        request.setTitle(track.getTitle());
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.parse(TRACK_DIRECTORY + track.getTitle() + FILE_EXTENSION));
        downloadManager.enqueue(request);
        mListener.onDownloading();
    }
    public interface TrackDownloadListener {
        void onDownloadError (String error);
        void onDownloading ();
    }
}

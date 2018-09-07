package com.framgia.yen.mymusic.screen.main.download;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.screen.TrackListener;
import com.framgia.yen.mymusic.utils.StringUtil;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {
    private Context mContext;
    private TrackListener mListener;
    private List<Track> mTracks;

    public DownloadAdapter(Context context, TrackListener listener){
        mContext = context;
        mListener = listener;
    }
    public void setTracks(List<Track> tracks){
        mTracks = tracks;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_download_track, parent, false);
        return new ViewHolder(v, mTracks, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context mContext;
        private List<Track> mTracks;
        private TrackListener mTrackListener;
        private ImageView mImageViewTrack;
        private TextView mTextViewTitle;
        private TextView mTextViewArtist;
        private TextView mTextViewDuration;
        private ImageButton mImageButtonOption;

        public ViewHolder(View itemView, List<Track> tracks, TrackListener trackListener) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mTracks = tracks;
            this.mTrackListener = trackListener;
            mImageViewTrack = itemView.findViewById(R.id.image_track);
            mTextViewTitle = itemView.findViewById(R.id.text_title);
            mTextViewArtist = itemView.findViewById(R.id.text_artist);
            mTextViewDuration = itemView.findViewById(R.id.text_duration);
            mImageButtonOption = itemView.findViewById(R.id.button_option);

            mImageButtonOption.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        public void bindData(int position){
            Track track = mTracks.get(position);
            mTextViewTitle.setText(track.getTitle());
            mTextViewArtist.setText(track.getArtist());
            mTextViewDuration.setText(StringUtil.convertMilisecondToTimer(track.getDuration()));
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.button_option:
                    handleOption();
                    break;
                default:
                    handlePlayTrack();
                    break;
            }
        }

        public void playTrack() {

        }

        private void handleOption() {
            PopupMenu popupMenu = new PopupMenu(mContext, mImageButtonOption);
            popupMenu.inflate(R.menu.menu_item_download_track);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.action_play_offline :
                            handlePlayTrack();
                            break;
                        case R.id.action_delete :
                            break;
                        default:
                            handlePlayTrack();
                    }
                    return false;
                }
            });
        }

        private void handlePlayTrack() {
            if (mTrackListener == null) return;
            mTrackListener.onPlayTrack(getAdapterPosition(), mTracks);
        }
    }
}

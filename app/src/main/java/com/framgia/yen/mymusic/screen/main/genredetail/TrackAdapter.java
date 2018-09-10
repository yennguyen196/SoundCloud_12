package com.framgia.yen.mymusic.screen.main.genredetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Track;
import com.framgia.yen.mymusic.screen.TrackListener;
import com.framgia.yen.mymusic.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private TrackListener mTrackListener;
    private List<Track> mTracks;
    private Context mContext;

    public TrackAdapter(Context context, TrackListener trackListener) {
        mContext = context;
        mTrackListener = trackListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_track, null, false);
        return new ViewHolder(view, mTracks, mTrackListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    public void updateListTrack(List<Track> tracks) {
        if (tracks == null) return;
        if (mTracks == null) {
            mTracks = new ArrayList<>();
        }
        int position = mTracks.size();
        mTracks.addAll(tracks);
        notifyItemRangeInserted(position, tracks.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageViewTrack;
        private TextView mTextViewTitle;
        private TextView mTextViewArtist;
        private TextView mTextViewDuration;
        private ImageButton mImageButtonOption;
        private Track mTrack;
        private TrackListener mTrackListener;
        private List<Track> mTracks;
        private Context mContext;

        private ViewHolder(View itemView, List<Track> tracks, TrackListener trackListener) {
            super(itemView);
            this.mTrackListener = trackListener;
            this.mTracks = tracks;
            this.mContext = itemView.getContext();
            mImageViewTrack = itemView.findViewById(R.id.image_track);
            mTextViewArtist = itemView.findViewById(R.id.text_artist);
            mTextViewTitle = itemView.findViewById(R.id.text_title);
            mTextViewDuration = itemView.findViewById(R.id.text_duration);
            mImageButtonOption = itemView.findViewById(R.id.button_option);

            itemView.setOnClickListener(this);
            mImageButtonOption.setOnClickListener(this);
            mImageViewTrack.setOnClickListener(this);
        }

        private void bindData(int position) {
            if (mTracks == null) return;
            mTrack = mTracks.get(position);
            Glide.with(mImageViewTrack).load(mTrack.getArtWorkUrl()).into(mImageViewTrack);
            mTextViewTitle.setText(mTrack.getTitle());
            mTextViewArtist.setText(mTrack.getArtist());
            mTextViewDuration.setText(StringUtil.convertMilisecondToTimer(mTrack.getDuration()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_option:
                    handleOption();
                    break;
                case R.id.image_track:
                    handlePlayTrack();
                    break;
                case R.id.item_track:
                    handlePlayTrack();
                    break;
                default:
                    handlePlayTrack();
                    break;
            }
        }

        private void handlePlayTrack() {
            mTrackListener.onPlayTrack(getAdapterPosition(), mTracks);
        }

        private void handleOption() {
            PopupMenu popupMenu = new PopupMenu(mContext, mImageButtonOption);
            popupMenu.getMenuInflater().inflate(R.menu.menu_item_track, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_download:
                            mTrackListener.download(mTrack);
                            break;
                        case R.id.action_add_to_favorite:
                            mTrackListener.addToFavorite(mTrack);
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    }
}

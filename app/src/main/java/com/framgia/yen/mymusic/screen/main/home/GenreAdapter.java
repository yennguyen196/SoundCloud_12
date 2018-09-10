package com.framgia.yen.mymusic.screen.main.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.data.model.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private List<Genre> genres;
    private Context mContext;
    private OnGenreClickListener mListener;
    private Genre mGenre;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;

    public GenreAdapter(Context context, List<Genre> genres) {
        mContext = context;
        this.genres = genres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.items_genre, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Genre genre = genres.get(position);
        holder.bindView(genre, mListener);
    }

    @Override
    public int getItemCount() {
        return genres != null ? genres.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageViewGenre;
        private TextView mTextViewGenre;

        private ViewHolder(View view) {
            super(view);
            mImageViewGenre = view.findViewById(R.id.image_genre);
            mTextViewGenre = view.findViewById(R.id.text_genre);
            mImageViewGenre.setOnClickListener(this);
        }

        private void bindView(final Genre genre, final OnGenreClickListener listener) {
            mTextViewGenre.setText(genre.getName().replace(mContext.getString(com.framgia.yen.mymusic.R.string.hyphen)," "));
            Glide.with(mImageViewGenre)
                    .load(genre.getImageResource())
                    .apply(new RequestOptions().override(WIDTH, HEIGHT)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background))
                    .into(mImageViewGenre);
        }

        @Override
        public void onClick(View v) {
            mListener.gotoDetailFragment(mGenre.getName().trim());
        }
    }

    public void setGenre(List<Genre> genres) {
        this.genres = genres;
        notifyDataSetChanged();
    }

    public void setOnGenreClickListener(OnGenreClickListener listener) {
        mListener = listener;
    }

    interface OnGenreClickListener {
        void gotoDetailFragment(String genre);
    }
}

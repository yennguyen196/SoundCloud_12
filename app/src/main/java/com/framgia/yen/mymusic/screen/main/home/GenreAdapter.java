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

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewGenre;
        private TextView mTextViewGenre;

        private ViewHolder(View view) {
            super(view);
            mImageViewGenre = view.findViewById(R.id.imageView_genre);
            mTextViewGenre = view.findViewById(R.id.text_genre);
        }

        private void bindView(final Genre genre, final OnGenreClickListener listener) {
            mTextViewGenre.setText(genre.getName());
            Glide.with(mImageViewGenre)
                    .load(genre.getImageResource())
                    .apply(new RequestOptions().override(100, 100)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background))
                    .into(mImageViewGenre);
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

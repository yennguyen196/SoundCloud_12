package com.framgia.yen.mymusic.screen.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.yen.mymusic.R;
import com.framgia.yen.mymusic.screen.main.download.DownloadFragment;
import com.framgia.yen.mymusic.screen.main.favorite.FavoriteFragment;
import com.framgia.yen.mymusic.screen.main.home.HomeFragment;
import com.framgia.yen.mymusic.utils.TabEntity;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final int TAB_COUNT = 3;
    private DownloadFragment mDownloadFragment;
    private FavoriteFragment mFavoriteFragment;
    private Context mContext;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TabEntity.TAB_HOME:
                return HomeFragment.getInstance();
            case TabEntity.TAB_DOWNLOAD:
                mDownloadFragment = new DownloadFragment();
                return mDownloadFragment;
            case TabEntity.TAB_FAVORITE:
                mFavoriteFragment = new FavoriteFragment();
                return mFavoriteFragment;
            default:
                return HomeFragment.getInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = mContext.getString(R.string.title_home);
                break;
            case 1:
                title = mContext.getString(R.string.string_download);
                break;
            case 2:
                title = mContext.getString(R.string.title_favorite);
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}

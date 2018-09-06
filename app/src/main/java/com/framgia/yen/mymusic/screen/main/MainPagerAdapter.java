package com.framgia.yen.mymusic.screen.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.yen.mymusic.R;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final int TAB_COUNT = 3;
    private Context mContext;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case TabEntity.TAB_HOME:
                title = mContext.getString(R.string.home);
                break;
            case TabEntity.TAB_DOWNLOAD:
                title = mContext.getString(R.string.download);
                break;
            case TabEntity.TAB_FAVORITE:
                title = mContext.getString(R.string.favorite);
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }
}

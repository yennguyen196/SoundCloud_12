package com.framgia.yen.mymusic;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.yen.mymusic.screen.main.MainPagerAdapter;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        View.OnClickListener, SearchView.OnQueryTextListener {
    private static int OFF_SCREEN_LIMIT = 3;
    private ConstraintLayout mConstraintLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mImageViewTrack;
    private TextView mTextViewTitle;
    private TextView mTextViewArtist;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonChangeState;
    private ImageButton mButtonNext;
    private TextView mToolBarTitle;
    private MainPagerAdapter mMainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setupUI();
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int tabIconColor = ContextCompat.getColor(this, R.color.color_accent);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int tabIconColor = ContextCompat.getColor(this, R.color.color_white);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    public void init() {
        mToolbar = findViewById(R.id.tool_bar);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager_genres);
        mImageViewTrack = findViewById(R.id.image_track);
        mTextViewTitle = findViewById(R.id.text_track);
        mTextViewArtist = findViewById(R.id.text_artist);
        mButtonPrevious = findViewById(R.id.button_previous);
        mButtonChangeState = findViewById(R.id.button_play_pause);
        mButtonNext = findViewById(R.id.button_option);

        mButtonNext.setOnClickListener(this);
        mButtonChangeState.setOnClickListener(this);
        mButtonPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_option:
                //todo
            case R.id.button_play_pause:
                //todo
            case R.id.button_previous:
                //todo
        }
    }

    public void setupUI() {
        setSupportActionBar(mToolbar);
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mMainPagerAdapter);
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(OFF_SCREEN_LIMIT);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

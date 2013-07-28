package com.twers.voiceofus;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.twers.voiceofus.feedback.ProvideFeedbackDialog;
import com.twers.voiceofus.util.SystemUiHider;

public class TwersCodesActivity extends SherlockFragmentActivity implements ActionBar.TabListener {
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final boolean TOGGLE_ON_CLICK = true;
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    private SystemUiHider mSystemUiHider;

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.twers_codes_screen);

        final View contentView = findViewById(R.id.fullscreen_content);
        initFeedbackSystem(contentView);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        buildTab(0);
    }

    private void buildTab(int i) {
        ActionBar.Tab tab = getSupportActionBar().newTab();
        tab.setText("Tab " + i);
        tab.setTabListener(this);
        getSupportActionBar().addTab(tab);
    }

    private void initFeedbackSystem(View contentView) {
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View feedbackButton = findViewById(R.id.feedback_button);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AUTO_HIDE) {
                    delayedHide(AUTO_HIDE_DELAY_MILLIS);
                }
                new ProvideFeedbackDialog().show(getSupportFragmentManager(), ProvideFeedbackDialog.class.getName());
            }
        });

        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider.setOnVisibilityChangeListener(new SystemUiVisibilityChangeListener(controlsView));

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(100);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    private class SystemUiVisibilityChangeListener implements SystemUiHider.OnVisibilityChangeListener {

        private final View controlsView;
        int mControlsHeight;
        int mShortAnimTime;

        public SystemUiVisibilityChangeListener(View controlsView) {
            this.controlsView = controlsView;
        }

        @Override
        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
        public void onVisibilityChange(boolean visible) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                if (mControlsHeight == 0) {
                    mControlsHeight = controlsView.getHeight();
                }
                if (mShortAnimTime == 0) {
                    mShortAnimTime = getResources().getInteger(
                            android.R.integer.config_shortAnimTime);
                }
                controlsView.animate().translationY(visible ? 0 : mControlsHeight)
                        .setDuration(mShortAnimTime);
            } else {
                controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
            }

            if (visible && AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
        }
    }
}

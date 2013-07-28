package com.twers.voiceofus.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.twers.voiceofus.R;

public class EventListFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.events_list, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.loading);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }
}

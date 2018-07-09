package com.android.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bakingapp.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class StepDetailFragment extends Fragment {

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        mPlayerView = rootView.findViewById(R.id.player_view);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}

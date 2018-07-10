package com.android.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetailFragment extends Fragment {

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Uri mMediaUri;
    private int mStepId;
    private int mPosition;
    private  Uri mThumbnailUri;
    private boolean mTwoPane;
    private Recipes mRecipes = new Recipes();

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mStepId = this.getArguments().getInt("StepId");
        mPosition = this.getArguments().getInt("Position");
        mTwoPane = this.getResources().getBoolean(R.bool.is_tablet);

        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        TextView stepTitle = rootView.findViewById(R.id.step_instruction_title);
        TextView stepDescription = rootView.findViewById(R.id.step_instruction_value);
        stepDescription.setText(mRecipes.getStepDescription(mStepId, mPosition));

        Button prevButton = rootView.findViewById(R.id.prev_button);

        Button nextButton = rootView.findViewById(R.id.next_button);


        if (mTwoPane) {
            prevButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        }

        mPlayerView = rootView.findViewById(R.id.player_view);
        mMediaUri = mRecipes.getMediaUri(mStepId, mPosition);
        mThumbnailUri = mRecipes.getThumbnailUri(mStepId, mPosition);

        if (mMediaUri == null && mThumbnailUri == null) {
            mPlayerView.setVisibility(View.GONE);
        } else if (mMediaUri != null) {
            initializePlayer(mMediaUri);
        } else {
            initializePlayer(mThumbnailUri);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPlayerView.getVisibility() != View.GONE) {
            releasePlayer();
        }
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}

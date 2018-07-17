package com.android.bakingapp.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.bakingapp.R;
import com.android.bakingapp.data.Recipes;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.NOTIFICATION_SERVICE;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener{

    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private Uri mMediaUri;
    private int mStepId;
    private int mPosition;
    private  Uri mThumbnailUri;
    private Recipes mRecipes = new Recipes();
    private String TAG = StepDetailFragment.class.getSimpleName();
    private NotificationManager mNotificationManager;

    private long startPosition;
    private boolean startAutoPlay;
    private int startWindow;

    @BindView(R.id.step_instruction_title) TextView stepTitle;
    @BindView(R.id.step_instruction_value) TextView stepDescription;
    @BindView(R.id.prev_button) Button prevButton;
    @BindView(R.id.next_button) Button nextButton;
    @BindView(R.id.player_view) SimpleExoPlayerView mPlayerView;
    @BindBool(R.bool.is_tablet) boolean mTwoPane;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mStepId = this.getArguments().getInt("StepId");
        mPosition = this.getArguments().getInt("Position");

        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);

        stepDescription.setText(mRecipes.getStepDescription(mStepId, mPosition));

        if (mTwoPane) {
            prevButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        }

        mMediaUri = mRecipes.getMediaUri(mStepId, mPosition);
        mThumbnailUri = mRecipes.getThumbnailUri(mStepId, mPosition);

        if (savedInstanceState != null) {
            startAutoPlay = savedInstanceState.getBoolean("PlayerAutoPlay");
            startWindow = savedInstanceState.getInt("PlayerWindowIndex");
            startPosition = savedInstanceState.getLong("PlayerPosition");
        } else {
            clearStartPosition();
        }

        if (mMediaUri == null && mThumbnailUri == null) {
            mPlayerView.setVisibility(View.GONE);
        } else if (mMediaUri != null) {
            initializeMediaSession();
            initializePlayer(mMediaUri);
        } else {
            initializeMediaSession();
            initializePlayer(mThumbnailUri);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        updateStartPosition();
        outState.putBoolean("PlayerAutoPlay", startAutoPlay);
        outState.putInt("PlayerWindowIndex", startWindow);
        outState.putLong("PlayerPosition", startPosition);
    }

    private void updateStartPosition() {
        if (mExoPlayer != null) {
            startAutoPlay = mExoPlayer.getPlayWhenReady();
            startWindow = mExoPlayer.getCurrentWindowIndex();
            startPosition = Math.max(0, mExoPlayer.getCurrentPosition());
        }
    }

    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
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
            mMediaSession.setActive(false);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            boolean haveStartPosition = startWindow != C.INDEX_UNSET;
            if (haveStartPosition) {
                mExoPlayer.seekTo(startWindow, startPosition);
            }

            mExoPlayer.prepare(mediaSource, !haveStartPosition, false);
            mExoPlayer.setPlayWhenReady(startAutoPlay);
        }
    }

    private void releasePlayer() {
        mNotificationManager.cancelAll();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(getContext(), TAG);
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
        showNotification(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }

    private void showNotification(PlaybackStateCompat state) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());

        int icon;
        String play_pause;
        if(state.getState() == PlaybackStateCompat.STATE_PLAYING){
            icon = R.drawable.exo_controls_pause;
            play_pause = "Pause";
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = "Play";
        }


        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(getContext(),
                        PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action restartAction = new android.support.v4.app.NotificationCompat
                .Action(R.drawable.exo_controls_previous, "Restart",
                MediaButtonReceiver.buildMediaButtonPendingIntent
                        (getContext(), PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (getContext(), 0, new Intent(getContext(), StepDetailFragment.class), 0);

        builder.setContentTitle("Baking App")
                .setContentText("Check out the recipes!")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(restartAction)
                .addAction(playPauseAction)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mMediaSession.getSessionToken())
                        .setShowActionsInCompactView(0,1));

        mNotificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }
}

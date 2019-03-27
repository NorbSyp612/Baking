package com.example.baking;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class VideoPlayerFragment extends Fragment implements ExoPlayer.EventListener {

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Uri mMediaUri;
    private String mMediaString;
    private Long mMediaPosition;

    public VideoPlayerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video_player, container, false);

        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.fragment_video_player);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.VIDEO_FRAG_OUT_POSITION)) && savedInstanceState.containsKey(getString(R.string.VIDEO_FRAG_OUT_URI))) {
            mMediaString = savedInstanceState.getString(getString(R.string.VIDEO_FRAG_OUT_URI));
            mMediaPosition = savedInstanceState.getLong(getString(R.string.VIDEO_FRAG_OUT_POSITION), 0);
            Log.d("TEST", "Restoring saved instance: " + mMediaString + " " + mMediaPosition);
            startPlayer(Uri.parse(mMediaString));
        } else {
            setViewGone();
        }

        return rootView;
    }

    public void clearStates() {
        mMediaUri = null;
        mExoPlayer = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMediaUri != null) {
            outState.putString(getString(R.string.VIDEO_FRAG_OUT_URI), mMediaUri.toString());
            Log.d("TEST", "VideoFrag putting out: " + mMediaUri);
        }
        if (mExoPlayer != null) {
            outState.putLong(getString(R.string.VIDEO_FRAG_OUT_POSITION), mExoPlayer.getCurrentPosition());
        }
    }

    public void startPlayer(Uri mediaUri) {

        mMediaUri = mediaUri;

        mPlayerView.setVisibility(View.VISIBLE);
        releasePlayer();
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(), "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null
            );
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    public void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    public void setViewGone() {
        releasePlayer();
        mPlayerView.setVisibility(View.GONE);
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

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }
}

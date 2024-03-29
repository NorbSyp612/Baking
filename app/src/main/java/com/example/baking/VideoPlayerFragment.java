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

import timber.log.Timber;

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

        Timber.d("OnCreate Video Frag");

        View rootView = inflater.inflate(R.layout.fragment_video_player, container, false);

        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.fragment_video_player);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.VIDEO_FRAG_OUT_URI))) {
            mMediaString = savedInstanceState.getString(getString(R.string.VIDEO_FRAG_OUT_URI));
            mMediaPosition = savedInstanceState.getLong(getString(R.string.VIDEO_FRAG_OUT_POSITION), 0);
            Timber.d("Restoring saved instance with video %s", mMediaString);
            mMediaUri = (Uri.parse(mMediaString));
        }

        if (mMediaUri != null) {
            startPlayer(mMediaUri);
            if (mMediaPosition != null) {
                mExoPlayer.seekTo(mMediaPosition);
            }
        } else {
            setViewGone();
        }

        return rootView;
    }

    public void setMediaUri(Uri uri) {
        if (uri.toString().isEmpty()) {
            Timber.d("Setting media uri to null");
            mMediaUri = null;
        } else {
            mMediaUri = uri;
        }
    }

    public void setPlayerPoisition(Long position) {
        mMediaPosition = position;
    }

    public Long getPlayerPosition() {
        if (mExoPlayer != null) {
            return mExoPlayer.getCurrentPosition();
        } else {
            return null;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMediaUri != null) {
            outState.putString(getString(R.string.VIDEO_FRAG_OUT_URI), mMediaUri.toString());
            Timber.d("VideoFrag putting out: %s", mMediaUri);
        } else {
            Timber.d("VideoFrag mMediaUri is null, not putting out media string");
        }
        if (mExoPlayer != null) {
            outState.putLong(getString(R.string.VIDEO_FRAG_OUT_POSITION), mExoPlayer.getCurrentPosition());
        }
    }

    public void startPlayer(Uri mediaUri) {

        mPlayerView.setVisibility(View.VISIBLE);
        if (mExoPlayer == null) {
            Timber.d("Starting new player");
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
        } else {
            Timber.d("Changing media in player");
            String userAgent = Util.getUserAgent(getContext(), "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null
            );
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    public void releasePlayer() {
        Timber.d("Releasing Player");
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    public void setViewGone() {
        mPlayerView.setVisibility(View.GONE);
        Timber.d("setting view gone");
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
        Timber.d("onDestroy");
        super.onDestroy();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }
}

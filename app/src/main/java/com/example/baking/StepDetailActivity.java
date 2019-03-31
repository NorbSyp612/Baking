package com.example.baking;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baking.Items.Recipe;
import com.example.baking.Items.RecipeIngredients;
import com.example.baking.Items.RecipeSteps;
import com.example.baking.Utils.JsonParser;
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

import java.util.ArrayList;

import timber.log.Timber;

public class StepDetailActivity extends AppCompatActivity implements ExoPlayer.EventListener {

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView mStepView;
    private Button mBackButton;
    private Button mNextButton;
    private int mSize;
    private int mPosition;
    private String jsonResult;
    private JsonParser jsonParser;
    private Recipe recipe;
    private ArrayList<RecipeSteps> mRecipeSteps;
    private Long mMediaPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.simpleExoPlayerView);
        mStepView = (TextView) findViewById(R.id.step_description);
        mBackButton = (Button) findViewById(R.id.button_back);
        mNextButton = (Button) findViewById(R.id.button_next);

        jsonParser = new JsonParser(getApplicationContext());

        Intent intent = getIntent();

        String step = intent.getStringExtra(getString(R.string.EXTRA_STEP_INSTRUCTION));
        String media = intent.getStringExtra(getString(R.string.EXTRA_MEDIA));
        String position = intent.getStringExtra(getString(R.string.EXTRA_STEP_NUM));
        String size = intent.getStringExtra(getString(R.string.EXTRA_SIZE));
        jsonResult = intent.getStringExtra(getString(R.string.EXTRA_JSON_RESULT));
        String jsonPosition = intent.getStringExtra(getString(R.string.EXTRA_JSON_POSITION));

        int jsonPos = Integer.parseInt(jsonPosition);

        recipe = jsonParser.parseJsonForRecipe(jsonResult, jsonPos);
        mRecipeSteps = recipe.getRecipeSteps();

        Timber.d("Size is %s", size);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.BUNDLE_MPOSITION))) {
            mPosition = savedInstanceState.getInt(getString(R.string.BUNDLE_MPOSITION));
        } else {
            mPosition = Integer.parseInt(position);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.VIDEO_FRAG_OUT_POSITION))) {
            mMediaPosition = savedInstanceState.getLong(getString(R.string.VIDEO_FRAG_OUT_POSITION), 0);
        }

        Timber.d("Current position is: %s", mPosition);
        mSize = Integer.parseInt(size);

        populateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.BUNDLE_MPOSITION), mPosition);
        if (mExoPlayer != null) {
            mMediaPosition = mExoPlayer.getCurrentPosition();
            outState.putLong(getString(R.string.VIDEO_FRAG_OUT_POSITION), mMediaPosition);
        }
    }

    private void populateUI() {

        Timber.d("Populating UI with position: %s", mPosition);

        int orientation = getResources().getConfiguration().orientation;
        mStepView.setVisibility(View.VISIBLE);
        mNextButton.setVisibility(View.VISIBLE);
        mBackButton.setVisibility(View.VISIBLE);

        if (!mRecipeSteps.get(mPosition).getVideoURL().isEmpty()) {
            if (mExoPlayer != null) {
                releasePlayer();
            }
            mPlayerView.setVisibility(View.VISIBLE);
            startPlayer(Uri.parse(mRecipeSteps.get(mPosition).getVideoURL()));

            if (mMediaPosition != null) {
                mExoPlayer.seekTo(mMediaPosition);
            }

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mStepView.setVisibility(View.INVISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
                mBackButton.setVisibility(View.INVISIBLE);
            }
        } else {
            releasePlayer();
            mPlayerView.setVisibility(View.GONE);
        }

        mStepView.setText(mRecipeSteps.get(mPosition).getDescription());
    }


    public void backPress(View view) {

        if (mPosition == 0) {
            Toast.makeText(this, getString(R.string.BACK_FIRST_STEP), Toast.LENGTH_SHORT).show();
        }

        if (mPosition > 0) {
            mPosition--;
            Timber.d("Position is now: %s", mPosition);
            mMediaPosition = null;
            populateUI();
        }


    }

    public void nextPress(View view) {
        if (mPosition == (mRecipeSteps.size()) - 1) {
            Toast.makeText(this, getString(R.string.NEXT_FINAL_STEP), Toast.LENGTH_SHORT).show();
        } else {
            mPosition++;
            Timber.d( "Position is now %s", mPosition);
            mMediaPosition = null;
            populateUI();
        }
    }

    private void startPlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(this, "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null
            );
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
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

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (!playWhenReady) {
                mNextButton.setVisibility(View.VISIBLE);
                mBackButton.setVisibility(View.VISIBLE);
                Timber.d("Player is paused.");
            } else {
                mNextButton.setVisibility(View.INVISIBLE);
                mBackButton.setVisibility(View.INVISIBLE);
            }

            if (playbackState == mExoPlayer.STATE_ENDED) {
                mNextButton.setVisibility(View.VISIBLE);
                mBackButton.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }
}

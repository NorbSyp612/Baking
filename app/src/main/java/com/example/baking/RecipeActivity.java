package com.example.baking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.baking.Items.Recipe;
import com.example.baking.Items.RecipeSteps;
import com.example.baking.Utils.JsonParser;
import com.example.baking.Utils.RecipesAdapter;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import timber.log.Timber;

public class RecipeActivity extends AppCompatActivity implements RecipesAdapter.ListItemClickListener, RecipeListFragment.onListItemClickListener {

    private Recipe mRecipe;
    private RecyclerView mRecylerView;
    private RecipesAdapter mRecipeAdapter;
    private String jsonResult;
    private String jsonPosition;
    private VideoPlayerFragment newVideoPlayerFragment;
    private InstructionsFragment newInstructionsFragment;
    private FragmentManager mFragmentManager;
    private FrameLayout mVideoFrame;
    private String mMediaString;
    private Long mMediaPosition;
    private String mText;
    private ImageView mThumbnail;
    String mThumbnailURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mVideoFrame = (FrameLayout) findViewById(R.id.exoplayer_container);

        Timber.d("On Create RecipeActivity");

        Context context = getApplicationContext();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        JsonParser jsonParser = new JsonParser(context);

        jsonResult = sharedPreferences.getString(getString(R.string.PREF_JSON), getString(R.string.PREF_JSON_DEFAULT));
        int pos = sharedPreferences.getInt(getString(R.string.PREF_KEY_CLICKED), 0);
        jsonPosition = pos + "";

        mRecipe = jsonParser.parseJsonForRecipe(jsonResult, pos);

        int numberOfHolders = mRecipe.getRecipeSteps().size();


        if (savedInstanceState == null) {
            if (getResources().getBoolean(R.bool.Tablet_Check)) {

                mThumbnail = (ImageView) findViewById(R.id.instructions_thumbnail);
                Timber.d("TABLET FROM RECIPE ACTIVITY");
                mRecylerView = (RecyclerView) findViewById(R.id.recipe_RecyclerView3);
                mFragmentManager = getSupportFragmentManager();

                newVideoPlayerFragment = new VideoPlayerFragment();
                newInstructionsFragment = new InstructionsFragment();

                mFragmentManager.beginTransaction()
                        .add(R.id.exoplayer_container, newVideoPlayerFragment)
                        .add(R.id.instructions_container, newInstructionsFragment)
                        .commit();
            } else {
                Timber.d("NOT TABLET FROM RECIPE ACTIVITY");
                mRecylerView = (RecyclerView) findViewById(R.id.recipe_RecyclerView2);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                mRecylerView.setLayoutManager(layoutManager);
                mRecipeAdapter = new RecipesAdapter(numberOfHolders, this, mRecipe);
                mRecylerView.setAdapter(mRecipeAdapter);
                mRecylerView.setHasFixedSize(true);
            }
        } else {
            if (!getResources().getBoolean(R.bool.Tablet_Check)) {
                mRecylerView = (RecyclerView) findViewById(R.id.recipe_RecyclerView2);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                mRecylerView.setLayoutManager(layoutManager);
                mRecipeAdapter = new RecipesAdapter(numberOfHolders, this, mRecipe);
                mRecylerView.setAdapter(mRecipeAdapter);
                mRecylerView.setHasFixedSize(true);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (getResources().getBoolean(R.bool.Tablet_Check)) {
            outState.putString(getString(R.string.VIDEO_FRAG_OUT_URI), mMediaString);
            outState.putString(getString(R.string.EXTRA_THUMB), mThumbnailURL);
            outState.putString(getString(R.string.INSTRUC_FRAG_OUT_TEXT), mText);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, StepDetailActivity.class);

        int index = clickedItemIndex - 1;

        Timber.d("clicked item index is: %s", index);

        ArrayList<RecipeSteps> steps = mRecipe.getRecipeSteps();
        String position = "" + index;
        String size = "" + (mRecipe.getRecipeSteps().size());

        intent.putExtra(getString(R.string.EXTRA_MEDIA), steps.get(index).getVideoURL());
        intent.putExtra(getString(R.string.EXTRA_STEP_INSTRUCTION), steps.get(index).getDescription());
        intent.putExtra(getString(R.string.EXTRA_STEP_NUM), position);
        intent.putExtra(getString(R.string.EXTRA_SIZE), size);
        intent.putExtra(getString(R.string.EXTRA_JSON_RESULT), jsonResult);
        intent.putExtra(getString(R.string.EXTRA_JSON_POSITION), jsonPosition);

        startActivity(intent);
    }

    @Override
    public void onListItemSelected(int position) {

        mThumbnailURL = mRecipe.getRecipeSteps().get(position-1).getThumbnailURL();

        if (!mThumbnailURL.isEmpty()) {
            Picasso.with(getApplicationContext()).load(mThumbnailURL).into(mThumbnail);
        }

        Timber.d("Clicked item index is: %s", position);

        mVideoFrame.setVisibility(View.VISIBLE);

        mMediaString = mRecipe.getRecipeSteps().get(position - 1).getVideoURL();
        mText = mRecipe.getRecipeSteps().get(position - 1).getDescription();

        newVideoPlayerFragment = new VideoPlayerFragment();
        newInstructionsFragment = new InstructionsFragment();


        newVideoPlayerFragment.setMediaUri(Uri.parse(mMediaString));
        newInstructionsFragment.setInstructions(mText);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.exoplayer_container, newVideoPlayerFragment)
                .replace(R.id.instructions_container, newInstructionsFragment)
                .commit();

        if (mMediaString.isEmpty()) {
            mFragmentManager.beginTransaction()
                    .remove(newVideoPlayerFragment)
                    .commit();
            mVideoFrame = (FrameLayout) findViewById(R.id.exoplayer_container);
            mVideoFrame.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("RecipeActivity Destroyed");
    }
}
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

import com.example.baking.Items.Recipe;
import com.example.baking.Items.RecipeSteps;
import com.example.baking.Utils.JsonParser;
import com.example.baking.Utils.RecipesAdapter;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mVideoFrame = (FrameLayout) findViewById(R.id.exoplayer_container);

        Log.d("TEST", "On Create RecipeActivity");

        Intent intent = getIntent();
        Context context = getApplicationContext();

        JsonParser jsonParser = new JsonParser(getApplicationContext());


        String position = intent.getStringExtra("TEST");
        jsonResult = intent.getStringExtra("TEST2");
        int pos = Integer.parseInt(position);
        jsonPosition = "" + pos;


        mRecipe = jsonParser.parseJsonForRecipe(jsonResult, pos);

        int numberOfHolders = mRecipe.getRecipeSteps().size();


        if (savedInstanceState == null) {
            if (getResources().getBoolean(R.bool.Tablet_Check)) {
                Log.d("TEST", "TABLET FROM RECIPE ACTIVITY");
                mRecylerView = (RecyclerView) findViewById(R.id.recipe_RecyclerView3);
                mFragmentManager = getSupportFragmentManager();

                newVideoPlayerFragment = new VideoPlayerFragment();
                newInstructionsFragment = new InstructionsFragment();

                mFragmentManager.beginTransaction()
                        .add(R.id.exoplayer_container, newVideoPlayerFragment)
                        .add(R.id.instructions_container, newInstructionsFragment)
                        .commit();
            } else {
                Log.d("TEST", "NOT TABLET FROM RECIPE ACTIVITY");
                mRecylerView = (RecyclerView) findViewById(R.id.recipe_RecyclerView2);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                mRecylerView.setLayoutManager(layoutManager);
                mRecipeAdapter = new RecipesAdapter(numberOfHolders, this, mRecipe);
                mRecylerView.setAdapter(mRecipeAdapter);
                mRecylerView.setHasFixedSize(true);
            }
        } else {
            if (getResources().getBoolean(R.bool.Tablet_Check)) {
                mRecylerView = (RecyclerView) findViewById(R.id.recipe_RecyclerView3);
                mFragmentManager = getSupportFragmentManager();

                if (savedInstanceState.containsKey(getString(R.string.VIDEO_FRAG_OUT_URI))) {
                    mMediaString = savedInstanceState.getString(getString(R.string.VIDEO_FRAG_OUT_URI));
                }
                if (savedInstanceState.containsKey(getString(R.string.VIDEO_FRAG_OUT_POSITION))) {
                    mMediaPosition = savedInstanceState.getLong(getString(R.string.VIDEO_FRAG_OUT_POSITION), 0);
                }
                if (savedInstanceState.containsKey(getString(R.string.INSTRUC_FRAG_OUT_TEXT))) {
                    mText = savedInstanceState.getString(getString(R.string.INSTRUC_FRAG_OUT_TEXT));
                }

                newVideoPlayerFragment = new VideoPlayerFragment();
                newInstructionsFragment = new InstructionsFragment();

                if (!mMediaString.isEmpty()) {
                    newVideoPlayerFragment.setMediaUri(Uri.parse(mMediaString));
                    newVideoPlayerFragment.setPlayerPoisition(mMediaPosition);
                }

                if (!mText.isEmpty()) {
                    newInstructionsFragment.setInstructions(mText);
                }

                mFragmentManager.beginTransaction()
                        .add(R.id.exoplayer_container, newVideoPlayerFragment)
                        .add(R.id.instructions_container, newInstructionsFragment)
                        .commit();

                if (mMediaString.isEmpty()) {
                    mFragmentManager.beginTransaction()
                            .remove(newVideoPlayerFragment)
                            .commit();
                    mVideoFrame = (FrameLayout) findViewById(R.id.exoplayer_container);
                    mVideoFrame.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.VIDEO_FRAG_OUT_URI), mMediaString);
        mMediaPosition = newVideoPlayerFragment.getPlayerPosition();
        if (mMediaPosition != null) {
            outState.putLong(getString(R.string.VIDEO_FRAG_OUT_POSITION), mMediaPosition);
        }
        outState.putString(getString(R.string.INSTRUC_FRAG_OUT_TEXT), mText);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, StepDetailActivity.class);

        ArrayList<RecipeSteps> steps = mRecipe.getRecipeSteps();
        String position = "" + clickedItemIndex;
        String size = "" + (mRecipe.getRecipeSteps().size() - 1);

        intent.putExtra(getString(R.string.EXTRA_MEDIA), steps.get(clickedItemIndex).getVideoURL());
        intent.putExtra(getString(R.string.EXTRA_STEP_INSTRUCTION), steps.get(clickedItemIndex).getDescription());
        intent.putExtra(getString(R.string.EXTRA_STEP_NUM), position);
        intent.putExtra(getString(R.string.EXTRA_SIZE), size);
        intent.putExtra(getString(R.string.EXTRA_JSON_RESULT), jsonResult);
        intent.putExtra(getString(R.string.EXTRA_JSON_POSITION), jsonPosition);

        startActivity(intent);
    }

    @Override
    public void onListItemSelected(int position) {

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
        Log.d("TEST", "RecipeActivity Destroyed");
    }
}

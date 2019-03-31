package com.example.baking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import timber.log.Timber;

public class InstructionsFragment extends Fragment {

    private TextView mTextView;
    private String mText;

    public InstructionsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);

        mTextView = (TextView) rootView.findViewById(R.id.fragment_ingredients);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.INSTRUC_FRAG_OUT_TEXT))) {
            mText = savedInstanceState.getString(getString(R.string.INSTRUC_FRAG_OUT_TEXT));
            Timber.d("Restoring saved instance state");
        }

        if (mText != null) {
            mTextView.setText(mText);
        } else {
            mTextView.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.INSTRUC_FRAG_OUT_TEXT), mText);
        super.onSaveInstanceState(outState);
    }

    public void setInstructions(String text) {
        mText = text;
    }
}

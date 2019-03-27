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

public class InstructionsFragment extends Fragment {

    private TextView mTextView;
    private String mText;
    private boolean isMedia;

    public InstructionsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);

        mTextView = (TextView) rootView.findViewById(R.id.fragment_ingredients);
        if (savedInstanceState != null) {
            mText = savedInstanceState.getString(getString(R.string.INSTRUC_FRAG_OUT_TEXT));
            mTextView.setText(mText);
        } else {
            mTextView.setVisibility(View.GONE);
        }

        return rootView;
    }

    public boolean setMedia(boolean media) {


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.INSTRUC_FRAG_OUT_TEXT), mText);
        super.onSaveInstanceState(outState);
    }

    public void setInstructions(String text) {
        mText = text;

        if (!text.isEmpty()) {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(text);
        }
    }
}

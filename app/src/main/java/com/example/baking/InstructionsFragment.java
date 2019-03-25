package com.example.baking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class InstructionsFragment extends Fragment {

    private TextView mTextView;

    public InstructionsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);

        mTextView = (TextView) rootView.findViewById(R.id.fragment_ingredients);
        mTextView.setVisibility(View.GONE);

        return rootView;
    }

    public void setInstructions(String text) {

        if (!text.isEmpty()) {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(text);
        }
    }
}

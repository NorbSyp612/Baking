package com.example.baking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class VideoPlayerFragment extends Fragment {

    public VideoPlayerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video_player, container, false);

        final SimpleExoPlayerView exoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.fragment_video_player);

        return rootView;
    }
}

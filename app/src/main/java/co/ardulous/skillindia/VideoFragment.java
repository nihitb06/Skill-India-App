package co.ardulous.skillindia;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        View rootView = layoutInflater.inflate(R.layout.fragment_about, viewGroup, false);
        ImageView imageView = rootView.findViewById(R.id.peoplephoto);
        imageView.setVisibility(View.GONE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.youtube_layout, youTubePlayerSupportFragment).commit();
            youTubePlayerSupportFragment.initialize(YouTubeConfig.getApiKey(), new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    if (!b) {
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                        youTubePlayer.loadVideo(YouTubeConfig.getVideoId());
                        youTubePlayer.play();
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    String error = youTubeInitializationResult.toString();
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                    Log.e("Youtube Fail", error);

                }
            });
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

}

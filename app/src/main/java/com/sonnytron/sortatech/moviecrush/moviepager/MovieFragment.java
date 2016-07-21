package com.sonnytron.sortatech.moviecrush.moviepager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sonnytron.sortatech.moviecrush.Movie;
import com.sonnytron.sortatech.moviecrush.R;
import com.sonnytron.sortatech.moviecrush.networking.MovieManager;

/**
 * Created by sonnyrodriguez on 7/21/16.
 */
public class MovieFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";

    private Movie mMovie;
    private Callbacks mCallbacks;

    private ImageButton mBackdropButton;
    private TextView mTitleTextView;
    private TextView mOverviewTextView;



    public interface Callbacks {
        void onMovieVideoTapped(Movie movie);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer movieId = (Integer) getArguments().getSerializable(ARG_MOVIE_ID);
        mMovie = MovieManager.get(getActivity()).getMovie(movieId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie, container, false);

        return v;
    }
}

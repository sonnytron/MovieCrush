package com.sonnytron.sortatech.moviecrush.moviepager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonnytron.sortatech.moviecrush.Movie;
import com.sonnytron.sortatech.moviecrush.R;
import com.sonnytron.sortatech.moviecrush.networking.MovieManager;

import java.util.List;

/**
 * Created by sonnyrodriguez on 7/21/16.
 */
public class MovieFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";

    private Movie mMovie;

    private ImageButton mBackdropButton;
    private TextView mTitleTextView;
    private TextView mOverviewTextView;

    private ImageView mStarOne;
    private ImageView mStarTwo;
    private ImageView mStarThree;
    private ImageView mStarFour;
    private ImageView mStarFive;

    private List<ImageView> mStarButtons;

    public static MovieFragment newInstance(Integer movieId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_ID, movieId);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        mTitleTextView = (TextView) v.findViewById(R.id.movie_fragment_title);
        mTitleTextView.setText(mMovie.getTitle());
        mOverviewTextView = (TextView) v.findViewById(R.id.movie_fragment_description_long);
        mOverviewTextView.setText(mMovie.getOverview());
        mBackdropButton = (ImageButton) v.findViewById(R.id.fragment_video_button);
        mBackdropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVideoTapped(mMovie);
            }
        });
        mStarOne = (ImageView) v.findViewById(R.id.movie_fragment_star1);
        mStarTwo = (ImageView) v.findViewById(R.id.movie_fragment_star2);
        mStarThree = (ImageView) v.findViewById(R.id.movie_fragment_star3);
        mStarFour = (ImageView) v.findViewById(R.id.movie_fragment_star4);
        mStarFive = (ImageView) v.findViewById(R.id.movie_fragment_star5);
        mStarButtons.add(mStarOne);
        mStarButtons.add(mStarTwo);
        mStarButtons.add(mStarThree);
        mStarButtons.add(mStarFour);
        mStarButtons.add(mStarFive);
        updateVoteButtons();
        return v;
    }

    public void onVideoTapped(Movie movie) {

    }

    public void updateVoteButtons() {
        double voteFive = mMovie.getAverage() / 2;
        int voteCount = (int) Math.round(voteFive);
        if (voteCount > 5) {
            mStarFive.setVisibility(View.VISIBLE);
        }
        if (voteCount > 4) {
            mStarFour.setVisibility(View.VISIBLE);
        }
        if (voteCount > 3) {
            mStarThree.setVisibility(View.VISIBLE);
        }
        if (voteCount > 2) {
            mStarTwo.setVisibility(View.VISIBLE);
        }
        if (voteCount > 1) {
            mStarOne.setVisibility(View.VISIBLE);
        }
    }
}

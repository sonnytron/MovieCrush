package com.sonnytron.sortatech.moviecrush.moviepager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sonnytron.sortatech.moviecrush.Movie;
import com.sonnytron.sortatech.moviecrush.R;
import com.sonnytron.sortatech.moviecrush.networking.MovieManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
    private String mImageUrl;

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
        mOverviewTextView = (TextView) v.findViewById(R.id.fragment_movie_description);
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
        updateVoteButtons();
        updateBackdrop();
        return v;
    }

    public void onVideoTapped(Movie movie) {

    }

    public void updateBackdrop() {
        final MovieManager movieManager = MovieManager.get(getActivity());
        if (movieManager.getImageBaseUrl().length() > 0) {
            mImageUrl = movieManager.getImageBaseUrl();
        } else {
            movieManager.getImageBaseUrl(new MovieManager.ManagerHandler() {
                @Override
                public void moviesReturned(List<Movie> movies) {
                    mImageUrl = movieManager.getImageBaseUrl();
                }
            });
        }

        if (mImageUrl.length() > 0) {
            String imageUrl = mImageUrl + mMovie.getBackdrop();
            Picasso.with(getContext()).load(imageUrl).into(mBackdropButton);
        }
    }

    public void updateVoteButtons() {
        double voteFive = mMovie.getAverage() / 2;
        int voteCount = (int) Math.round(voteFive);
        switch (voteCount) {
            case 5:
                mStarFive.setVisibility(View.VISIBLE);
            case 4:
                mStarFour.setVisibility(View.VISIBLE);
            case 3:
                mStarThree.setVisibility(View.VISIBLE);
            case 2:
                mStarTwo.setVisibility(View.VISIBLE);
            case 1:
                mStarOne.setVisibility(View.VISIBLE);
        }
    }
}

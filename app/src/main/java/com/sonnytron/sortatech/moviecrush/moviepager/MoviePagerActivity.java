package com.sonnytron.sortatech.moviecrush.moviepager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sonnytron.sortatech.moviecrush.Movie;
import com.sonnytron.sortatech.moviecrush.R;
import com.sonnytron.sortatech.moviecrush.networking.MovieManager;

import java.util.List;

/**
 * Created by sonnyrodriguez on 7/13/16.
 */
public class MoviePagerActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE_ID = "com.sonnytron.sortatech.moviecrush.movie_id";

    private ViewPager mViewPager;
    private List<Movie> mMovies;

    public static Intent newIntent(Context packageContext, Integer movieId) {
        Intent intent = new Intent(packageContext, MoviePagerActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pager);

        // TODO: Add movieId to movie object and ensure MovieFragment is built with layout
        Integer movieId = (Integer) getIntent().getSerializableExtra(EXTRA_MOVIE_ID);

        MovieManager movieManager = MovieManager.get(getApplicationContext());
        if (movieManager.getMovies().size() > 0) {
            mMovies = movieManager.getMovies();
            mViewPager = (ViewPager) findViewById(R.id.activity_movie_view_pager);
            FragmentManager fragmentManager = getSupportFragmentManager();
            mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
                @Override
                public Fragment getItem(int position) {
                    Movie movie = mMovies.get(position);
                    return MovieFragment.newInstance(movie.getId());
                }

                @Override
                public int getCount() {
                    return mMovies.size();
                }
            });
        }

        for (int i = 0; i < mMovies.size(); i++) {
            if (mMovies.get(i).getId().equals(movieId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        movieManager.getImageBaseUrl(new MovieManager.ManagerHandler() {
            @Override
            public void moviesReturned(List<Movie> movies) {
                mMovies = movies;
            }
        });


    }
}

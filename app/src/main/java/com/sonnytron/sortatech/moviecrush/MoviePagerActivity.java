package com.sonnytron.sortatech.moviecrush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sonnytron.sortatech.moviecrush.networking.MovieManager;

import java.util.List;

/**
 * Created by sonnyrodriguez on 7/13/16.
 */
public class MoviePagerActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE_ID = "com.sonnytron.sortatech.moviecrush.movie_id";

    private ViewPager mViewPager;
    private List<Movie> mMovies;

    public static Intent newIntent(Context packageContext, String movieId) {
        Intent intent = new Intent(packageContext, MoviePagerActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pager);

        // TODO: Add movieId to movie object and ensure MovieFragment is built with layout

        String movieId = (String) getIntent().getSerializableExtra(EXTRA_MOVIE_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_movie_view_pager);
        mMovies = MovieManager.get(this).getMovies();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Movie movie = mMovies.get(position);
                return null;
            }

            @Override
            public int getCount() {
                return mMovies.size();
            }
        });
    }
}

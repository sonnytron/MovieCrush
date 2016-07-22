package com.sonnytron.sortatech.moviecrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sonnytron.sortatech.moviecrush.MovieList.MovieListFragment;
import com.sonnytron.sortatech.moviecrush.moviepager.MoviePagerActivity;

public class MovieCrushMainActivity extends AppCompatActivity implements MovieListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onMovieSelected(Movie movie) {
        Intent intent = MoviePagerActivity.newIntent(this, movie.getId());
        startActivity(intent);
    }
}

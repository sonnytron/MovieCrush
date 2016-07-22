package com.sonnytron.sortatech.moviecrush;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sonnytron.sortatech.moviecrush.MovieList.MovieListFragment;
import com.sonnytron.sortatech.moviecrush.moviepager.MoviePagerActivity;

/**
 * Created by sonnyrodriguez on 7/14/16.
 */
public class MovieCrushListActivity extends SingleFragmentActivity implements MovieListFragment.Callbacks {
    @Override
    protected Fragment createFragment() {
        return new MovieListFragment();
    }

    @Override
    public void onMovieSelected(Movie movie) {
        Intent intent = MoviePagerActivity.newIntent(this, movie.getId());
        startActivity(intent);
    }
}

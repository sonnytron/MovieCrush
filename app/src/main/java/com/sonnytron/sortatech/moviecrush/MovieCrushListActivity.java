package com.sonnytron.sortatech.moviecrush;

import android.support.v4.app.Fragment;

import com.sonnytron.sortatech.moviecrush.MovieList.MovieListFragment;

/**
 * Created by sonnyrodriguez on 7/14/16.
 */
public class MovieCrushListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MovieListFragment();
    }
}

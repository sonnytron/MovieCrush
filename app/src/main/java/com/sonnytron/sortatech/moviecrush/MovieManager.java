package com.sonnytron.sortatech.moviecrush;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonnyrodriguez on 7/13/16.
 */
public class MovieManager {

    private static MovieManager sMovieManager;
    private Context mContext;

    /* TODO: Add networking manager and import here to retrieve movies from TMDB */

    public static MovieManager get(Context context) {
        if (sMovieManager == null) {
            sMovieManager = new MovieManager(context);
        }
        return sMovieManager;
    }

    private MovieManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        return movies;
    }
}

package com.sonnytron.sortatech.moviecrush;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sonnyrodriguez on 7/13/16.
 */
public class Movie {
    private String mTitle;
    private Integer mId;
    private String mBackdrop;
    private String mOverview;
    private String mPoster;
    private Double mAverage;

    public static Movie fromJson(JSONObject jsonObject) {
        Movie mMovie = new Movie();
        try {
            mMovie.setTitle(jsonObject.getString("title"));
            mMovie.setBackdrop(jsonObject.getString("backdrop_path"));
            mMovie.setPoster(jsonObject.getString("poster_path"));
            mMovie.setOverview(jsonObject.getString("overview"));
            mMovie.setAverage(jsonObject.getDouble("vote_average"));
            mMovie.setId(jsonObject.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return mMovie;
    }

    public static ArrayList<Movie> fromJson(JSONArray jsonArray) {
        JSONObject movieJson;
        ArrayList<Movie> movies = new ArrayList<Movie>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                movieJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Movie movie = Movie.fromJson(movieJson);
            if (movie != null) {
                movies.add(movie);
            }
        }
        return movies;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getOverview() {
        return mOverview;
    }

    public Double getAverage() {
        return mAverage;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setBackdrop(String backdrop) {
        mBackdrop = backdrop;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public void setAverage(Double average) {
        mAverage = average;
    }

    public void setId(Integer id) {
        mId = id;
    }


}

package com.sonnytron.sortatech.moviecrush.networking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sonnytron.sortatech.moviecrush.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sonnyrodriguez on 7/13/16.
 */
public class MovieManager {

    public interface ManagerHandler {
        void moviesReturned(List<Movie> movies);
    }

    private static String baseUrl = "http://api.themoviedb.org/3";
    private static String nowPlaying = "/movie/now_playing";
    private static String configurationUrl = "http://api.themoviedb.org/3/configuration";
    private String mImageBaseUrl = "";

    private static MovieManager sMovieManager;
    private Context mContext;

    private String mApiKey = "";
    private Integer mPage = 1;

    private AsyncHttpClient mClient;
    private RequestParams mParams;

    private ArrayList<Movie> mMovies;

    public static MovieManager get(Context context) {
        if (sMovieManager == null) {
            sMovieManager = new MovieManager(context);
        }
        return sMovieManager;
    }

    private MovieManager(Context context) {
        mContext = context.getApplicationContext();
        mClient = new AsyncHttpClient();
        mParams = new RequestParams();
    }


    public void getNowPlaying(ManagerHandler handler) {
        final ManagerHandler managerHandler = handler;
        if (mMovies != null) {
            managerHandler.moviesReturned(mMovies);
        } else {
            mParams.put("page", mPage.toString());
            mParams.put("api_key", getApiKey());
            mClient.get(baseUrl + nowPlaying, mParams, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray moviesJson = response.getJSONArray("results");
                        mMovies = Movie.fromJson(moviesJson);
                        managerHandler.moviesReturned(mMovies);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                    Log.d("Failed: ", ""+statusCode);
                    Log.d("Error : ", "" + throwable);
                }
            });
        }

    }

    public void getImageBaseUrl(ManagerHandler handler) {
        final ManagerHandler managerHandler = handler;
        mParams.put("api_key", getApiKey());

        mClient.get(configurationUrl, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject images = response.getJSONObject("images");
                    String base = images.getString("base_url");
                    base = base.replaceAll("\\\\", "");
                    JSONArray backdropSizes = images.getJSONArray("backdrop_sizes");
                    ArrayList<String> backdrops = new ArrayList<String>(backdropSizes.length());
                    String backdrop;
                    for (int i = 0; i < backdropSizes.length(); i++) {
                        backdrop = backdropSizes.getString(i);
                        backdrops.add(backdrop);
                    }
                    if (backdrops.size() > 0) {
                        mImageBaseUrl = base + backdrops.get(1);
                    } else if (backdrops.size() != 0) {
                        mImageBaseUrl = base + backdrops.get(0);
                    }
                    if (mImageBaseUrl != null) {
                        getNowPlaying(managerHandler);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });
    }

    public Movie getMovie(Integer movieId) {
        Movie movie = new Movie();
        for (int i = 0; i < mMovies.size(); i++) {
            movie = mMovies.get(i);
            if (movie.getId() == movieId) {
                break;
            }
        }
        return movie;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }

    public String getImageBaseUrl() {
        return mImageBaseUrl;
    }

    public ArrayList<Movie> getMovies() {
        return mMovies;
    }
}

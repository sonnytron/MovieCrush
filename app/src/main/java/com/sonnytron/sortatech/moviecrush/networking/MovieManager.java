package com.sonnytron.sortatech.moviecrush.networking;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sonnytron.sortatech.moviecrush.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sonnyrodriguez on 7/13/16.
 */
public class MovieManager {

    private static String baseUrl = "http://api.themoviedb.org/3";
    private static String nowPlaying = "/movie/now_playing";

    private static MovieManager sMovieManager;
    private Context mContext;

    private String mApiKey = "";

    private AsyncHttpClient mClient;
    private RequestParams mParams;

    private ArrayList<Movie> mMovies;

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onMoviesRetrieved(List<Movie> movies);
    }

    public static MovieManager get(Context context) {
        if (sMovieManager == null) {
            sMovieManager = new MovieManager(context);
        }
        return sMovieManager;
    }

    public void removeCallbacks() {
        mCallbacks = null;
    }

    private MovieManager(Context context) {
        mContext = context.getApplicationContext();
        mClient = new AsyncHttpClient();
        mParams = new RequestParams();
        mCallbacks = (Callbacks) context;
    }


    public void getNowPlaying(Integer page) {
        mParams.put(page.toString(), "page");
        mParams.put(mApiKey, "api_key");
        mClient.get(baseUrl + nowPlaying, mParams, new JsonHttpResponseHandler() {
           @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               try {
                   JSONArray moviesJson = response.getJSONArray("results");
                   mMovies = Movie.fromJson(moviesJson);
                   mCallbacks.onMoviesRetrieved(mMovies);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
               Toast.makeText(sMovieManager.mContext, "Failed Network", Toast.LENGTH_SHORT).show();
           }
        });
    }

    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        return movies;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }
}

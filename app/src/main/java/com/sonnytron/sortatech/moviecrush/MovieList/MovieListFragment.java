package com.sonnytron.sortatech.moviecrush.MovieList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sonnytron.sortatech.moviecrush.Movie;
import com.sonnytron.sortatech.moviecrush.R;
import com.sonnytron.sortatech.moviecrush.networking.MovieManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sonnyrodriguez on 7/14/16.
 */
public class MovieListFragment extends Fragment {

    private RecyclerView mMovieRecyclerView;
    private Callbacks mCallbacks;
    private MovieAdapter mAdapter;
    private String mImageBaseUrl;

    public interface Callbacks {
        void onMovieSelected(Movie movie);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mMovieRecyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
        mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateFragment();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;

    }

    public void updateFragment() {
        MovieManager movieManager = MovieManager.get(getActivity());
        movieManager.setApiKey("73f9c1015526958e0eaad7045ac2269f");
        if (movieManager.getImageBaseUrl().length() > 0) {
            movieManager.getNowPlaying(new MovieManager.ManagerHandler() {
                @Override
                public void moviesReturned(List<Movie> movies) {
                    MovieManager movieManager = MovieManager.get(getActivity());
                    setImageBaseUrl(movieManager.getImageBaseUrl());
                    if (mAdapter == null) {
                        mAdapter = new MovieAdapter(movies);
                        mMovieRecyclerView.setAdapter(mAdapter);
                    } else {
                        mAdapter.setMovies(movies);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            movieManager.getImageBaseUrl(new MovieManager.ManagerHandler() {
                @Override
                public void moviesReturned(List<Movie> movies) {
                    MovieManager movieManager = MovieManager.get(getActivity());
                    setImageBaseUrl(movieManager.getImageBaseUrl());
                    if (mAdapter == null) {
                        mAdapter = new MovieAdapter(movies);
                        mMovieRecyclerView.setAdapter(mAdapter);
                    } else {
                        mAdapter.setMovies(movies);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Movie mMovie;

        private TextView mTitleTextView;
        private ImageView mImageView;

        public void bindMovie(Movie movie) {
            mMovie = movie;
            mTitleTextView.setText(mMovie.getTitle());
            updateImageView();
        }

        public MovieHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_movie_title);
            mImageView = (ImageView) itemView.findViewById(R.id.list_movie_image_view);
        }

        private void updateImageView() {
            if (mImageBaseUrl.length() > 0) {
                if (mMovie.getBackdrop().length() > 0 && mImageBaseUrl != null) {
                    String imageUrl = mImageBaseUrl + mMovie.getBackdrop();
                    Picasso.with(getContext()).load(imageUrl).into(mImageView);
                }
            }
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onMovieSelected(mMovie);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
        private List<Movie> mMovies;
        private LayoutInflater mInflater;

        public MovieAdapter(List<Movie> movies) {
            mMovies = movies;
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mInflater = LayoutInflater.from(getActivity());
            View view = mInflater.inflate(R.layout.list_item_movie, parent, false);
            return new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position) {
            Movie movie = mMovies.get(position);
            holder.bindMovie(movie);
        }

        @Override
        public int getItemCount() {
            if (mMovies != null) {
                return mMovies.size();
            } else {
                return 0;
            }
        }

        public void setMovies(List<Movie> movies) {
            mMovies = movies;
        }
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        mImageBaseUrl = imageBaseUrl;
    }

    // TODO: Remove this function and push it to MovieManager.java when you learn Observables

}

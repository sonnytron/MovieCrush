package com.sonnytron.sortatech.moviecrush.MovieList;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonnytron.sortatech.moviecrush.Movie;
import com.sonnytron.sortatech.moviecrush.R;
import com.sonnytron.sortatech.moviecrush.networking.MovieManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sonnyrodriguez on 7/14/16.
 */
public class MovieListFragment extends Fragment implements MovieManager.Callbacks {

    private RecyclerView mMovieRecyclerView;
    private Callbacks mCallbacks;
    private MovieAdapter mAdapter;
    private Integer mPageCount = 1;

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
        MovieManager manager = MovieManager.get(getActivity());
        manager.removeCallbacks();
    }

    public void updateFragment() {
        MovieManager movieManager = MovieManager.get(getActivity());
        movieManager.getNowPlaying(mPageCount);
    }

    @Override
    public void onMoviesRetrieved(List<Movie> movies) {
        if (mAdapter == null) {
            mAdapter = new MovieAdapter(movies);
            mMovieRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setMovies(movies);
            mAdapter.notifyDataSetChanged();
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
            if (mMovie.getBackdrop().length() > 0) {
                Picasso.with(getContext()).load(mMovie.getBackdrop()).into(mImageView);
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
            return mMovies.size();
        }

        public void setMovies(List<Movie> movies) {
            mMovies = movies;
        }
    }
}

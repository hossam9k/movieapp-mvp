/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shehabsalah.movieappmvp.movieslist;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shehabsalah.movieappmvp.data.Movie;
import com.shehabsalah.movieappmvp.data.source.local.MovieAppDatabase;
import com.shehabsalah.movieappmvp.data.source.remote.MovieApiConfig;
import com.shehabsalah.movieappmvp.data.source.remote.MoviesResponse;
import com.shehabsalah.movieappmvp.data.source.remote.listeners.NoConnectionListener;
import com.shehabsalah.movieappmvp.data.source.remote.listeners.OnServiceListener;
import com.shehabsalah.movieappmvp.data.source.remote.request.RequestHandler;
import com.shehabsalah.movieappmvp.data.source.remote.response.GeneralResponse;
import com.shehabsalah.movieappmvp.moviepreview.MoviePreviewActivity;
import com.shehabsalah.movieappmvp.util.ApplicationClass;
import com.shehabsalah.movieappmvp.util.Constants;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by ShehabSalah on 1/8/18.
 * Listens to user actions from the UI ({@link MoviesListFragment}), retrieves the data and updates the
 * UI as required.
 */

public class MoviesPresenter implements MoviesContract.Presenter, OnServiceListener, NoConnectionListener {
    private MoviesContract.View views;
    private MoviesSortType moviesSortType;
    private MovieAppDatabase movieAppDatabase;
    private Activity activity;

    public MoviesPresenter(MoviesContract.View views) {
        this.views = views;
        movieAppDatabase = MovieAppDatabase.getInstance(ApplicationClass.getAppContext());
    }

    @Override
    public void reload(boolean setAdapter) {
        ArrayList<Movie> movies;
        switch (moviesSortType) {
            case FAVORITES:
                movies = new ArrayList<>(movieAppDatabase.movieDAO().selectFavorites());
                if (movies.size() > 0)
                    views.showMovies(movies, setAdapter);
                else
                    views.showNoFavorites();
                break;
            case TOP_RATED:
                movies = new ArrayList<>(movieAppDatabase.movieDAO().selectTopRatedMovies());
                if (movies.size() > 0)
                    views.showMovies(movies, setAdapter);
                else
                    loadMovies();
                break;
            case MOST_POPULAR:
                movies = new ArrayList<>(movieAppDatabase.movieDAO().selectPopularMovies());
                if (movies.size() > 0)
                    views.showMovies(movies, setAdapter);
                else
                    views.showNoMovies();
                break;
        }
    }

    @Override
    public void goToDetailsActivity(Movie movie) {
        //ToDo: make intent to the moviesDetails
    }

    @Override
    public void loadMovies() {
        MovieApiConfig movieApiConfig = RequestHandler.getClient(Constants.BASE_URL, null).create(MovieApiConfig.class);
        switch (moviesSortType) {
            case MOST_POPULAR:
                RequestHandler.execute(movieApiConfig.executePopular(Constants.API_KEY), this, this, ApplicationClass.getAppContext());
                break;
            case TOP_RATED:
                RequestHandler.execute(movieApiConfig.executeTopRated(Constants.API_KEY), this, this, ApplicationClass.getAppContext());
                break;
            case FAVORITES:
                reload(true);
                break;
        }
    }

    @Override
    public void setMoviesType(MoviesSortType moviesType, boolean reload) {
        this.moviesSortType = moviesType;
        if (reload) reload(true);
    }

    @Override
    public MoviesSortType getMoviesType() {
        return moviesSortType;
    }

    @Override
    public void noInternetConnection() {
        views.showNoInternetConnection();
        reload(true);
    }

    @Override
    public void onResponse(String TAG, Object response) {
        Log.e("requests", "Request End");
        if (response instanceof MoviesResponse) {
            MoviesResponse moviesResponse = (MoviesResponse) response;
            ArrayList<Movie> movies = moviesResponse.getResults();
            if (movies.size() > 0) {
                saveListIntoDatabase(movies);
                reload(true);
            } else {
                if (moviesSortType == MoviesSortType.TOP_RATED)
                    views.showNoMovies();
                else
                    reload(true);
            }
        } else {
            onErrorResponse("", new GeneralResponse("-1", "Error Response!"));
        }
    }

    @Override
    public void onErrorResponse(String TAG, GeneralResponse response) {
        views.showServerError(response.getMessage());
    }

    @Override
    public void openMoviePreview(Movie movie, View imageView, View textView, View cardView) {
        if (movie!=null){
            Intent intent = new Intent(activity, MoviePreviewActivity.class);
            intent.putExtra(Constants.MOVIE_EXTRA, movie);
            views.makeBackgroundBlur();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //1
                Pair<View, String> titlePair = Pair.create(textView, Constants.KEY_CONNECTION_TITLE);
                //2
                Pair<View, String> imagePair = Pair.create(imageView, Constants.KEY_CONNECTION_IMAGE);
                //3
                Pair<View, String> containerPair = Pair.create(cardView, Constants.KEY_CONNECTION_CONTAINER);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activity,
                                titlePair, imagePair, containerPair);
                activity.startActivity(intent, options.toBundle());
            } else {
                activity.startActivity(intent);
            }
        }
    }

    private void saveListIntoDatabase(ArrayList<Movie> movies) {
        deleteAll();
        for (Movie movie : movies) {
            switch (moviesSortType){
                case MOST_POPULAR:
                    movie.setType(Constants.PAGE_POPULAR);
                    break;
                case TOP_RATED:
                    movie.setType(Constants.PAGE_TOP_RATED);
                    break;
            }
            movieAppDatabase.movieDAO().insertMovie(movie);
        }
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void deleteAll() {
        if (moviesSortType == MoviesSortType.MOST_POPULAR)
            movieAppDatabase.movieDAO().deleteAll();
    }
}
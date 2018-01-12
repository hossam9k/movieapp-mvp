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
package com.shehabsalah.movieappmvp.moviedetails;

import com.shehabsalah.movieappmvp.data.Movie;
import com.shehabsalah.movieappmvp.data.source.local.MovieAppDatabase;
import com.shehabsalah.movieappmvp.util.ApplicationClass;

/**
 * Created by ShehabSalah on 1/12/18.
 */

public class DetailsPresenter implements DetailsContract.presenter {

    private DetailsContract.view view;
    private MovieAppDatabase movieAppDatabase;

    public DetailsPresenter(DetailsContract.view view) {
        this.view = view;
        movieAppDatabase = MovieAppDatabase.getInstance(ApplicationClass.getAppContext());
    }

    @Override
    public void loadMovieInformation(int movieId) {

    }

    @Override
    public void onFavoriteClick(Movie movie) {

    }
}

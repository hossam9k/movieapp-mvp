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

package com.shehabsalah.movieappmvp.util;

/**
 * Created by ShehabSalah on 1/8/18.
 * This class contain all constants variables that will be used among this application.
 */

public class Constants {

    public static final int FAVORITE_ACTIVE             = 1;
    public static final int FAVORITE_NOT_ACTIVE         = 0;
    //API KEY
    public static final String API_KEY                  = "0ee45dc23e205b3b3147467c2e583359"; //"API_KEY"; // <== TODO! (Add TheMovieDB API KEY HERE)

    //LINKS
    public static final String BASE_URL                 = "http://api.themoviedb.org/3/movie/";
    public static final String IMAGE_URL                = "http://image.tmdb.org/t/p/w342";
    public static final String BACKDROP_URL             = "http://image.tmdb.org/t/p/w780";

    //PARAMS
    public static final String PARAM_API_KEY            = "api_key";

    //PAGES
    public static final String PAGE_POPULAR             = "popular";
    public static final String PAGE_TOP_RATED           = "top_rated";

    //EXTRAS
    public static final String MOVIE_EXTRA              = "movie";

    //TRANSACTIONS
    public static final String KEY_CONNECTION_TITLE     = "KEY_CONNECTION_NAME";
    public static final String KEY_CONNECTION_IMAGE     = "KEY_CONNECTION_IMAGE";
    public static final String KEY_CONNECTION_CONTAINER = "KEY_CONNECTION_CONTAINER";
}

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
package com.shehabsalah.movieappmvp.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.shehabsalah.movieappmvp.data.Movie;
import java.util.List;

/**
 * Created by ShehabSalah on 1/9/18.
 * Data Access Object for the movies table.
 */
@Dao
public interface MovieDAO {

    @Query("SELECT * FROM movies WHERE movie_type = 'popular' ORDER BY popularity DESC")
    List<Movie> selectPopularMovies();

    @Query("SELECT * FROM movies WHERE movie_type = 'top_rated' ORDER BY db_id ASC")
    List<Movie> selectTopRatedMovies();

    @Query("SELECT * FROM movies WHERE favorite = 1")
    List<Movie> selectFavorites();

    /**
     * Insert a movie in the database. If the task already exists, ignore it.
     *
     * @param movie the movie to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    /**
     * Update a movie.
     *
     * @param movie movie to be updated
     * @return the number of movies updated. This should always be 1.
     */
    @Update
    int updateMovie(Movie movie);

    /**
     * Delete a movie by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM movies WHERE favorite = 0")
    int deleteAll();


}

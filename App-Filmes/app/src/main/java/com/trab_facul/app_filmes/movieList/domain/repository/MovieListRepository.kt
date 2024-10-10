package com.trab_facul.app_filmes.movieList.domain.repository

import com.trab_facul.app_filmes.movieList.domain.model.Movie
import com.trab_facul.app_filmes.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}
package com.trab_facul.app_filmes.details.presentation

import com.trab_facul.app_filmes.movieList.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)

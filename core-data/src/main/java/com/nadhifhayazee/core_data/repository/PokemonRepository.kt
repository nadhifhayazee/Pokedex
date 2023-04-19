package com.nadhifhayazee.core_data.repository

import com.nadhifhayazee.core_model.Pokemon
import com.nadhifhayazee.core_model.PokemonResponse
import retrofit2.Response

interface PokemonRepository {
    suspend fun getListPokemon(
        limit: Int,
        offset: Int
    ): Response<PokemonResponse>

    suspend fun getDetailPokemon(
        name: String
    ): Response<Pokemon>
}
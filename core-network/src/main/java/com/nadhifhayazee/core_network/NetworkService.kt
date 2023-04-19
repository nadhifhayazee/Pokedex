package com.nadhifhayazee.core_network

import com.nadhifhayazee.core_model.Pokemon
import com.nadhifhayazee.core_model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getDetailPokemon(
        @Path("name") name: String?
    ): Response<Pokemon>
}
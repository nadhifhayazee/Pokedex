package com.nadhifhayazee.core_data.repository

import com.nadhifhayazee.core_model.Pokemon
import com.nadhifhayazee.core_model.PokemonResponse
import com.nadhifhayazee.core_network.NetworkService
import retrofit2.Response
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val service: NetworkService
) : PokemonRepository {
    override suspend fun getListPokemon(limit: Int, offset: Int): Response<PokemonResponse> {
        return service.getListPokemon(limit, offset)
    }

    override suspend fun getDetailPokemon(name: String): Response<Pokemon> {
        return service.getDetailPokemon(name)
    }
}
package com.nadhifhayazee.core_data.repository

import com.nadhifhayazee.core_datastore.entity.PokemonEntity

interface MyPokemonRepository {
    suspend fun addPokemon(pokemonEntity: PokemonEntity)
    suspend fun getAllPokemon(): List<PokemonEntity>
    suspend fun getPokemonById(id: Int): PokemonEntity?
    suspend fun deletePokemon(pokemonEntity: PokemonEntity)
}
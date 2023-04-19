package com.nadhifhayazee.core_data.repository

import com.nadhifhayazee.core_datastore.dao.PokemonDao
import com.nadhifhayazee.core_datastore.entity.PokemonEntity
import javax.inject.Inject

class MyPokemonRepositoryImpl @Inject constructor(
    private val pokemonDao: PokemonDao
) : MyPokemonRepository {
    override suspend fun addPokemon(pokemonEntity: PokemonEntity) {
        return pokemonDao.addPokemon(pokemonEntity)
    }

    override suspend fun updatePokemon(pokemonEntity: PokemonEntity) {
        return pokemonDao.updatePokemon(pokemonEntity)
    }

    override suspend fun getAllPokemon(): List<PokemonEntity> {
        return pokemonDao.getAllMyPokemon()
    }

    override suspend fun getPokemonById(id: Int): PokemonEntity? {
        return pokemonDao.getMyPokemonById(id)
    }

    override suspend fun deletePokemonById(id: Int) {
        return pokemonDao.deletePokemonById(id)
    }
}
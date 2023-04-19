package com.nadhifhayazee.core_datastore.dao

import androidx.room.*
import com.nadhifhayazee.core_datastore.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPokemon(pokemonEntity: PokemonEntity)

    @Delete
    suspend fun deletePokemon(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM my_pokemon")
    suspend fun getAllMyPokemon(): List<PokemonEntity>

    @Query("SELECT * FROM my_pokemon WHERE pokemonId=:id")
    suspend fun getMyPokemonById(id: Int): PokemonEntity?
}
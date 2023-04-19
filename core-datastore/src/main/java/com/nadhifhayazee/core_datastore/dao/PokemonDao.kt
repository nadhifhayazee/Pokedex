package com.nadhifhayazee.core_datastore.dao

import androidx.room.*
import com.nadhifhayazee.core_datastore.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPokemon(pokemonEntity: PokemonEntity)

    @Update
    suspend fun updatePokemon(pokemonEntity: PokemonEntity)

    @Query("DELETE FROM my_pokemon WHERE pokemonId=:id")
    suspend fun deletePokemonById(id: Int)

    @Query("SELECT * FROM my_pokemon")
    suspend fun getAllMyPokemon(): List<PokemonEntity>

    @Query("SELECT * FROM my_pokemon WHERE pokemonId=:id")
    suspend fun getMyPokemonById(id: Int): PokemonEntity?
}
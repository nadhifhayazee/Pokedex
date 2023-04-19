package com.nadhifhayazee.core_datastore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nadhifhayazee.core_datastore.dao.PokemonDao
import com.nadhifhayazee.core_datastore.entity.PokemonEntity


@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
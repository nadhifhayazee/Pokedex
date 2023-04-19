package com.nadhifhayazee.core_datastore.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("my_pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    @ColumnInfo(name = "pokemonId")
    val pokemonId: Int?,
    @ColumnInfo(name= "pokemonName")
    val pokemonName: String?,
    @ColumnInfo(name = "nickname")
    val nickname: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "renamedCount")
    val renamedCount: Int

)
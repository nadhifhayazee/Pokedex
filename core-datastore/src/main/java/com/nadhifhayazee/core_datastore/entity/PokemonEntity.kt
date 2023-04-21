package com.nadhifhayazee.core_datastore.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("my_pokemon")
data class PokemonEntity(
    @PrimaryKey()
    @ColumnInfo(name = "pokemonId")
    val pokemonId: Int?,
    @ColumnInfo(name= "pokemonName")
    val pokemonName: String?,
    @ColumnInfo(name = "firstNickname")
    val firstNickname: String,
    @ColumnInfo(name = "nickname")
    var nickname: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "renamedCount")
    var renamedCount: Int

)
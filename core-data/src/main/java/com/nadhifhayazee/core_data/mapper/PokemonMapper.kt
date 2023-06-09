package com.nadhifhayazee.core_data.mapper

import com.nadhifhayazee.core_datastore.entity.PokemonEntity
import com.nadhifhayazee.core_model.MyPokemon
import com.nadhifhayazee.core_model.Pokemon

object PokemonMapper {

    fun pokemonToPokemonEntity(pokemon: Pokemon, nickName: String): PokemonEntity {
        return PokemonEntity(
            pokemonId = pokemon.id,
            pokemonName = pokemon.name,
            nickname = nickName,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png",
            renamedCount = 1,
            firstNickname = nickName
        )
    }

    fun pokemonEntityToMyPokemon(list: List<PokemonEntity>?): List<MyPokemon> {
       return list?.map { item ->
            MyPokemon(
                id = item.pokemonId,
                name = item.pokemonName,
                nickname = item.nickname,
                imageUrl = item.imageUrl
            )

        } ?: listOf()
    }
}
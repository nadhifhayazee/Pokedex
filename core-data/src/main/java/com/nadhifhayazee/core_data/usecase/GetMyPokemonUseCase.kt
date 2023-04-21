package com.nadhifhayazee.core_data.usecase

import com.nadhifhayazee.core_data.mapper.PokemonMapper
import com.nadhifhayazee.core_data.repository.MyPokemonRepository
import com.nadhifhayazee.core_model.MyPokemon
import com.nadhifhayazee.core_model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyPokemonUseCase @Inject constructor(
    private val myPokemonRepository: MyPokemonRepository
) {

    operator fun invoke(): Flow<State<List<MyPokemon>>> {
        return flow {
            emit(State.Loading())
            try {
                val listFromDb = myPokemonRepository.getAllPokemon()
                val listMyPokemon = PokemonMapper.pokemonEntityToMyPokemon(listFromDb)
                emit(State.Success(listMyPokemon))
            } catch (e: Exception) {
                emit(
                    State.Error("Failed to get my pokemon")
                )
            }
        }
    }
}
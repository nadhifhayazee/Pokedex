package com.nadhifhayazee.core_data.usecase

import com.nadhifhayazee.core_data.repository.PokemonRepository
import com.nadhifhayazee.core_model.Pokemon
import com.nadhifhayazee.core_model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    operator fun invoke(name: String): Flow<State<Pokemon>> {
        return flow {
            try {
                val response = pokemonRepository.getDetailPokemon(name)
                if (response.isSuccessful && response.body() != null) {
                    emit(State.Success(response.body()!!))
                } else {
                    emit(State.Error("Failed to retrieve data"))
                }
            } catch (e: Exception) {
                emit(State.Error(e.message))
            }
        }
    }
}
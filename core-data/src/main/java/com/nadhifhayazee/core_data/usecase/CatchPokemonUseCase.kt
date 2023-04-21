package com.nadhifhayazee.core_data.usecase

import com.nadhifhayazee.core_data.mapper.PokemonMapper
import com.nadhifhayazee.core_data.repository.MyPokemonRepository
import com.nadhifhayazee.core_data.util.MyPokemonUtil
import com.nadhifhayazee.core_model.Pokemon
import com.nadhifhayazee.core_model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class CatchPokemonUseCase @Inject constructor(
    private val myPokemonRepository: MyPokemonRepository
) {


    suspend operator fun invoke(pokemon: Pokemon, nickName: String): Flow<State<Boolean>> {
        return flow {
            emit(State.Loading())
            try {
                myPokemonRepository.addPokemon(
                    PokemonMapper.pokemonToPokemonEntity(
                        pokemon,
                        nickName
                    )
                )
                emit(State.Success(true))
            } catch (e: Exception){
                emit(State.Error("Failed to catch pokemon"))
            }
        }
    }
}
package com.nadhifhayazee.core_data.usecase

import com.nadhifhayazee.core_data.repository.MyPokemonRepository
import com.nadhifhayazee.core_data.util.MyPokemonUtil
import com.nadhifhayazee.core_model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReleasePokemonUseCase @Inject constructor(
    private val myPokemonRepository: MyPokemonRepository
) {

    operator fun invoke(pokemonId: Int): Flow<State<Boolean>>{
        return flow {
            try {
                if (MyPokemonUtil.isPrime()){
                    myPokemonRepository.deletePokemonById(pokemonId)
                    emit(State.Success(true))
                } else {
                    emit(State.Error("Failed to release pokemon"))
                }

            }catch (e: Exception){
                emit(State.Error("Failed to release pokemon"))
            }
        }
    }
}
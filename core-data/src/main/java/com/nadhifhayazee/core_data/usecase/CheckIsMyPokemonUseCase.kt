package com.nadhifhayazee.core_data.usecase

import com.nadhifhayazee.core_data.repository.MyPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckIsMyPokemonUseCase @Inject constructor(
    private val myPokemonRepository: MyPokemonRepository
) {

    operator fun invoke(id: Int): Flow<Boolean> {

        return flow {
            try {
                emit(myPokemonRepository.getPokemonById(id) != null)
            } catch (e: Exception) {
                emit(false)
            }
        }

    }
}
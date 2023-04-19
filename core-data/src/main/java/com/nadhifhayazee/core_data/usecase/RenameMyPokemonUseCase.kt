package com.nadhifhayazee.core_data.usecase

import com.nadhifhayazee.core_data.repository.MyPokemonRepository
import com.nadhifhayazee.core_data.util.MyPokemonUtil
import com.nadhifhayazee.core_model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RenameMyPokemonUseCase @Inject constructor(
    private val myPokemonRepository: MyPokemonRepository
) {

    operator fun invoke(id: Int): Flow<State<Boolean>> {
        return flow {
            try {
                val itemToUpdate = myPokemonRepository.getPokemonById(id)
                if (itemToUpdate != null) {
                    val newName = MyPokemonUtil.getRenamedName(
                        itemToUpdate.firstNickname,
                        itemToUpdate.renamedCount
                    )
                    itemToUpdate.nickname = newName
                    itemToUpdate.renamedCount++

                    myPokemonRepository.updatePokemon(itemToUpdate)

                    emit(State.Success(true))
                } else {
                    emit(State.Error("Pokemon not found"))
                }

            } catch (e: Exception) {
                emit(State.Error("Rename pokemon failed"))
            }
        }
    }
}
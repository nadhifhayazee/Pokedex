package com.nadhifhayazee.pokedex.my_pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.core_data.usecase.GetMyPokemonUseCase
import com.nadhifhayazee.core_data.usecase.ReleasePokemonUseCase
import com.nadhifhayazee.core_data.usecase.RenameMyPokemonUseCase
import com.nadhifhayazee.core_model.MyPokemon
import com.nadhifhayazee.core_model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPokemonViewModel @Inject constructor(
    private val getMyPokemonUseCase: GetMyPokemonUseCase,
    private val releasePokemonUseCase: ReleasePokemonUseCase,
    private val renameMyPokemonUseCase: RenameMyPokemonUseCase
) : ViewModel(){


    private val _myPokemonState = MutableStateFlow<State<List<MyPokemon>>>(State.Init())
    val myPokemonState get() = _myPokemonState.asStateFlow()

    fun getMyPokemon() {
        viewModelScope.launch {
            getMyPokemonUseCase().collectLatest {
                _myPokemonState.value = it
            }
        }
    }

    fun releasePokemon(id: Int) {
        viewModelScope.launch {
            releasePokemonUseCase(id).collectLatest {
                when (it){
                    is State.Success -> {getMyPokemon()}
                    else ->{}
                }
            }
        }
    }

    fun renamePokemon(id: Int) {
        viewModelScope.launch {
            renameMyPokemonUseCase(id).collectLatest {
                when (it){
                    is State.Success -> {getMyPokemon()}
                    else ->{}
                }
            }
        }
    }
}
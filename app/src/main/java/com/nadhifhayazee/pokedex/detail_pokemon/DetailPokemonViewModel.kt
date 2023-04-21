package com.nadhifhayazee.pokedex.detail_pokemon

import androidx.lifecycle.*
import com.nadhifhayazee.core_data.usecase.CatchPokemonUseCase
import com.nadhifhayazee.core_data.usecase.CheckIsMyPokemonUseCase
import com.nadhifhayazee.core_data.usecase.GetDetailPokemonUseCase
import com.nadhifhayazee.core_data.usecase.ReleasePokemonUseCase
import com.nadhifhayazee.core_data.util.MyPokemonUtil
import com.nadhifhayazee.core_model.Pokemon
import com.nadhifhayazee.core_model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val getDetailPokemonUseCase: GetDetailPokemonUseCase,
    private val catchPokemonUseCase: CatchPokemonUseCase,
    private val checkIsMyPokemonUseCase: CheckIsMyPokemonUseCase,
    private val releasePokemonUseCase: ReleasePokemonUseCase
) : ViewModel() {


    private val _detailPokemon = MutableStateFlow<State<Pokemon>>(State.Init())
    val detailPokemon get() = _detailPokemon.asStateFlow()

    private val _catchPokemonState = MutableStateFlow<State<Boolean>>(State.Init())
    val catchPokemonState get() = _catchPokemonState.asStateFlow()

    private val _isMyPokemon = MutableLiveData<Boolean>()
    val isMyPokemon: LiveData<Boolean> = _isMyPokemon
    fun checkIsMyPokemon(id: Int) {
        viewModelScope.launch{
            checkIsMyPokemonUseCase(id).collectLatest {
                _isMyPokemon.value = it
            }
        }
    }


    fun getDetailPokemon(name: String) {
        viewModelScope.launch {
            getDetailPokemonUseCase(name).collectLatest {
                _detailPokemon.value = it
            }
        }
    }

    fun getPossibility(): Boolean {
        return MyPokemonUtil.getFiftyFiftyPossibility()
    }

    fun catchPokemon(pokemon: Pokemon, nickName: String) {
        viewModelScope.launch {
            catchPokemonUseCase(pokemon, nickName).collectLatest {
                pokemon.id?.let { it1 -> checkIsMyPokemon(it1) }
                _catchPokemonState.value = it
            }
        }
    }

    fun releasePokemon(id: Int){
        viewModelScope.launch {
            releasePokemonUseCase(id).collectLatest {
                checkIsMyPokemon(id)
            }
        }
    }
}
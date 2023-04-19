package com.nadhifhayazee.pokedex.list_pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nadhifhayazee.core_data.usecase.GetPokemonListUseCase
import com.nadhifhayazee.core_model.PokemonResult
import com.nadhifhayazee.core_model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _pokemonListState =
        MutableStateFlow<State<PagingData<PokemonResult>>>(State.Loading())
    val pokemonListState get() = _pokemonListState.asStateFlow()

    fun getPokemonList() {
        viewModelScope.launch {
            getPokemonListUseCase().cachedIn(this).collectLatest {
                _pokemonListState.value = State.Success(it)
            }
        }
    }
}
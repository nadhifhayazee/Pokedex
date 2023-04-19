package com.nadhifhayazee.core_data.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nadhifhayazee.core_data.paging.PokemonPagingSource
import com.nadhifhayazee.core_data.repository.PokemonRepository
import com.nadhifhayazee.core_model.PokemonResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    operator fun invoke(): Flow<PagingData<PokemonResult>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PokemonPagingSource(pokemonRepository) }
        ).flow
    }
}
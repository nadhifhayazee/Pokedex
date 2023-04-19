package com.nadhifhayazee.core_data.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nadhifhayazee.core_data.repository.PokemonRepository
import com.nadhifhayazee.core_model.PokemonResult
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : PagingSource<Int, PokemonResult>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        return try {
            val pageIndex = params.key ?: 0
            val response = pokemonRepository.getListPokemon(
                limit = 20,
                offset = pageIndex
            )

            val body = response.body()
            val pokemonList = response.body()?.results
            var nextPageNumber: Int? = null
            var prefPageNumber: Int? = null
            if (body?.next != null) {
                val nextUri = Uri.parse(body.next)
                val nextPageQuery = nextUri.getQueryParameter("offset")
                nextPageNumber = nextPageQuery?.toInt()
            }

            if (body?.previous != null){
                val prefUri = Uri.parse(body.previous)
                val prefPageQuery = prefUri.getQueryParameter("offset")
                prefPageNumber = prefPageQuery?.toInt()
            }

            LoadResult.Page(
                data = pokemonList.orEmpty(),
                prevKey = prefPageNumber,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
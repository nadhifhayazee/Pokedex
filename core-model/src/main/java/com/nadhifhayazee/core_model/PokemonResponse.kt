package com.nadhifhayazee.core_model

data class PokemonResponse(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>?
)



package com.nadhifhayazee.core_model

data class MyPokemon(
    val id: Int?,
    val name: String?,
    val nickname: String?,
    val imageUrl: String?,
) {
    fun getPreviewName() = "$name ($nickname)"
}
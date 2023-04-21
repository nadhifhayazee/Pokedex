package com.nadhifhayazee.core_model

data class Pokemon(
    val id: Int?,
    val name: String?,
    val height: Int?,
    val weight: Int?,
    val moves: List<Moves>?,
    val types: List<Types>?
) {
    fun getTypesToString(): String {
        return this.types?.joinToString(separator = ", ") { it.type?.name ?: "-" } ?: "-"
    }

    fun getMovesToString(): String {
        return this.moves?.joinToString(separator = ", ") { it.move?.name ?: "-" } ?: "-"
    }
}

data class Moves(
    val move: Move?
)

data class Move(
    val name: String?
)
data class Types(
    val type: Type?
)
data class Type(
    val name: String?
)

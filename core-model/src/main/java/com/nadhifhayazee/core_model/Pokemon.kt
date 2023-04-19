package com.nadhifhayazee.core_model

data class Pokemon(
    val id: Long?,
    val name: String?,
    val moves: List<Move>?,
    val types: List<Type>?
)

data class Move(
    val name: String?
)

data class Type(
    val name: String?
)

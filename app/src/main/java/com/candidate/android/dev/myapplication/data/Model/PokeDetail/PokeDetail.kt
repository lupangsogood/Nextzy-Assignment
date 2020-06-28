package com.candidate.android.dev.myapplication.data.Model.PokeDetail

data class PokeDetail(
    var height: Int? = null,
    var id: Int? = null,
    var name: String? = null,
    var order: Int? = null,
    var species: Species? = null,
    var sprites: Sprites? = null,
    var stats: ArrayList<Stat>? = null,
    var types: ArrayList<Type>? = null,
    var weight: Int? = null
)
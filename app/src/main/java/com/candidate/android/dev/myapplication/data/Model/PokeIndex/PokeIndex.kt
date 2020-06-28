package com.candidate.android.dev.myapplication.data.Model.PokeIndex

import com.candidate.android.dev.myapplication.data.PokemonArrayList

data class PokeIndex(
    var count: Int? = null,
    var next: String? = null,
    var previous: String? = null,
    var results: PokemonArrayList? = null
)
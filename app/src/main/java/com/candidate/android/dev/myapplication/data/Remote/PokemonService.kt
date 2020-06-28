package com.candidate.android.dev.myapplication.data.Remote

import com.candidate.android.dev.myapplication.data.Model.PokeDetail.PokeDetail
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndex
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {


    @GET("pokemon")
    suspend fun getPokemon(
        @Query("limit") limit: Int? = 20,
        @Query("offset") offset: Int? = 0
    ):PokeIndex

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ):PokeDetail


}
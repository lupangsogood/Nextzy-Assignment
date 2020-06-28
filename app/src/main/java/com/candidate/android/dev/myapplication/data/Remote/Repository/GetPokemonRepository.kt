package com.candidate.android.dev.myapplication.data.Remote.Repository

import com.candidate.android.dev.myapplication.data.BaseResult
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.PokeDetail
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndex

interface GetPokemonRepository{

    suspend fun getPokemon(limit:Int?,offset:Int?):BaseResult<PokeIndex>

    suspend fun getPokemonDetail(name:String):BaseResult<PokeDetail>
}
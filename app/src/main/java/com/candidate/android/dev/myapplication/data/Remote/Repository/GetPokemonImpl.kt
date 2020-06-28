package com.candidate.android.dev.myapplication.data.Remote.Repository

import com.candidate.android.dev.myapplication.data.BaseResult
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.PokeDetail
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndex
import com.candidate.android.dev.myapplication.data.Remote.ApiManager
import com.candidate.android.dev.myapplication.data.Remote.RemoteDataSource

class GetPokemonImpl :GetPokemonRepository,RemoteDataSource(){
    override suspend fun getPokemon(limit:Int?,offset:Int?): BaseResult<PokeIndex> {
        val result= call(ApiManager.callService.getPokemon(limit ?: 20,offset ?: 0))
       return  result
    }

    override suspend fun getPokemonDetail(name: String): BaseResult<PokeDetail> {
        return call(ApiManager.callService.getPokemonDetail(name))
    }
}
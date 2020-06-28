package com.candidate.android.dev.myapplication.data.Remote.Repository

import com.candidate.android.dev.myapplication.data.BaseResult
import com.candidate.android.dev.myapplication.data.Local.Repository.PokeImpl
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.PokeDetail
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndex
import com.candidate.android.dev.myapplication.data.Remote.ApiManager
import com.candidate.android.dev.myapplication.data.Remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class GetPokemonImpl(private val pokemonCache : PokeImpl) :GetPokemonRepository,RemoteDataSource(){
    override suspend fun getPokemon(limit:Int?,offset:Int?): BaseResult<PokeIndex> {
        val result= call(ApiManager.callService.getPokemon(limit ?: 20,offset ?: 0))
        CoroutineScope(IO).launch {
            result.data?.results?.mapIndexed { index, pokeIndexResult ->
                pokemonCache.insertDashBoard(pokeIndexResult)
            }
        }
       return  result
    }

    override suspend fun getPokemonDetail(name: String): BaseResult<PokeDetail> {
        return call(ApiManager.callService.getPokemonDetail(name))
    }
}
package com.candidate.android.dev.myapplication.data.Local.Repository

import androidx.lifecycle.LiveData
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult

interface PokeRepository{

     fun searchPokemonName(name:String?):LiveData<List<String>>
     fun getPokemonList(offset:Int): LiveData<List<PokeIndexResult>>
     fun getPokemonByName(name: String?):LiveData<List<PokeIndexResult>>
     fun insertDashBoard(data: PokeIndexResult)
     fun deletePokemonData()
}
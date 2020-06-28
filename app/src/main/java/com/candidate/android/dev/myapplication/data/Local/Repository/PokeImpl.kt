package com.candidate.android.dev.myapplication.data.Local.Repository

import androidx.lifecycle.LiveData
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult

class PokeImpl(private val pokeDao : PokeDAO) :PokeRepository{

    override fun getPokemonList(offset: Int): LiveData<List<PokeIndexResult>> {
        return pokeDao.getPokemon(offset)
    }

    override fun insertDashBoard(data: PokeIndexResult) {
        return pokeDao.insertPokemonData(data)
    }

    override fun deleteDashboard() {

    }
}
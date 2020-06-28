package com.candidate.android.dev.myapplication.data.Local.Repository

import androidx.lifecycle.LiveData
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult

class PokeImpl(private val pokeDao : PokeDAO) :PokeRepository{
    override fun searchPokemonName(name: String?): LiveData<List<String>> {
        return pokeDao.searchPokemon(name)
    }

    override fun getPokemonList(offset: Int): LiveData<List<PokeIndexResult>> {
        return pokeDao.getPokemon(offset)
    }

    override fun getPokemonByName(name: String?): LiveData<List<PokeIndexResult>> {
        return pokeDao.getPokemonByName(name)
    }

    override fun insertDashBoard(data: PokeIndexResult) {
        return pokeDao.insertPokemonData(data)
    }

    override fun deletePokemonData() {
        return pokeDao.deletePokemonData()
    }
}
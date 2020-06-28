package com.candidate.android.dev.myapplication.data.Local.Repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult

@Dao
interface PokeDAO{
    @Query("SELECT * FROM pokeIndex LIMIT 20 OFFSET :offset")
    fun getPokemon(offset:Int): LiveData<List<PokeIndexResult>>

    @Insert
    fun insertPokemonData(data : PokeIndexResult)

    @Query("DELETE FROM pokeIndex")
    fun deleteDashboardData()
}
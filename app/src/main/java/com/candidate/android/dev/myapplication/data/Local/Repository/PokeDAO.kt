package com.candidate.android.dev.myapplication.data.Local.Repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult

@Dao
interface PokeDAO {

    @Query("SELECT name FROM pokename WHERE name LIKE :nameSearch")
    fun searchPokemon(nameSearch: String?): LiveData<List<String>>

    @Query("SELECT * FROM pokename WHERE name LIKE :nameSearch")
    fun getPokemonByName(nameSearch: String?): LiveData<List<PokeIndexResult>>

    @Query("SELECT * FROM pokename LIMIT 1000 OFFSET :offset")
    fun getPokemon(offset: Int): LiveData<List<PokeIndexResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonData(data: PokeIndexResult)

    @Query("DELETE FROM pokename")
    fun deletePokemonData()
}
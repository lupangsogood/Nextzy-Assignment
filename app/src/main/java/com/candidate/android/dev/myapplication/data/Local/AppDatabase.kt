package com.candidate.android.dev.myapplication.data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.candidate.android.dev.myapplication.data.Local.Repository.PokeDAO
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult

@Database(entities = [PokeIndexResult::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokeDAO(): PokeDAO
}
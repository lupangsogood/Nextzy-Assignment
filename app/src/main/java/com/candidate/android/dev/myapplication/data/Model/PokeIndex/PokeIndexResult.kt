package com.candidate.android.dev.myapplication.data.Model.PokeIndex

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pokename")
data class PokeIndexResult(
    @PrimaryKey val name: String,
    var url: String? = null
)
package com.candidate.android.dev.myapplication.data.Local

import androidx.room.TypeConverter
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType

class PokemonListConverter : BaseListConverter<PokeIndexResult>() {

    @TypeConverter
    override fun fromString(value: String?): ArrayList<PokeIndexResult> {
        val typeToken = object : TypeToken<ArrayList<PokeIndexResult>>() {}
        val type = typeToken.type as ParameterizedType
        val list = GsonBuilder().create().fromJson<ArrayList<PokeIndexResult>>(value, type)
        return list ?: ArrayList()
    }

}
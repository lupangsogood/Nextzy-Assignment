package com.candidate.android.dev.myapplication.data.Local

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder

abstract class BaseListConverter<T> {

    @TypeConverter
    abstract fun fromString(value: String?): ArrayList<T>

    @TypeConverter
    fun fromArrayList(value: ArrayList<T>?): String {
        val json = GsonBuilder().create().toJson(value)
        return json ?: ""
    }
}
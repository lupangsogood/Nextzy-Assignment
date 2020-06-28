package com.candidate.android.dev.myapplication.data

import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult
import com.candidate.android.dev.myapplication.util.Constant.Companion.KEY_DATA
import com.candidate.android.dev.myapplication.util.Constant.Companion.KEY_MESSAGE
import com.candidate.android.dev.myapplication.util.Constant.Companion.KEY_STATUS
import com.google.gson.annotations.SerializedName
import java.net.HttpURLConnection

data class BaseResult<out T> constructor(
    @SerializedName(KEY_STATUS)
    var statusCode: Int = -1,
    @SerializedName(KEY_MESSAGE)
    var message: String? = "",
    @SerializedName(KEY_DATA)
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T?): BaseResult<T> = BaseResult(HttpURLConnection.HTTP_OK, null, data)
        fun <T> error(statusCode: Int, message: String?): BaseResult<T> =
            BaseResult(statusCode, message, null)
    }
    fun isSuccessful(): Boolean = statusCode == HttpURLConnection.HTTP_OK
}

typealias PokemonArrayList = (ArrayList<PokeIndexResult>?)
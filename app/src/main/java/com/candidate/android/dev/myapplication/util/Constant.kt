package com.candidate.android.dev.myapplication.util

import android.R.color


class Constant {
    companion object {
        const val baseUrl =
            "https://pokeapi.co/api/v2/"

        const val KEY_STATUS = "KEY_STATUS"
        const val KEY_MESSAGE = "KEY_MESSAGE"
        const val KEY_DATA = "KEY_DATA"

        const val ERR_CODE_NO_STATUS_CODE = -1
        const val ERR_CODE_NO_INTERNET_CONNECTION = 0
        const val ERR_CODE_SOCKET_EXCEPTION = 481

        const val DATABASE_NAME = "NextzyDB"

        fun getPokemonImage(index: String): String {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index}.png"
        }



    }


}
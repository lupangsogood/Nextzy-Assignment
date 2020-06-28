package com.candidate.android.dev.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.PokeDetail
import com.candidate.android.dev.myapplication.data.PokemonArrayList
import com.candidate.android.dev.myapplication.data.Remote.Repository.GetPokemonImpl
import com.candidate.android.dev.myapplication.extension.notifyObserver
//import com.candidate.android.dev.myapplication.extension.notifyObserver
import com.candidate.android.dev.myapplication.ui.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber


class MainViewModel(private val service: GetPokemonImpl) : BaseViewModel() {

    private val _pokemonList = MutableLiveData<PokemonArrayList>()
    val pokemonList: LiveData<PokemonArrayList>
        get() = _pokemonList

    var index = 0
    var clearData = false
    var backPress = false


    fun getPokemonList() {
        if (!backPress){
            viewModelScope.launch {
                val result = service.getPokemon(null,pokemonList.value?.size)
                clearData = true
                when (result.isSuccessful()) {
                    true -> {
                        if (_pokemonList.value.isNullOrEmpty()) {
                            _pokemonList.value = result.data?.results
                        }
                    }
                    false -> {
                        Timber.d(result.message)
                    }
                }
            }
        }else{
            Timber.d("NON ")
        }
    }

    fun getNextPagePokemon() {
        index = index.plus(20)
        viewModelScope.launch {
            val result = service.getPokemon(limit = null,offset = index)
            Timber.d(result.toString())
            clearData = false
            when (result.isSuccessful()) {
                true -> {
                    backPress = false
                    result.data?.results?.let { _pokemonList.value!!.addAll(it)}
                    _pokemonList.notifyObserver()
                }
                false -> {
                    Timber.d(result.message)
                }
            }
        }
    }

}
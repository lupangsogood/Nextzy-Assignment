package com.candidate.android.dev.myapplication.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.candidate.android.dev.myapplication.data.Model.PokeDetail.PokeDetail
import com.candidate.android.dev.myapplication.data.Remote.Repository.GetPokemonImpl
import com.candidate.android.dev.myapplication.ui.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber


class DetailViewModel(private val service: GetPokemonImpl) : BaseViewModel() {

    private val _pokemonDetail = MutableLiveData<PokeDetail>()
    val pokemonDetail: LiveData<PokeDetail>
        get() = _pokemonDetail

    fun getPokemonDetail(index: String) {
        viewModelScope.launch {
            val result = service.getPokemonDetail(index)
            Timber.d(result.toString())
            when (result.isSuccessful()) {
                true -> _pokemonDetail.value = result.data
                false -> {//เว้นไว้ใส่ Dialog แจ้งว่าผิดพลาด}
                }
            }
        }
    }
}
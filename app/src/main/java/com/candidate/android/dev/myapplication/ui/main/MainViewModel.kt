package com.candidate.android.dev.myapplication.ui.main

import androidx.lifecycle.*
import com.candidate.android.dev.myapplication.data.Local.Repository.PokeImpl
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult
import com.candidate.android.dev.myapplication.data.Remote.Repository.GetPokemonImpl
import com.candidate.android.dev.myapplication.extension.notifyObserver
import com.candidate.android.dev.myapplication.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class MainViewModel(private val service: GetPokemonImpl, private val cache: PokeImpl) :
    BaseViewModel() {

    private val _pokemonList = MutableLiveData<ArrayList<PokeIndexResult>>(arrayListOf())
    val pokemonList: LiveData<ArrayList<PokeIndexResult>>
        get() = _pokemonList

    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh

    private val _scroll = MutableLiveData<Boolean>()
    val scroll: LiveData<Boolean>
        get() = _scroll

    private val index = MutableLiveData<Int>(0)
    private val getByName = MutableLiveData<String>("")
    private val name = MutableLiveData<String>("")

    var pullRefresh = false
    var isSearch = false
    var backPress = false


    var pokemonCacheData: LiveData<List<PokeIndexResult>> = Transformations.switchMap(index) {
        cache.getPokemonList(it)
    }

    var pokemonOnceData: LiveData<List<PokeIndexResult>> = Transformations.switchMap(getByName) {
        cache.getPokemonByName(it)
    }

    var pokemonNameCacheData: LiveData<List<String>> = Transformations.switchMap(name) {
        _pokemonList.value?.clear()
        cache.searchPokemonName(it)
    }

    fun getPokemonList() {
        viewModelScope.launch {
            service.getPokemon(1000, 0)
        }
    }

    fun getRefreshPokemon() {
        CoroutineScope(Main).launch {
            _pokemonList.value!!.clear()
            isNotSearch()
            index.value = 0
        }
    }

    fun getNextPagePokemon() {
        if (_pokemonList.value?.size ?: 0 >= 20) {
            CoroutineScope(Main).launch {
                isNotSearch()
                index.value = _pokemonList.value?.size!!
            }
        } else {
            CoroutineScope(Main).launch {
                isNotSearch()
                isNotShowRefresh()
                isCanNotScroll()
            }
        }
    }

    fun setUpPokeMainData(data: List<PokeIndexResult>) {
            CoroutineScope(Main).launch {
                _pokemonList.value!!.addAll(data)
                _pokemonList.notifyObserver()
                isNotBackPress()
        }
    }

    fun setupNameToComplete(nameTmp: String) {
        CoroutineScope(Main).launch {
            name.value = "%$nameTmp%"
        }
    }

    fun setupPokeNameToGetData(nameTmp: String) {
        CoroutineScope(Main).launch {
            isSearching()
            getByName.value = "%$nameTmp%"
        }
    }

    fun isPullRefresh() {
        pullRefresh = true
    }

    fun isNotPullRefresh() {
        pullRefresh = false
    }

    private fun isSearching() {
        isSearch = true
    }

    private fun isNotSearch() {
        isSearch = false
    }

    fun isNotShowRefresh() {
        _refresh.value = false
    }

    fun isShowRefresh() {
        _refresh.value = true
    }

    fun isCanNotScroll() {
        _scroll.value = false
    }

    fun isCanScroll() {
        _scroll.value = true
    }

    fun isBackPress() {
        backPress = true
    }

    fun isNotBackPress() {
        backPress = false
    }

}
package com.candidate.android.dev.myapplication.ui.main

import androidx.lifecycle.*
import com.candidate.android.dev.myapplication.data.Local.Repository.PokeDAO
import com.candidate.android.dev.myapplication.data.Local.Repository.PokeImpl
import com.candidate.android.dev.myapplication.data.Model.PokeIndex.PokeIndexResult
import com.candidate.android.dev.myapplication.data.Remote.Repository.GetPokemonImpl
import com.candidate.android.dev.myapplication.extension.notifyObserver
import com.candidate.android.dev.myapplication.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class MainViewModel(private val service: GetPokemonImpl, private val cache: PokeImpl) :
    BaseViewModel() {

    private val _pokemonList = MutableLiveData<ArrayList<PokeIndexResult>>(arrayListOf())
    val pokemonList: LiveData<ArrayList<PokeIndexResult>>
        get() = _pokemonList

    private val _refresh = MutableLiveData<Boolean>()
    val refresh :LiveData<Boolean>
        get() = _refresh

    private val _scroll = MutableLiveData<Boolean>()
    val scroll :LiveData<Boolean>
        get() = _scroll

    private val index = MutableLiveData<Int>(0)
    private val getByName = MutableLiveData<String>("")
    private val name = MutableLiveData<String>("")

    var isSearch = false
    var backPress = false


    var pokemonCacheData: LiveData<List<PokeIndexResult>> = Transformations.switchMap(index) {
        cache.getPokemonList(it)
    }

    var pokemonOnceData:LiveData<List<PokeIndexResult>> = Transformations.switchMap(getByName){
        cache.getPokemonByName(it)
    }

    var pokemonNameCacheData :LiveData<List<String>> = Transformations.switchMap(name){
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
            notSearch()
            index.value = 0
        }
    }

    fun getNextPagePokemon() {
        if (_pokemonList.value?.size ?:0 >= 20){
            CoroutineScope(Main).launch {
                notSearch()
                index.value = _pokemonList.value?.size!!
            }
        }else{
            CoroutineScope(Main).launch {
                notSearch()
                notShowRefresh()
                canNotScroll()
            }
        }
    }

    fun setUpPokeMainData(data: List<PokeIndexResult>) {
        _pokemonList.value!!.addAll(data)
        _pokemonList.notifyObserver()
    }

    fun setupNameToComplete(nameTmp:String){
        CoroutineScope(Main).launch {
            name.value = "%$nameTmp%"
        }
    }

    fun setupPokeNameToGetData(nameTmp: String){
        CoroutineScope(Main).launch {
            searching()
            getByName.value = "%$nameTmp%"
        }
    }

    fun searching(){
        isSearch = true
    }

    fun notSearch(){
        isSearch = false
    }

    fun notShowRefresh(){
        _refresh.value = false
    }

    fun showRefresh(){
        _refresh.value = true
    }

    fun canNotScroll(){
        _scroll.value = false
    }

    fun canScroll(){
        _scroll.value = true
    }

    fun isBackPress(){
        backPress = true
    }

    fun isNotBackPress(){
        backPress = false
    }

}
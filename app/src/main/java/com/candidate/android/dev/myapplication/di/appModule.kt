package com.candidate.android.dev.myapplication.di

import androidx.room.Room
//import com.candidate.android.dev.myapplication.data.Local.AppDatabase
//import com.candidate.android.dev.myapplication.data.Local.Repository.PokeImpl
import com.candidate.android.dev.myapplication.data.Remote.Repository.GetPokemonImpl
import com.candidate.android.dev.myapplication.ui.detail.DetailViewModel
import com.candidate.android.dev.myapplication.ui.main.MainViewModel
import com.candidate.android.dev.myapplication.util.Constant
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { GetPokemonImpl() }
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val roomModule = module {
//    single {
//        Room.databaseBuilder(androidApplication(), AppDatabase::class.java,Constant.DATABASE_NAME)
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//    single { get<AppDatabase>().pokeDAO()}
//
//    single { PokeImpl(get())}
}
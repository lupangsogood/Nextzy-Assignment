package com.candidate.android.dev.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancelChildren
import timber.log.Timber

open class BaseViewModel :ViewModel(){

    private fun cancelJob() {
        viewModelScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        cancelJob()
        Timber.w("$this is Cleared")
        super.onCleared()
    }
}
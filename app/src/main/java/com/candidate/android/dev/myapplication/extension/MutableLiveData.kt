package com.candidate.android.dev.myapplication.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}


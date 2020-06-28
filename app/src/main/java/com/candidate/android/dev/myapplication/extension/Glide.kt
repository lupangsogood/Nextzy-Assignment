package com.candidate.android.dev.myapplication.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.candidate.android.dev.myapplication.R

fun ImageView.loadFromURL(url:String){
    Glide.with(this)
        .load(url)
        .into(this)
}
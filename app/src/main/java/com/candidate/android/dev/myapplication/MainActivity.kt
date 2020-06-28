package com.candidate.android.dev.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.candidate.android.dev.myapplication.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar?.hide()
    }
}
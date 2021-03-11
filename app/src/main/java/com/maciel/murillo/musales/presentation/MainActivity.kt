package com.maciel.murillo.musales.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maciel.murillo.musales.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
    }
}
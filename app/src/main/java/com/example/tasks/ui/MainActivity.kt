package com.example.tasks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.tasks.R
import com.google.android.material.elevation.SurfaceColors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Matching color of actionBar and StatusBar
        val color = SurfaceColors.SURFACE_2.getColor(this)
        window.statusBarColor = color
    }


}
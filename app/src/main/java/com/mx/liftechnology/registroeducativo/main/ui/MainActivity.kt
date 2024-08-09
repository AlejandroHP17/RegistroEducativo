package com.mx.liftechnology.registroeducativo.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mx.liftechnology.registroeducativo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }

}
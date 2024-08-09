package com.mx.liftechnology.registroeducativo.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mx.liftechnology.registroeducativo.databinding.ActivityMainBinding
import com.mx.liftechnology.registroeducativo.model.di.menuModule
import com.mx.liftechnology.registroeducativo.model.di.studentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(menuModule, studentModule)
        }
    }

}
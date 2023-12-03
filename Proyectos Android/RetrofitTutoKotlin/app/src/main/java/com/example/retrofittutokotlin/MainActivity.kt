package com.example.retrofittutokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofittutokotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}
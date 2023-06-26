package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.viewpager2.widget.WindowInsetsApplier
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomNavigationMediator: BottomNavigationViewMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        // fix the broken `WindowInsets` dispatch of old API versions (< 30)
        WindowInsetsApplier.install(binding.viewpager)
        binding.viewpager.isUserInputEnabled = false
        bottomNavigationMediator =
            BottomNavigationViewMediator(binding.bottomNavView, binding.viewpager)
        bottomNavigationMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationMediator.detach()
    }
}
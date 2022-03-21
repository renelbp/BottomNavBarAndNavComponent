package com.example.multimediaenandroidstudio

import android.app.usage.ExternalStorageStats
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.multimediaenandroidstudio.databinding.ActivityMainBinding
import android.os.Handler
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragment)
        val appBarConfig = AppBarConfiguration(setOf(R.id.mediaPlayerFragment, R.id.internalStorageFragment,R.id.externalStorageFragment))
        binding.bottomNavigationView.setupWithNavController(navController)


    }




}


package com.example.dadjokes.framework.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.dadjokes.R
import com.example.dadjokes.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding ?= null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setToolbar()
        setupNavigationComponent()
    }


    @SuppressLint("ResourceType")
    private fun setToolbar()
    {
        val toolbar : Toolbar = findViewById(R.id.toolbar_atimi)
        setSupportActionBar(toolbar)
        toolbar.background = getDrawable(R.color.colorAccent)
        val title : TextView = findViewById(R.id.toolbar_title_tv)
        title.setTextColor(Color.parseColor(getString(R.color.colorAccent)))
    }


    private fun setupNavigationComponent()
    {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_atimi)
        setSupportActionBar(toolbar)
        val navHostFragment : NavHostFragment = joke_nav_host_fragment as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.joke_nav)
        val navController = navHostFragment.navController
        navGraph.startDestination = R.id.randomJokeFragment
        navController.graph = navGraph
    }

    override fun onSupportNavigateUp(): Boolean {
        return (Navigation.findNavController(this, R.id.joke_nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.example.weather.mainModel.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.weather.BR

import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.mainModel.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        val vm: MainViewModel by viewModels()

        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)


    }

    private fun setupObservers() {

        binding.viewModel?.let {
            it.getSnackbarMsg().observe(this){resMsg ->

                Snackbar.make(binding.root, resMsg, Snackbar.LENGTH_SHORT).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch{
            binding.viewModel?.getWeatherAndForecast(19.4109, -99.1260, "e06d8ec03ef322ebc4be1d43ca3cc9a1", "metric", "en")
        }
    }
}
package com.example.weather.mainModel.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.R
import com.example.weather.common.entities.WeatherForecastEntity
import com.example.weather.mainModel.model.MainRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repository = MainRepository()

    private val result = MutableLiveData<WeatherForecastEntity>()
    fun getResult(): LiveData<WeatherForecastEntity> = result

    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg

    private val loaded = MutableLiveData<Boolean>()
    fun isLoad() = loaded

    suspend fun getWeatherAndForecast(lat: Double, lon: Double, appId: String,
                                      units: String, lang: String){
        viewModelScope.launch {
            try {
                loaded.value = false
                val resultServer = repository.getWeatherAndForecast(lat, lon, appId, units, lang)
                Log.e("result", resultServer.toString())
                result.value = resultServer

                Log.e("ViewModel", resultServer.toString())
            } catch (e: Exception) {
                snackbarMsg.value = R.string.main_error_server
            } finally {

                loaded.value = true
            }

        }
    }


}
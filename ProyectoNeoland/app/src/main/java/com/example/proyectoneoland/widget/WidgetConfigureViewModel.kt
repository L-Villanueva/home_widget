package com.example.proyectoneoland.widget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.proyectoneoland.data.App
import com.example.proyectoneoland.data.Devices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WidgetConfigureViewModel (application: Application) : AndroidViewModel(application) {

    suspend fun loadDevices(): List<Devices> = withContext(Dispatchers.IO) {

        return@withContext App.getDatabase(getApplication()).devicesDao().getAll()
    }
}
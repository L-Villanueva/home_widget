package com.example.proyectoneoland

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.proyectoneoland.data.App
import com.example.proyectoneoland.data.Devices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivityViewModel (application: Application) : AndroidViewModel(application){

    suspend fun loadDevices(): List<Devices> = withContext(Dispatchers.IO){

        return@withContext App.getDatabase(getApplication()).devicesDao().getAll()
    }
    suspend fun deleteDevice(device: Devices)= withContext(Dispatchers.IO){

        App.getDatabase(getApplication()).devicesDao().delete(device)
    }

    suspend fun updateDevice(device: Devices)= withContext(Dispatchers.IO){

        App.getDatabase(getApplication()).devicesDao().update(device)
    }
}
package com.example.proyectoneoland.list_screen.fragment_add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.proyectoneoland.data.App
import com.example.proyectoneoland.data.Devices
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class FragmentAddViewModel(application: Application) : AndroidViewModel(application){

    suspend fun addDevice(device: Devices) = withContext (IO) {

        App.getDatabase(getApplication()).devicesDao().insert(device)
    }

    suspend fun modifyDevice(device: Devices) = withContext (IO) {

        App.getDatabase(getApplication()).devicesDao().update(device)
    }
}
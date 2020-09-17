package com.example.proyectoneoland.list_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.proyectoneoland.data.App
import com.example.proyectoneoland.data.Devices
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListActivityViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun loadDevices(): List<Devices> = withContext(Dispatchers.IO) {
        val devices = mutableListOf<Devices>()
        App.getDatabase(getApplication()).devicesDao().getAll().forEach {
            if (it.owner.equals(FirebaseAuth.getInstance().currentUser?.email)) {
                devices.add(it)
            }
        }
        return@withContext devices
    }
}
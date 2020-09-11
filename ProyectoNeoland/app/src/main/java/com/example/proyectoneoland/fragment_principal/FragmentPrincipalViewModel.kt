package com.example.proyectoneoland.fragment_principal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.proyectoneoland.data.App
import com.example.proyectoneoland.data.Devices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FragmentPrincipalViewModel (application: Application) : AndroidViewModel(application){

    suspend fun cargarBootcamp(): LiveData<List<Devices>> = withContext(Dispatchers.IO){

        return@withContext App.getDatabase(getApplication()).devicesDao().getAllLive()
    }
}
package com.example.proyectoneoland.list_screen.fragment_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.proyectoneoland.data.App
import com.example.proyectoneoland.data.Brand
import com.example.proyectoneoland.data.Devices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class FragmentListViewModel (application: Application) : AndroidViewModel(application){

    suspend fun LoadDevices(): LiveData<List<Devices>> = withContext(Dispatchers.IO){

        return@withContext App.getDatabase(getApplication()).devicesDao().getAllLive()
    }

    suspend fun LoadBrand(brand: Brand): List<Devices> = withContext(IO){

        return@withContext App.getDatabase(getApplication()).devicesDao().getBrand(brand)
    }
}
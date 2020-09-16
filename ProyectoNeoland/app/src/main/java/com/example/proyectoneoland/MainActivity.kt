package com.example.proyectoneoland

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.ListActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

interface DeleteInterface{
    fun delete(device: Devices)
}
class MainActivity : AppCompatActivity() , DeleteInterface{

    lateinit var model: MainActivityViewModel
    private val adapter = MainActivityAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRecyclerView()

        //funcion para salir de la sesion TODO agregarlo al boton correcto
        /*buttonEliminar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }*/
        floatingActionButton.setOnClickListener {
            showList()
        }

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainActivityViewModel::class.java
            )

        updateAdapter()
        buttonEliminar.setOnClickListener {
            adapter.showDelete()
        }
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

    private fun createRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }

    override fun delete(device: Devices) {
        CoroutineScope(Main).launch {
            model.deleteDevice(device)
        }
        updateAdapter()
    }
    private fun updateAdapter(){
        CoroutineScope(Main).launch {
            val devices = mutableListOf<Devices>()
            model.LoadDevices().forEach {
                if (it.owner.equals(FirebaseAuth.getInstance().currentUser?.email)){
                    devices.add(it)
                }
            }
            if (devices.isNullOrEmpty()){
                showList()
            } else {
                adapter.updateDevices(devices)
            }
            //model.LoadDevices().observe(this@MainActivity, Observer { devices -> adapter.updateDevices(devices) })

        }
    }

    private fun showList() {
        val mainIntent = Intent(this, ListActivity::class.java)
        startActivity(mainIntent)

    }
}
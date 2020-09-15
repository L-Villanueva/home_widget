package com.example.proyectoneoland

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoneoland.list_screen.ListActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var model: MainActivityViewModel
    private val adapter = MainActivityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRecyclerView()

        //funcion para salir de la sesion TODO agregarlo al boton correcto
        buttonEliminar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        floatingActionButton.setOnClickListener {
            val mainIntent = Intent(this, ListActivity::class.java)
            startActivity(mainIntent)
        }


        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainActivityViewModel::class.java
            )

        CoroutineScope(Dispatchers.Main).launch {
            model.LoadDevices().value?.let { devices -> adapter.updateDevices(devices) }
            model.LoadDevices().observe(this@MainActivity, Observer { devices -> adapter.updateDevices(devices) })

        }
    }

    private fun createRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
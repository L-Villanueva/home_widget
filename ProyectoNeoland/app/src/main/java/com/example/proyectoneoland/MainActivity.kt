package com.example.proyectoneoland

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
            val mainIntent = Intent(this, List::class.java)
            startActivity(mainIntent)
        }


        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainActivityViewModel::class.java
            )
        CoroutineScope(Dispatchers.Main).launch {
            model.cargarBootcamp().value?.let { devices -> adapter.updateDevices(devices) }
            model.cargarBootcamp().observe(
                this@MainActivity,
                Observer { bootcamps -> adapter.updateDevices(bootcamps) })

        }
    }

    private fun createRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
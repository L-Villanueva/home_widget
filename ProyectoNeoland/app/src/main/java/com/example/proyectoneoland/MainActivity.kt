package com.example.proyectoneoland

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.ListActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

interface DeleteInterface{
    fun delete(device: Devices)
    fun getDelete(): ImageView
}
class MainActivity : AppCompatActivity() , DeleteInterface{

    //intendo de hacer que se eliminen los items a traves de un drag
    private val onDragListener = View.OnDragListener { view, dragEvent ->
        (view as? ImageView)?.let {
            when (dragEvent.action) {


                // Once the color is dropped on the area, we want to paint it in that color.
                DragEvent.ACTION_DROP -> {
                    // Read color data from the clip data and apply it to the card view background.
                    val item: ClipData.Item = dragEvent.clipData.getItemAt(0)

                    return@OnDragListener true
                }
                else -> return@OnDragListener false
            }
        }
        false
    }

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
        buttonDeleteMain.setOnClickListener {
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

    override fun getDelete(): ImageView {
        return buttonDeleteMain
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
                finish()

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
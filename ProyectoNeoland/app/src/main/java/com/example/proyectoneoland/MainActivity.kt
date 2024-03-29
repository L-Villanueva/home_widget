package com.example.proyectoneoland

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.DragEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.ListActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


interface MainInterface{
    fun delete(device: Devices)
    fun modify(device: Devices)

}

interface DataChangeListener{
    fun dataChanged()
}
class MainActivity : AppCompatActivity() , MainInterface, DataChangeListener{

    private lateinit var model: MainActivityViewModel
    private val adapter = MainActivityAdapter(this)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //
        // creo un menu personalizado para agregar un boton de logout
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // logica boton logout
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.mybutton) {
            showAlert()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRecyclerView()


        floatingActionButton.setOnClickListener {
            showList()
        }

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            MainActivityViewModel::class.java
        )

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
            updateAdapter()
        }
    }

    override fun modify(device: Devices) {
        CoroutineScope(IO).launch {
            device.toggle = !device.toggle
            model.updateDevice(device)
            updateAdapter()
        }
    }

    private fun updateAdapter(){
        CoroutineScope(Main).launch {
            //cuando se actualiza la lista del recycler view me aseguro que solo aparezcan los dispositivos de la cuenta que esta activa,
            //como los dispositivos de prueba no tienen dueño, no se agregan a la lsta y no salen en pantalla
            val devices = mutableListOf<Devices>()
            model.loadDevices().forEach {
                if (it.owner.equals(FirebaseAuth.getInstance().currentUser?.email)){
                    devices.add(it)
                }
            }
            //si el usuario no tiene dispositivos, se viaja a la proxima pantalla
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

    override fun dataChanged() {
        CoroutineScope(Main).launch {
            adapter.updateDevices(model.loadDevices())
        }
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage(getString(R.string.sing_out_message))
            setPositiveButton(getString(R.string.accept)) { dialog, _ ->
                FirebaseAuth.getInstance().signOut()
                dialog.dismiss()
                onBackPressed() }

            setNegativeButton(getString(R.string.cancel), null)
            create()
        }
        builder.show()
    }
}
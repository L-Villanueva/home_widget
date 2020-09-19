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
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

interface DeleteInterface{
    fun delete(device: Devices)

}

interface DataChangeListener{
    fun dataChanged()
}
class MainActivity : AppCompatActivity() , DeleteInterface, DataChangeListener{

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

    private lateinit var model: MainActivityViewModel
    private val adapter = MainActivityAdapter(this)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // R.my_menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/my_menu directory.
        // If you don't have res/my_menu, just create a directory named "my_menu" inside res
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // handle button activities
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

        //funcion para salir de la sesion TODO agregarlo al boton correcto
        /*buttonEliminar.setOnClickListener {

        }*/

        floatingActionButton.setOnClickListener {
            showList()
        }

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainActivityViewModel::class.java)

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

    private fun updateAdapter(){
        CoroutineScope(Main).launch {
            val devices = mutableListOf<Devices>()
            model.loadDevices().forEach {
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

            setNegativeButton(getString(R.string.cancel),null)
            create()
        }
        builder.show()
    }
}
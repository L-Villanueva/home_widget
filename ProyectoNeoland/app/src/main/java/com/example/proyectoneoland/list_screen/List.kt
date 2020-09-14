package com.example.proyectoneoland.list_screen

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Devices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_screen.*

interface ListInterface{
    fun clickList(device: Devices)
}

class List: AppCompatActivity(),ListInterface {

  
    private val adapter = ListAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.list_screen)
        
        createRecyclerView()


        val list = listOf("Xiaomi", "Yeelight", "Philips")

        dropdown_menu.setAdapter(ArrayAdapter(this, DEFAULT_BUFFER_SIZE,list))
    }

    private fun createRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun clickList(device: Devices) {

    }
}
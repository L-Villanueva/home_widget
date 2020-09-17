package com.example.proyectoneoland.list_screen

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.component1
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoneoland.MainActivityViewModel
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.fragment_add.FragmentAdd
import com.example.proyectoneoland.list_screen.fragment_list.FragmentList
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


interface ListInterface{
    fun clickList(device: Devices)
}

interface BackPressed{
    fun backPressed()
}

//


class ListActivity: AppCompatActivity(), ListInterface,BackPressed{

    private var fragment: FragmentAdd? = null
    private lateinit var model: ListActivityViewModel

    override fun onBackPressed() {
        CoroutineScope(Main).launch {
            if (!model.loadDevices().isNullOrEmpty()){
                super.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            ListActivityViewModel::class.java)

        setContentView(R.layout.activity_list)

        ArrayAdapter.createFromResource(this, R.array.Brands, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            dropdown_menu.adapter = adapter
        }

        dropdown_menu.onItemSelectedListener = object : OnItemSelectedListener {


            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position != 0){
                val selectedItem = parent.getItemAtPosition(position).toString()
                    materialCardView2.visibility = View.VISIBLE
                    showFragment(FragmentList.setArgument(selectedItem, this@ListActivity))
                }

            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        floatingActionButtonList.setOnClickListener {
            CoroutineScope(Main).launch {
                fragment?.let {
                    it.createDevice()
                }
            }
        }
    }



    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.disallowAddToBackStack()
        fragmentTransaction.commit()
    }

    override fun clickList(device: Devices) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragment = FragmentAdd.setArgument(device, this)
        fragment?.let {
            fragmentTransaction.replace(R.id.frameLayout,it)
        }
        fragmentTransaction.commitNow()

    }

    override fun backPressed() {
        onBackPressed()
    }
}
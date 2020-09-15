package com.example.proyectoneoland.list_screen

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.fragment_add.FragmentAdd
import com.example.proyectoneoland.list_screen.fragment_list.FragmentList
import kotlinx.android.synthetic.main.activity_list.*


interface ListInterface{
    fun clickList(device: Devices)
}
//


class ListActivity: AppCompatActivity(), ListInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)



        ArrayAdapter.createFromResource(this, R.array.Brands, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            dropdown_menu.adapter = adapter
        }

        dropdown_menu.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()

                showFragment(FragmentList.setArgument( selectedItem,this@ListActivity))

            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }



    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    override fun clickList(device: Devices) {
        showFragment(FragmentAdd.getFragment(device))
    }
}
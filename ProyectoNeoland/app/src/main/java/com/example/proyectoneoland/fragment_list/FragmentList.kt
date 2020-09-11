package com.example.proyectoneoland.fragment_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.proyectoneoland.R
import kotlinx.android.synthetic.main.fragment_list.*

class FragmentList: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val list = listOf("Xiaomi", "Yeelight", "Philips")

        dropdown_menu.setAdapter(context?.let { ArrayAdapter(it, DEFAULT_BUFFER_SIZE, list) })
    }


}
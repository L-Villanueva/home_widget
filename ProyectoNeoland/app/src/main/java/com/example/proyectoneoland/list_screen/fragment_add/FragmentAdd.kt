package com.example.proyectoneoland.list_screen.fragment_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.fragment_list.FragmentList

class FragmentAdd: Fragment() {

    companion object {

        val clave_2 = "CLAVE2"

        fun getFragment(device: Devices): FragmentList {
            FragmentList().apply {
                arguments?.putString(clave_2, device)
            }
            return FragmentList()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
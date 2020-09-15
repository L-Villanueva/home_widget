package com.example.proyectoneoland.list_screen.fragment_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Devices
import kotlinx.android.synthetic.main.fragment_add.*

class FragmentAdd: Fragment() {

    private lateinit var model: FragmentAddViewModel

    companion object {

        val clave_2 = "CLAVE2"

        fun getFragment(device: Devices): FragmentAdd {
            FragmentAdd().apply {
                arguments?.putSerializable(clave_2, device)
            }

            return FragmentAdd()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val device = arguments?.getSerializable(clave_2) as Devices
        defaultName.text = device.name
        itemImage.setImageResource(device.pictures.image)

        activity?.let {
            model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(it.application)).get(
                FragmentAddViewModel::class.java)
        }


    }
}
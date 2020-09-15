package com.example.proyectoneoland.list_screen.fragment_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.ListInterface
import com.example.proyectoneoland.list_screen.fragment_list.FragmentList
import kotlinx.android.synthetic.main.fragment_add.*

class FragmentAdd: Fragment() {

    private lateinit var model: FragmentAddViewModel

    companion object {

        val clave_2 = "CLAVE2"

        fun getFragment(): FragmentAdd {
            return FragmentAdd()
        }

        fun setArgument(device: Devices): FragmentAdd {
            val fragment = FragmentAdd.getFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(clave_2, device)
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val device = arguments?.getSerializable(clave_2) as Devices
        defaultName.text = device.defaultName
        itemImage.setImageResource(device.pictures.buttonOff)

        activity?.let {
            model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(it.application)).get(
                FragmentAddViewModel::class.java)
        }


    }
}
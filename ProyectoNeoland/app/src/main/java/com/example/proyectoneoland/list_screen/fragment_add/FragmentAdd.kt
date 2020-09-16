package com.example.proyectoneoland.list_screen.fragment_add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoneoland.MainActivity
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.BackPressed

import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.fragment_add.*



class FragmentAdd(val listener : BackPressed): Fragment() {

    private lateinit var model: FragmentAddViewModel

    companion object {
        private lateinit var device: Devices

        const val clave_2 = "CLAVE2"

        private fun getFragment(listener: BackPressed): FragmentAdd {
            return FragmentAdd(listener)
        }

        fun setArgument(device: Devices,listener: BackPressed): FragmentAdd {
            val fragment = getFragment(listener)
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

        device = arguments?.getSerializable(clave_2) as Devices
        defaultName.text = device.defaultName
        itemImage.setImageResource(device.pictures.buttonOff)

        activity?.let {
            model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(it.application)).get(
                FragmentAddViewModel::class.java
            )
        }
    }

    suspend fun createDevice() {
        if (!editNameAdd.text.isNullOrEmpty()) {
            val newDevice = Devices(
                device.defaultName,
                editNameAdd.text.toString(),
                FirebaseAuth.getInstance().currentUser?.email,
                device.pictures,
                device.widgets,
                device.toggle,
                device.type,
                device.brand)
                onDestroy()
            model.addDevice(newDevice)
            listener.backPressed()
        } else {
            Toast.makeText(activity?.applicationContext,"Error agregando", Toast.LENGTH_LONG).show()
        }
    }
}
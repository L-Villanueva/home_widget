package com.example.proyectoneoland.fragment_principal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoneoland.R
import com.example.proyectoneoland.fragment_list.FragmentList
import kotlinx.android.synthetic.main.fragment_principal.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentPrincipal: Fragment(){

    companion object {
        fun getFragment(): FragmentPrincipal {
            return FragmentPrincipal()
        }
    }

    lateinit var model :FragmentPrincipalViewModel
    private val adapter = FragmentAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView()

        activity?.let {
            model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(it.application)).get(
                FragmentPrincipalViewModel::class.java)
            CoroutineScope(Dispatchers.Main).launch {
                model.cargarBootcamp().value?.let { devices -> adapter.updateDevices(devices) }
                model.cargarBootcamp().observe(this@FragmentPrincipal, Observer { bootcamps -> adapter.updateDevices(bootcamps) })
            }
        }
    }
    private fun createRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}
package com.example.proyectoneoland.list_screen.fragment_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Brand
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.ListInterface
import kotlinx.android.synthetic.main.list_recycler.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

interface FragmentInterface{
    fun onClick(device: Devices)
}

class FragmentList(private var listener: ListInterface): Fragment(), FragmentInterface {

    private lateinit var adapter: ListAdapter
    private lateinit var model: FragmentListViewModel
    private lateinit var brandSelected: String

    companion object {

        const val clave_1 = "CLAVE1"


        private fun getFragment(listener: ListInterface): FragmentList {
            return FragmentList(listener)
        }
        fun setArgument(brand: String, listener: ListInterface): FragmentList{
            val fragment = getFragment(listener)
            fragment.arguments = Bundle().apply {
                putString(clave_1, brand)
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListAdapter(this)
        createRecyclerView()



        activity?.let { activity ->
            model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(activity.application)).get(
                FragmentListViewModel::class.java)

        }
        CoroutineScope(Main).launch {
            when (arguments?.getString(clave_1).toString().toUpperCase()) {

                Brand.YEELIGHT.name -> { adapter.updateDevices(loadBrand(Brand.YEELIGHT)) }
                Brand.PHILIPS.name -> { adapter.updateDevices(loadBrand(Brand.PHILIPS)) }
                Brand.XIAOMI.name -> { adapter.updateDevices(loadBrand(Brand.XIAOMI)) }
            }

        }
    }

    private suspend fun loadBrand(brand: Brand): List<Devices> {
        return model.loadBrand(brand).filter { it.owner.isNullOrEmpty() }
    }

    private fun createRecyclerView() {
        recyclerViewList.layoutManager = LinearLayoutManager(context)
        recyclerViewList.adapter = adapter
    }

    override fun onClick(device: Devices) {
        listener.clickList(device)
    }
}
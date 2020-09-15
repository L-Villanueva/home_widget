package com.example.proyectoneoland.list_screen.fragment_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoneoland.MainActivityViewModel
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Brand
import com.example.proyectoneoland.data.DeviceType
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.list_screen.ListInterface
import kotlinx.android.synthetic.main.list_recycler.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

interface FragmentInterface{
    fun onClick(device: Devices)
}

class FragmentList(var listener: ListInterface): Fragment(), FragmentInterface {

    private lateinit var adapter: ListAdapter
    private lateinit var model: FragmentListViewModel


    companion object {

        const val clave_1 = "CLAVE1"

        fun getFragment(brand: String,listener: ListInterface): FragmentList {
            FragmentList(listener).apply {
                arguments = Bundle().apply {
                    putString(clave_1, brand)
                }

            }
            return FragmentList(listener)
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
            CoroutineScope(Main).launch {
                if (arguments?.getString(clave_1)?.capitalize().equals(Brand.YEELIGHT.name.capitalize())){
                    adapter.updateDevices(LoadBrand(Brand.YEELIGHT))
                } else if (arguments?.getString(clave_1)?.capitalize().equals(Brand.PHILIPS.name.capitalize())){
                    adapter.updateDevices(LoadBrand(Brand.PHILIPS))
                } else if (arguments?.getString(clave_1)?.capitalize().equals(Brand.XIAOMI.name.capitalize())){
                    adapter.updateDevices(LoadBrand(Brand.XIAOMI))
                }

            }
        }
    }

    private suspend fun LoadBrand(brand: Brand): List<Devices> {
        return model.LoadBrand(brand).filter { it.owner.isNullOrEmpty() }
    }

    private fun createRecyclerView() {
        recyclerViewList.layoutManager = LinearLayoutManager(context)
        recyclerViewList.adapter = adapter
    }

    private fun stringToBrand(brand: String): Brand{
        if (brand.capitalize() == Brand.YEELIGHT.name) {
            return Brand.YEELIGHT
        } else if (brand.capitalize() == (Brand.XIAOMI.name)) {
            return Brand.XIAOMI
        } else {
            return Brand.PHILIPS
        }
    }

    override fun onClick(device: Devices) {
        listener.clickList(device)
    }
}
package com.example.proyectoneoland.list_screen.fragment_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Brand
import com.example.proyectoneoland.data.DeviceType
import kotlinx.android.synthetic.main.list_recycler.*

class FragmentList: Fragment() {

    lateinit var adapter: ListAdapter

    companion object {

        val clave_1 = "CLAVE1"

        fun getFragment(brand: String): FragmentList {
            FragmentList().apply {
                arguments?.putString(clave_1, brand)
            }
            return FragmentList()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListAdapter()
        when (arguments?.getString(clave_1)?.let { stringToBrand(it) }) {
            Brand.YEELIGHT -> adapter.updateDevices()
            Brand.PHILIPS -> adapter.updateDevices()
            Brand.XIAOMI -> adapter.updateDevices()
        }
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
}
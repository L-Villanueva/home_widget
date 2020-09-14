package com.example.proyectoneoland.fragment_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoneoland.R
import com.example.proyectoneoland.fragment_principal.FragmentAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_principal.*

class FragmentList: Fragment() {

    companion object {
        fun getFragment(): FragmentList {
            return FragmentList()
        }
    }
    private val adapter = FragmentAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView()


        val list = listOf("Xiaomi", "Yeelight", "Philips")

        dropdown_menu.setAdapter(context?.let { ArrayAdapter(it, DEFAULT_BUFFER_SIZE, list) })
    }


    private fun createRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}
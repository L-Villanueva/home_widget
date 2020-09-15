package com.example.proyectoneoland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoneoland.data.Devices


class MainActivityAdapter: RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    private var list = listOf<Devices>()

    class ViewHolder(var root: View, var image: ImageView, var name: TextView) : RecyclerView.ViewHolder(root)

    fun updateDevices(devices : List<Devices>){
        this.list = devices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device_item, parent, false)
        val image = view.findViewById<ImageView>(R.id.imageMain)
        val name = view.findViewById<TextView>(R.id.nameMain)

        return ViewHolder(view, image, name)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(list[position].pictures.buttonOff)
        holder.name.text = list[position].defaultName

    }
}
package com.example.proyectoneoland.list_screen.fragment_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoneoland.data.Devices
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Brand
import com.example.proyectoneoland.list_screen.ListInterface


class ListAdapter(var listener: FragmentInterface): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var list = listOf<Devices>()

    class ViewHolder(var root: View, var image: ImageView, var name: TextView) : RecyclerView.ViewHolder(root)

    fun updateDevices(devices : List<Devices>){
        this.list = devices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        val image = view.findViewById<ImageView>(R.id.imageList)
        val name = view.findViewById<TextView>(R.id.nameList)

        return ViewHolder(view, image, name)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.root.setOnClickListener {
            listener.onClick(list[position])
        }
        holder.image.setImageResource(list[position].pictures.buttonOff)
        holder.name.text = list[position].defaultName

    }
}
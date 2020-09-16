package com.example.proyectoneoland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoneoland.data.Devices
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivityAdapter(private var listener: DeleteInterface): RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    private var list = listOf<Devices>()
    private var delete = false

    class ViewHolder(var root: View, var image: ImageView, var name: TextView, var delete: FloatingActionButton) : RecyclerView.ViewHolder(root)

    fun updateDevices(devices : List<Devices>){
        this.list = devices
        delete = false
        notifyDataSetChanged()
    }

    fun showDelete(){
        delete = true
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device_item, parent, false)
        val image = view.findViewById<ImageView>(R.id.imageMain)
        val name = view.findViewById<TextView>(R.id.nameMain)
        val delete = view.findViewById<FloatingActionButton>(R.id.deleteFloatingAction)

        return ViewHolder(view, image, name, delete)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list[position].toggle){
            holder.image.setImageResource(list[position].pictures.buttonOn)
        } else {
            holder.image.setImageResource(list[position].pictures.buttonOff)
        }
        holder.name.text = list[position].name
        if (list[position].toggle) {
            holder.root.setOnClickListener {
                list[position].toggle = false
                notifyDataSetChanged()
            }
        } else {
            holder.root.setOnClickListener {
                list[position].toggle = true
                notifyDataSetChanged()
            }
        }

        if (delete) {
            holder.delete.visibility = View.VISIBLE
            holder.delete.setOnClickListener {
                listener.delete(list[position])
            }
        } else {
            holder.delete.visibility = View.GONE
        }
    }

}
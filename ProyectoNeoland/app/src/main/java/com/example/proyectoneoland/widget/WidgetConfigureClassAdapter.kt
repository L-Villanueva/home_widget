package com.example.proyectoneoland.widget

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoneoland.MainActivityAdapter
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Devices
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WidgetConfigureClassAdapter(var listener: Configure): RecyclerView.Adapter<WidgetConfigureClassAdapter.ViewHolder>()  {

    private var list = listOf<Devices>()

    fun updateDevices(devices : List<Devices>){
        this.list = devices
        notifyDataSetChanged()
    }

    class ViewHolder(var root: View, var image: ImageView, var name: TextView) : RecyclerView.ViewHolder(root)

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
        if (list[position].toggle){
            holder.image.setImageResource(list[position].pictures.buttonOn)
        } else {
            holder.image.setImageResource(list[position].pictures.buttonOff)
        }
        holder.name.text = list[position].name
        holder.root.setOnClickListener {

            val builder = AlertDialog.Builder(holder.root.context)
            builder.apply {
                setMessage("Select theme for widget")
                setNegativeButton("Light", DialogInterface.OnClickListener { dialog, _ ->
                    listener.confirmConfigure(list[position], true)
                    dialog.dismiss()
                })
                setPositiveButton("Dark", DialogInterface.OnClickListener { dialog, _ ->
                    listener.confirmConfigure(list[position], false)
                    dialog.dismiss()
                })
                create()
            }
            builder.show()
        }
    }
}
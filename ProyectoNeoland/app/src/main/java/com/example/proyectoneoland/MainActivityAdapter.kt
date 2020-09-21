package com.example.proyectoneoland

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.os.Build
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

    //intendo de hacer que se eliminen los items a traves de un drag
    private val onLongClickListener = View.OnLongClickListener { view: View ->
        (view as? View)?.let {
            val item = ClipData.Item(it.tag as? CharSequence)
            val dragData = ClipData(
                it.tag as? CharSequence,
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                item
            )
            val myShadow = View.DragShadowBuilder(it)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.startDragAndDrop(dragData, myShadow, null, 0)
            } else {
                it.startDrag(dragData, myShadow, null, 0)
            }

            true
        }
        false
    }

    class ViewHolder(var root: View, var image: ImageView, var name: TextView, var delete: ImageView) : RecyclerView.ViewHolder(root)

    fun updateDevices(devices : List<Devices>){
        this.list = devices
        delete = false
        notifyDataSetChanged()
    }

    fun showDelete(){
        delete = !delete
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device_item, parent, false)
        val image = view.findViewById<ImageView>(R.id.imageMain)
        val name = view.findViewById<TextView>(R.id.nameMain)
        val delete = view.findViewById<ImageView>(R.id.deleteFloatingAction)

        return ViewHolder(view, image, name, delete)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // si la bombilla esta encendida se muestra una imagen, y si esta apagada otra
        if (list[position].toggle){
            holder.image.setImageResource(list[position].pictures.buttonOn)
        } else {
            holder.image.setImageResource(list[position].pictures.buttonOff)
        }
        holder.name.text = list[position].name

        holder.root.setOnClickListener {
            list[position].toggle = !list[position].toggle
            notifyDataSetChanged()
        }

        //si la variable delete es true se muestra el boton para eliminar y se le agrega un listener que muestra un alert
        if (delete) {
            holder.delete.visibility = View.VISIBLE
            holder.delete.setOnClickListener {

                showAlert(holder.root.context, position)
            }
        } else {
            holder.delete.visibility = View.GONE
        }


    }
    private fun showAlert(context: Context, position: Int) {
        
        val builder = AlertDialog.Builder(context)
        builder.apply {

            setMessage(context.getString(R.string.delete_message))
            setPositiveButton(context.getString(R.string.accept)) { dialog, _ ->
                //si el usuario esta seguro de querer eliminar se llama a una funcion del listener que accede a la base de datos,elimina y luego notifica el cambio
                listener.delete(list[position])
                dialog.dismiss()
                notifyDataSetChanged() }

            setNegativeButton(context.getString(R.string.cancel),null)
            create()
        }
        builder.show()
    }

}
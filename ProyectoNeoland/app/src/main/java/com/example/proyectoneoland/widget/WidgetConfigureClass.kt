package com.example.proyectoneoland.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.Devices
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.widget_configure_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


interface Configure {
    fun confirmConfigure(device: Devices)
}

class WidgetConfigureClass: AppCompatActivity(), Configure {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var model : WidgetConfigureViewModel
    private lateinit var adapter : WidgetConfigureClassAdapter
    val SHARED_PREFS = "prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.widget_configure_activity)

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            WidgetConfigureViewModel::class.java
        )

        adapter = WidgetConfigureClassAdapter(this)

        val extras: Bundle? = intent.extras

        if (extras != null) { appWidgetId = extras.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )
        }
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_CANCELED, resultValue)

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }

        createRecyclerView()
        updateAdapter()

    }



    private fun createRecyclerView() {
        configureRecyclerView.layoutManager = GridLayoutManager(this, 3)
        configureRecyclerView.adapter = adapter
    }

    private fun updateAdapter() {
        CoroutineScope(Main).launch {
            val devices = mutableListOf<Devices>()
            model.loadDevices().forEach {
                if (it.owner.equals(FirebaseAuth.getInstance().currentUser?.email)) {
                    devices.add(it)
                }
            }

            adapter.updateDevices(devices)

        }
    }

    override fun confirmConfigure(device: Devices) {
        val appWidgetManager=  AppWidgetManager.getInstance(this)
        appWidgetManager.updateAppWidget(
            appWidgetId, RemoteViews(
                this.packageName,
                R.layout.new_app_widget
            )
        )
        val prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(appWidgetId.toString(), device.uid)
        editor.apply()

        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, resultValue)

        val arrayWidgetId = IntArray(1)
        arrayWidgetId[0] = appWidgetId
        NewAppWidget().onUpdate(this,appWidgetManager, arrayWidgetId)
        finish()
    }
}
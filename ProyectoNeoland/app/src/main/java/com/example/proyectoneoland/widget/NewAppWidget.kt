package com.example.proyectoneoland.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.proyectoneoland.MainActivity
import com.example.proyectoneoland.R
import com.example.proyectoneoland.data.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    val SHARED_PREFS = "prefs"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {

            val prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

            val deviceId = prefs.getInt(appWidgetId.toString(), -1)

            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            CoroutineScope(IO).launch {
                withContext(IO) {

                    val device = App.getDatabase(context).devicesDao().getById(deviceId)
                    device?.let {
                        views.setImageViewResource(
                            R.id.appwidget_image,
                            it.widgets.widgetLightOff
                        )
                    }
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                    // Instruct the widget manager to update the widget
                }
            }


        }

    }


    override fun onEnabled(context: Context) {

        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


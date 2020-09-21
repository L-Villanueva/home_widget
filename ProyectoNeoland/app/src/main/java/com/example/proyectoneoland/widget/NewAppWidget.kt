package com.example.proyectoneoland.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
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
    val CLICKED = "buttonclicked"
    val clave2 = "clave2"

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
                        it.widgetTheme?.let {theme->
                            views.setTextViewText(R.id.name_widget, device.name)

                            if (it.toggle && theme){
                                views.setImageViewResource(
                                    R.id.appwidget_image,
                                    it.widgets.widgetLightOn
                                )
                            } else if(!it.toggle && theme){
                                views.setImageViewResource(
                                    R.id.appwidget_image,
                                    it.widgets.widgetLightOff
                                )
                            } else if (it.toggle && !theme){
                                views.setImageViewResource(
                                    R.id.appwidget_image,
                                    it.widgets.widgetDarkOn
                                )
                            } else if (!it.toggle && !theme){
                                views.setImageViewResource(
                                    R.id.appwidget_image,
                                    it.widgets.widgetDarkOff
                                )
                            }
                        }
                    }
                    views.setOnClickPendingIntent(
                        R.id.appwidget_image, getPendingSelfIntent(
                            context,
                            CLICKED,
                            appWidgetIds,
                            deviceId
                        )
                    )

                    appWidgetManager.updateAppWidget(appWidgetId, views)
                    // Instruct the widget manager to update the widget
                }
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (CLICKED == intent?.action){

            if (context != null) {
                CoroutineScope(IO).launch {
                    withContext(IO) {
                        val id = intent.getIntExtra(clave2, -1)
                        if (id != -1) {
                            val device = App.getDatabase(context).devicesDao()
                                .getById(id)

                            device.toggle = !device.toggle
                            App.getDatabase(context).devicesDao().update(device)
                            onUpdate(context)
                        }
                    }
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

    fun onUpdate(context: Context){
        val appWidgetManager = AppWidgetManager.getInstance(context)

        val thisAppWidgetComponentName = ComponentName(
            context.packageName, javaClass.name
        )
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            thisAppWidgetComponentName
        )

        onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun getPendingSelfIntent(context: Context?, action: String?,id: IntArray, deviceId: Int): PendingIntent? {

        val intent = Intent(context, javaClass)
        intent.putExtra(clave2, deviceId)

        intent.action = action

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}


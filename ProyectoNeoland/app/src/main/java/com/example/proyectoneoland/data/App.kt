package com.example.proyectoneoland.data

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proyectoneoland.R
import com.facebook.stetho.Stetho
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class App : Application() {

    companion object {
        private var db: AppDatabase? = null

        fun getDatabase(application: Application): AppDatabase {
            db?.let { return it }

            db = Room.databaseBuilder(application, AppDatabase::class.java, "main.db")
                .addCallback(getCallback())
                .build()
            return db as AppDatabase
        }
            private fun getCallback(): RoomDatabase.Callback {
                return object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.IO) {

                                val imagesLightbulb = Picture(R.drawable.ic_boton_lightbulb_encendido, R.drawable.ic_boton__lightbulb )
                                val imagesOutlet = Picture(R.drawable.ic_boton_outlet_encendido_nuevo, R.drawable.ic_boton_outlet)
                                val imagesSpeaker = Picture(R.drawable.ic_boton_speaker_encendido, R.drawable.ic_boton_speaker)

                                val widgetLightbulb = Widget(R.drawable.ic_widget_lightbulb_encencido_light,R.drawable.ic_widget_lightbulb_apagado_light,
                                    R.drawable.ic_widget_outlet_encendido_dark,R.drawable.ic_widget_outlet_apagado_dark)
                                val widgetOutlet = Widget(R.drawable.ic_widget_outlet_encendido_light,R.drawable.ic_widget_outlet_apagado_light,
                                R.drawable.ic_widget_outlet_encendido_dark,R.drawable.ic_widget_outlet_apagado_dark)
                                val widgetSpeaker = Widget(R.drawable.ic_widget_outlet_encendido_light,R.drawable.ic_widget_outlet_apagado_light,
                                R.drawable.ic_widget_outlet_encendido_dark,R.drawable.ic_widget_outlet_apagado_dark)

                                val devices = listOf(
                                    Devices("Bombilla LED", pictures = imagesLightbulb, widgets = widgetLightbulb, type = DeviceType.LIGHT,brand = Brand.XIAOMI),
                                    Devices("Bombilla LED", pictures = imagesLightbulb, widgets = widgetLightbulb, type = DeviceType.LIGHT,brand = Brand.YEELIGHT),
                                    Devices("Bombilla LED", pictures = imagesLightbulb, widgets = widgetLightbulb, type = DeviceType.LIGHT,brand = Brand.PHILIPS),
                                    Devices("Speaker", pictures = imagesSpeaker, widgets = widgetSpeaker, type = DeviceType.SPEAKER,brand = Brand.XIAOMI),
                                    Devices("Speaker", pictures = imagesSpeaker, widgets = widgetSpeaker, type = DeviceType.SPEAKER,brand = Brand.YEELIGHT),
                                    Devices("Speaker", pictures = imagesSpeaker, widgets = widgetSpeaker, type = DeviceType.SPEAKER,brand = Brand.PHILIPS),
                                    Devices("Outlet", pictures = imagesOutlet, widgets = widgetOutlet, type = DeviceType.OUTLET,brand = Brand.XIAOMI),
                                    Devices("Outlet", pictures = imagesOutlet, widgets = widgetOutlet, type = DeviceType.OUTLET,brand = Brand.YEELIGHT),
                                    Devices("Outlet", pictures = imagesOutlet, widgets = widgetOutlet, type = DeviceType.OUTLET,brand = Brand.PHILIPS),
                                    Devices("Bombilla RGB", pictures = imagesLightbulb, widgets = widgetLightbulb, type = DeviceType.LIGHT,brand = Brand.XIAOMI),
                                    Devices("Bombilla RGB", pictures = imagesLightbulb, widgets = widgetLightbulb, type = DeviceType.LIGHT,brand = Brand.YEELIGHT),
                                    Devices("Bombilla RGB", pictures = imagesLightbulb, widgets = widgetLightbulb, type = DeviceType.LIGHT,brand = Brand.PHILIPS)
                                )

                                App.db?.devicesDao()?.insertAll(devices.shuffled())
                                }
                            }
                        }
                    }
                }
            }


        override fun onCreate() {
            super.onCreate()
            Stetho.initializeWithDefaults(this)
            getDatabase(this)
        }
    }
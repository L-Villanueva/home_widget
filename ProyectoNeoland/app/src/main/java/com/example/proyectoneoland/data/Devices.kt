package com.example.proyectoneoland.data

import androidx.room.Entity

@Entity
data class Devices(
    var name: String,
    var owner: String,
    var pictures: Picture,
    var widgets: Widget,
    var toggle: Boolean,
    var type: DeviceType
) {
    enum class DeviceType {

        Light,
        Speaker,
        Outlet

    }
}

data class Picture(
    val pictureOn: Int,
    val pictureOff: Int
)

data class Widget(
    val widgetOn: Int,
    val widgetOff: Int
)
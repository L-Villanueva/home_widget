package com.example.proyectoneoland.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Devices(
    var name: String,
    var owner: String,
    @Embedded
    var pictures: Picture,
    @Embedded
    var widgets: Widget,
    @Embedded
    var toggle: Boolean,
    @Embedded
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
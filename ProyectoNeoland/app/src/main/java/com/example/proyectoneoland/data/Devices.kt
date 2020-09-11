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
    var toggle: Boolean,
    @Embedded
    var type: DeviceType
) {
    @PrimaryKey(autoGenerate = true)
    var uid = 0

}

data class Picture(
    var pictureOn: Int,
    var pictureOff: Int
)

data class Widget(
    var widgetOn: Int,
    var widgetOff: Int
)
enum class DeviceType (){

    Light,
    Speaker,
    Outlet
}
package com.example.proyectoneoland.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity
data class Devices(
    var name: String,
    var owner: String,
    @Embedded
    var pictures: Picture,
    @Embedded
    var widgets: Widget,
    var toggle: Boolean,
    var type: DeviceType,
    var brand: Brand

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
enum class DeviceType(val value: Int) {

    LIGHT(0),
    SPEAKER(1),
    OUTLET(2)

}

enum class Brand(val value: Int) {
    XIAOMI(0),
    PHILIPS(1),
    YEELIGHT(2)
}

class Converters {
    @TypeConverter
    fun toDevice(value: Int): DeviceType{
        return when (value){
            0 -> DeviceType.LIGHT
            1 -> DeviceType.SPEAKER
            else -> DeviceType.OUTLET
        }
    }

    @TypeConverter
    fun fromDevice(device: DeviceType): Int{
        return device.value
    }

    @TypeConverter
    fun toBrand(value: Int): Brand{
        return when (value){
            0 -> Brand.XIAOMI
            1 -> Brand.PHILIPS
            else -> Brand.YEELIGHT
        }
    }

    @TypeConverter
    fun fromBrand(brand: Brand): Int{
        return brand.value
    }
}
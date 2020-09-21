package com.example.proyectoneoland.data

import android.media.Image
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable


@Entity
@Serializable

data class Devices(

    var defaultName: String,
    var name: String? = null,
    var owner: String? = null,
    @Embedded
    var pictures: Picture,
    @Embedded
    var widgets: Widget,
    var toggle: Boolean = false,
    var type: DeviceType,
    var brand: Brand,
    var widgetTheme: Boolean? = null

) : java.io.Serializable {
    @PrimaryKey(autoGenerate = true)
    var uid = 0

}
@Serializable
data class Picture(
    var buttonOn: Int,
    var buttonOff: Int,
)
@Serializable
data class Widget(
    var widgetLightOn: Int,
    var widgetLightOff: Int,
    var widgetDarkOn: Int,
    var widgetDarkOff: Int
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
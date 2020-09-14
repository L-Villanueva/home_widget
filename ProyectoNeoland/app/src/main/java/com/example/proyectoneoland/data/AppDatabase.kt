package com.example.proyectoneoland.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Devices::class], version = 1)
@TypeConverters(Converters::class)


abstract class AppDatabase : RoomDatabase() {
    abstract fun devicesDao(): DevicesDao

}



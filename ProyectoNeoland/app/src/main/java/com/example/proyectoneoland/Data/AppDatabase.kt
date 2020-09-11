package com.example.proyectoneoland.Data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Devices::class], version = 1)


abstract class AppDatabase : RoomDatabase() {
    abstract fun devicesDao(): DevicesDao

}



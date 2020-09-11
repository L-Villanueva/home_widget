package com.example.proyectoneoland.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DevicesDao {

    @Query("SELECT * FROM Devices")
    fun getAll() : List<Devices>

    @Insert
    fun insert(Devices : Devices)

    @Insert
    fun insertAll(Devices : List<Devices>)

    @Update
    fun update(Devices : Devices)

    @Delete
    fun delete(Devices: Devices)

    @Query("SELECT * FROM Devices")
    fun getAllLive() : LiveData<List<Devices>>
}
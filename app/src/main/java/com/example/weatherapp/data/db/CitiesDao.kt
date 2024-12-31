package com.example.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.utils.CITIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCity(entity: CitiesEntity)



    @Delete
    suspend fun deleteCity(entity: CitiesEntity)


    @Query("SELECT*FROM $CITIES_TABLE ORDER BY id DESC")
    fun getAllCities():Flow<List<CitiesEntity>>








}
package com.example.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CitiesEntity::class], exportSchema = false, version = 2)
abstract class CitiesDatabase():RoomDatabase() {

    abstract fun citiesDao():CitiesDao



}
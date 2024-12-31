package com.example.weatherapp.utils.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.db.CitiesDatabase
import com.example.weatherapp.utils.CITIES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, CitiesDatabase::class.java,
        CITIES_DATABASE
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideDao(db:CitiesDatabase)=db.citiesDao()


}
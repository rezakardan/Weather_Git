package com.example.weatherapp.utils.event

class Events {
    data class OnUpdateWeather(val name: String?, val lat: Double?, val lon: Double?)
}
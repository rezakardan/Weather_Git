package com.example.weatherapp.data.model.main


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import kotlinx.parcelize.RawValue

@Parcelize
data class ResponseCurrentWeather(
    @SerializedName("base")
    var base: String,
    @SerializedName("clouds")
    var clouds:@RawValue Clouds,
    @SerializedName("cod")
    var cod: Int,
    @SerializedName("coord")
    var coord:@RawValue Coord,
    @SerializedName("dt")
    var dt: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main:@RawValue Main,
    @SerializedName("name")
    var name: String,
    @SerializedName("sys")
    var sys: @RawValue Sys,
    @SerializedName("timezone")
    var timezone: Int,
    @SerializedName("visibility")
    var visibility: Int,
    @SerializedName("weather")
    var weather:@RawValue List<Weather>,
    @SerializedName("wind")
    var wind:@RawValue Wind
) : Parcelable {
    @Parcelize
    data class Clouds(
        @SerializedName("all")
        var all: Int
    ) : Parcelable

    @Parcelize
    data class Coord(
        @SerializedName("lat")
        var lat: Double,
        @SerializedName("lon")
        var lon: Double
    ) : Parcelable

    @Parcelize
    data class Main(
        @SerializedName("feels_like")
        var feelsLike: Double,
        @SerializedName("grnd_level")
        var grndLevel: Int,
        @SerializedName("humidity")
        var humidity: Int,
        @SerializedName("pressure")
        var pressure: Int,
        @SerializedName("sea_level")
        var seaLevel: Int,
        @SerializedName("temp")
        var temp: Double,
        @SerializedName("temp_max")
        var tempMax: Double,
        @SerializedName("temp_min")
        var tempMin: Double
    ) : Parcelable

    @Parcelize
    data class Sys(
        @SerializedName("country")
        var country: String,
        @SerializedName("id")
        var id: Int,
        @SerializedName("sunrise")
        var sunrise: Int,
        @SerializedName("sunset")
        var sunset: Int,
        @SerializedName("type")
        var type: Int
    ) : Parcelable

    @Parcelize
    data class Weather(
        @SerializedName("description")
        var description: String,
        @SerializedName("icon")
        var icon: String,
        @SerializedName("id")
        var id: Int,
        @SerializedName("main")
        var main: String
    ) : Parcelable

    @Parcelize
    data class Wind(
        @SerializedName("deg")
        var deg: Int,
        @SerializedName("gust")
        var gust: Double,
        @SerializedName("speed")
        var speed: Double
    ) : Parcelable
}
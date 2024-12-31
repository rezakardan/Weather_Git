package com.example.weatherapp.data.model.main


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResponseForecast(
    @SerializedName("city")
    var city: City,
    @SerializedName("cnt")
    var cnt: Int,
    @SerializedName("cod")
    var cod: String,
    @SerializedName("list")
    var list: List<Item0>,
    @SerializedName("message")
    var message: Int
) : Parcelable {
    @Parcelize
    data class City(
        @SerializedName("coord")
        var coord: Coord,
        @SerializedName("country")
        var country: String,
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("population")
        var population: Int,
        @SerializedName("sunrise")
        var sunrise: Int,
        @SerializedName("sunset")
        var sunset: Int,
        @SerializedName("timezone")
        var timezone: Int
    ) : Parcelable {
        @Parcelize
        data class Coord(
            @SerializedName("lat")
            var lat: Double,
            @SerializedName("lon")
            var lon: Double
        ) : Parcelable
    }

    @Parcelize
    data class Item0(
        @SerializedName("clouds")
        var clouds: Clouds,
        @SerializedName("dt")
        var dt: Int,
        @SerializedName("dt_txt")
        var dtTxt: String,
        @SerializedName("main")
        var main: Main,
        @SerializedName("pop")
        var pop: Double,
        @SerializedName("snow")
        var snow: Snow?,
        @SerializedName("sys")
        var sys: Sys,
        @SerializedName("visibility")
        var visibility: Int,
        @SerializedName("weather")
        var weather: List<Weather>,
        @SerializedName("wind")
        var wind: Wind
    ) : Parcelable {
        @Parcelize
        data class Clouds(
            @SerializedName("all")
            var all: Int
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
            @SerializedName("temp_kf")
            var tempKf: Double,
            @SerializedName("temp_max")
            var tempMax: Double,
            @SerializedName("temp_min")
            var tempMin: Double
        ) : Parcelable

        @Parcelize
        data class Snow(
            @SerializedName("3h")
            var h: Double
        ) : Parcelable

        @Parcelize
        data class Sys(
            @SerializedName("pod")
            var pod: String
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
}
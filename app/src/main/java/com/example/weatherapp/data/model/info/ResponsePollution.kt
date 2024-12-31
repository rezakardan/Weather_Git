package com.example.weatherapp.data.model.info


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResponsePollution(
    @SerializedName("coord")
    var coord: Coord,
    @SerializedName("list")
    var list: List<Item0>
) : Parcelable {
    @Parcelize
    data class Coord(
        @SerializedName("lat")
        var lat: Double,
        @SerializedName("lon")
        var lon: Double
    ) : Parcelable

    @Parcelize
    data class Item0(
        @SerializedName("components")
        var components: Components,
        @SerializedName("dt")
        var dt: Int,
        @SerializedName("main")
        var main: Main
    ) : Parcelable {
        @Parcelize
        data class Components(
            @SerializedName("co")
            var co: Double,
            @SerializedName("nh3")
            var nh3: Double,
            @SerializedName("no")
            var no: Double,
            @SerializedName("no2")
            var no2: Double,
            @SerializedName("o3")
            var o3: Double,
            @SerializedName("pm10")
            var pm10: Double,
            @SerializedName("pm2_5")
            var pm25: Double,
            @SerializedName("so2")
            var so2: Double
        ) : Parcelable

        @Parcelize
        data class Main(
            @SerializedName("aqi")
            var aqi: Int
        ) : Parcelable
    }
}
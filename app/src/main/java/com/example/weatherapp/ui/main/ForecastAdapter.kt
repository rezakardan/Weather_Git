package com.example.weatherapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.model.main.ResponseForecast
import com.example.weatherapp.databinding.ItemCitiesBinding
import com.example.weatherapp.databinding.ItemForecastBinding
import com.example.weatherapp.ui.cities.CitiesListAdapter
import com.example.weatherapp.utils.BASE_URL_IMAGE
import com.example.weatherapp.utils.PNG_IMAGE
import com.example.weatherapp.utils.convertToDayName
import com.example.weatherapp.utils.other.TimeUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ForecastAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    lateinit var binding: ItemForecastBinding


    var itemCities = emptyList<ResponseForecast.Item0>()


    inner class ForecastViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(item: ResponseForecast.Item0) {

item.main.let {






            val temp = it.temp.toInt()


            binding.tempTxt.text = "${temp} ${context.getString(R.string.degreeCelsius)}"
}
item.main.humidity.let {




            binding.humidityTxt.text = "${   it}%"

}


            item.dtTxt.let {


                val dateSplit = it.split(" ")[0].convertToDayName()










                val hourSplit=it.split(" ")[1].dropLast(3)





                binding.dateTxt.text = "$dateSplit\n$hourSplit"


            }



            val image="${BASE_URL_IMAGE}${item.weather[0].icon}${PNG_IMAGE}"

binding.iconImg.load(image){
    crossfade(true)
    crossfade(100)
}

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        binding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding.root)
    }


    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = itemCities.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {


        holder.onBind(itemCities[position])
    }


    fun setData(data: List<ResponseForecast.Item0>) {

        val diffUtils = DiffUtils(itemCities, data)

        val diff = DiffUtil.calculateDiff(diffUtils)

        itemCities = data

        diff.dispatchUpdatesTo(this)

    }


    class DiffUtils(
        val oldItem: List<ResponseForecast.Item0>,
        val newItem: List<ResponseForecast.Item0>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItem[newItemPosition] === oldItem[oldItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newItem[newItemPosition] === oldItem[oldItemPosition]
        }


    }


}
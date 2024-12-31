package com.example.weatherapp.ui.add_city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.model.ResponseCitiesList
import com.example.weatherapp.databinding.ItemCitiesBinding
import javax.inject.Inject

class AddCityAdapter @Inject constructor() :
    RecyclerView.Adapter<AddCityAdapter.AddCityViewHolder>() {

    lateinit var binding: ItemCitiesBinding


    var itemCities = emptyList<ResponseCitiesList.ResponseCitiesListItem>()


    inner class AddCityViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(item: ResponseCitiesList.ResponseCitiesListItem) {


val citiesName=item.localNames?.fa

            if (!citiesName.isNullOrEmpty()){




                    binding.citiesNameTxt.text = "${item.localNames.fa} - ${item.country}"



            }else{

                binding.citiesNameTxt.text="${item.name} - ${item.country}"

            }




            binding.root.setOnClickListener {

                onItemClickListener?.let {


                    it(item)

                }

            }



        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddCityViewHolder {
        binding = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddCityViewHolder(binding.root)
    }




  private  var onItemClickListener:((ResponseCitiesList.ResponseCitiesListItem)->Unit)?=null


    fun setOnItemClickListener(listener:(ResponseCitiesList.ResponseCitiesListItem)->Unit){


        onItemClickListener=listener

    }



    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = itemCities.size

    override fun onBindViewHolder(holder: AddCityViewHolder, position: Int) {


        holder.onBind(itemCities[position])
    }


    fun setData( data:List<ResponseCitiesList.ResponseCitiesListItem>){

        val diffUtils=DiffUtils(itemCities,data)

     val diff=   DiffUtil.calculateDiff(diffUtils)

        itemCities=data

        diff.dispatchUpdatesTo(this)

    }



    class DiffUtils(
        val oldItem: List<ResponseCitiesList.ResponseCitiesListItem>,
        val newItem: List<ResponseCitiesList.ResponseCitiesListItem>
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
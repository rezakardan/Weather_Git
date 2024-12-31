package com.example.weatherapp.ui.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.model.ResponseCitiesList
import com.example.weatherapp.databinding.ItemCitiesBinding
import com.example.weatherapp.ui.add_city.AddCityAdapter
import javax.inject.Inject

class CitiesListAdapter@Inject constructor() :
    RecyclerView.Adapter<CitiesListAdapter.CityViewHolder>() {

    lateinit var binding: ItemCitiesBinding


    var itemCities = emptyList<CitiesEntity>()


    inner class CityViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(item: CitiesEntity) {







                binding.citiesNameTxt.text=item.name








            binding.root.setOnClickListener {

                onItemClickListener?.let {


                    it(item,"select")

                }



            }

            binding.trashImg.visibility=View.VISIBLE
            binding.trashImg.setOnClickListener {



                onItemClickListener?.let {

                    it(item,"delete")

                }



            }



        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        binding = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding.root)
    }




    private  var onItemClickListener:((CitiesEntity,String)->Unit)?=null


    fun setOnItemClickListener(listener:(CitiesEntity,String)->Unit){


        onItemClickListener=listener

    }



    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = itemCities.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {


        holder.onBind(itemCities[position])
    }


    fun setData( data:List<CitiesEntity>){

        val diffUtils=DiffUtils(itemCities,data)

        val diff=   DiffUtil.calculateDiff(diffUtils)

        itemCities=data

        diff.dispatchUpdatesTo(this)

    }



    class DiffUtils(
        val oldItem: List<CitiesEntity>,
        val newItem: List<CitiesEntity>
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
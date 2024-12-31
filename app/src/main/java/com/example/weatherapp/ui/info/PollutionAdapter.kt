package com.example.weatherapp.ui.info

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.model.info.PollutionModel
import com.example.weatherapp.databinding.ItemPollutionBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PollutionAdapter@Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<PollutionAdapter.PollutionViewHolder>() {

    lateinit var binding: ItemPollutionBinding


    var itemCities = emptyList<PollutionModel>()


    inner class PollutionViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(item: PollutionModel) {

          item.let {pollution->

              binding.pollutionTitle.text=pollution.name






              binding.pollutionCount.text=pollution.value.toString()





          }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollutionViewHolder {
        binding = ItemPollutionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PollutionViewHolder(binding.root)
    }


    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = itemCities.size

    override fun onBindViewHolder(holder: PollutionViewHolder, position: Int) {


        holder.onBind(itemCities[position])
    }


    fun setData(data: List<PollutionModel>) {

        val diffUtils = DiffUtils(itemCities, data)

        val diff = DiffUtil.calculateDiff(diffUtils)

        itemCities = data

        diff.dispatchUpdatesTo(this)

    }


    class DiffUtils(
        val oldItem: List<PollutionModel>,
        val newItem: List<PollutionModel>
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
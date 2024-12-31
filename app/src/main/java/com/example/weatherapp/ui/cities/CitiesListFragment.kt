package com.example.weatherapp.ui.cities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.databinding.FragmentAddCityBinding
import com.example.weatherapp.databinding.FragmentCitiesListBinding
import com.example.weatherapp.utils.event.EventBus
import com.example.weatherapp.utils.event.Events
import com.example.weatherapp.utils.network.NetworkChecker
import com.example.weatherapp.viewmodel.CitiesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CitiesListFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentCitiesListBinding

    @Inject
    lateinit var networkChecker: NetworkChecker
    override fun getTheme() = R.style.RemoveDialogBackground


    @Inject
    lateinit var citiesListAdapter: CitiesListAdapter


    private val viewModel by viewModels<CitiesViewModel>()


    @Inject
    lateinit var entity: CitiesEntity

    var isNetworkAvailable = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCitiesListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {


            networkChecker.checkNetwork().collect {

                isNetworkAvailable = it
            }

        }





        citiesListAdapter.setOnItemClickListener { item, type ->


            when (type) {

                "select" -> {


                    lifecycleScope.launch {


                        EventBus.publish(Events.OnUpdateWeather(item.name, item.lat, item.lon))

                    }

                    this@CitiesListFragment.dismiss()
                }

                "delete" -> {


                    viewModel.deleteCity(item)
                }


            }


        }



        viewModel.callGetAllCities()



        loadCitiesList()
    }

    private fun loadCitiesList() {
        viewModel.getAllCitiesLiveData.observe(viewLifecycleOwner) {

           // visibilityView(it.isEmpty())
            if (it.isNotEmpty()) {

     binding.containerGroup.visibility=View.VISIBLE
                citiesListAdapter.setData(it)
                binding.citiesList.adapter = citiesListAdapter
                binding.citiesList.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


            }
           else{
               binding.containerGroup.visibility=View.GONE

           }


        }
    }


//    private fun visibilityView(isEmpty: Boolean) {
//        binding.apply {
//            emptyLay.isVisible = isEmpty
//            containerGroup.isVisible = isEmpty.not()
//        }
//
//    }
}
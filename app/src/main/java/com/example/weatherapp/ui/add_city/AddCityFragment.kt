package com.example.weatherapp.ui.add_city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.databinding.FragmentAddCityBinding
import com.example.weatherapp.utils.event.EventBus
import com.example.weatherapp.utils.event.Events
import com.example.weatherapp.utils.network.NetworkChecker
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.viewmodel.AddCityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddCityFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddCityBinding

    @Inject
    lateinit var networkChecker: NetworkChecker

    @Inject
    lateinit var addCityAdapter: AddCityAdapter
    private val viewModel by viewModels<AddCityViewModel>()

    @Inject
    lateinit var citiesEntity: CitiesEntity


    override fun getTheme() = R.style.RemoveDialogBackground

    var isNetworkAvailable = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCityBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        lifecycleScope.launch {


            networkChecker.checkNetwork().collect {

                isNetworkAvailable = it
            }

        }



        addCityAdapter.setOnItemClickListener {


            citiesEntity.lat = it.lat
            citiesEntity.lon = it.lon


            val citiesName = it.localNames?.fa
            if (!citiesName.isNullOrEmpty()) {

                citiesEntity.name = "${it.localNames.fa}"


            } else {

                citiesEntity.name = "${it.name} "

            }




            viewModel.saveCity(citiesEntity)


            lifecycleScope.launch {
                EventBus.publish(
                    Events.OnUpdateWeather(
                        citiesEntity.name,
                        citiesEntity.lat,
                        citiesEntity.lon
                    )
                )


            }
            this@AddCityFragment.dismiss()
        }






        binding.searchInpLay.setEndIconOnClickListener {

            val search = binding.searchEdt.text.toString()

            if (isNetworkAvailable) {

                if (search.isNotEmpty()) {
                    viewModel.callAddCities(search)
                } else {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.searchCanNotBeEmpty),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }








        loadAddCities()
    }

    private fun loadAddCities() {
        viewModel.getAddCitiesListLiveData.observe(viewLifecycleOwner) { response ->

            when (response) {


                is NetworkRequest.Loading -> {

                    binding.loading.visibility = View.VISIBLE
                }


                is NetworkRequest.Success -> {

                    binding.loading.visibility = View.GONE

                    response.data?.let { data ->

                        if (data.isNotEmpty()) {
                            addCityAdapter.setData(data)

                            binding.citiesList.adapter = addCityAdapter

                            binding.citiesList.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )



                        }

                    }
                }

                is NetworkRequest.Error -> {

                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()

                }

            }


        }


    }


    override fun onStart() {
        super.onStart()


        val window = dialog!!.window

        window!!.setBackgroundDrawableResource(R.color.backShadow)

    }


}
package com.example.weatherapp.ui.main

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.utils.API_KEY
import com.example.weatherapp.utils.event.EventBus
import com.example.weatherapp.utils.event.Events
import com.example.weatherapp.utils.isVisible
import com.example.weatherapp.utils.network.NetworkChecker
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.onceObserve
import com.example.weatherapp.utils.setStatusBarIconsColor
import com.example.weatherapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {


    lateinit var binding: FragmentMainBinding


    private val viewModel by viewModels<MainViewModel>()


    @Inject
    lateinit var networkChecker: NetworkChecker




    @Inject
    lateinit var forecastAdapter:ForecastAdapter


    private val calendar by lazy { Calendar.getInstance() }


    var isNetworkAvailable = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().setStatusBarIconsColor(false)




        lifecycleScope.launch {
            EventBus.subscribe<Events.OnUpdateWeather> {

                viewModel.callGetCurrentWeather("$API_KEY", it.lat!!, it.lon!!)

viewModel.callGetForecast("${API_KEY}",it.lat,it.lon)
            }

        }


        binding.menuImg.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_citiesListFragment)
        }

        binding.addImg.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addCityFragment)

        }

        lifecycleScope.launch {
            networkChecker.checkNetwork().collect {

                isNetworkAvailable = it
            }
        }


        if (isNetworkAvailable) {

            viewModel.callGetAllCities()


        }

        loadAllCities()

        loadCurrentWeather()
        loadForeCast()
    }



    private fun loadForeCast() {
        viewModel.getForecastWeatherLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {


                }

                is NetworkRequest.Success -> {


                    response.data?.let { data ->


                        if (data.list.isNotEmpty()){





                        forecastAdapter.setData(data.list)

                        binding.forecastList.adapter=forecastAdapter
                        binding.forecastList.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,true)

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










    private fun loadCurrentWeather() {
        viewModel.getCurrentWeatherLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding. loading.isVisible(true, binding.container)


                }

                is NetworkRequest.Success -> {
                    binding.loading.isVisible(false,binding. container)




                    response.data?.let { data ->


                        binding.showAllTxt.setOnClickListener {



val directions=MainFragmentDirections.actionMainFragmentToInfoFragment(data)

                            findNavController().navigate(directions)


                        }

                        binding.cityName.text = data.name




                        data.weather.let { weather ->

                            if (weather.isNotEmpty()) {

                                weather[0].let { weather ->

                                    binding.infoTxt.text = weather.description




                                        val image = if (isNightNow()) {
                                            R.drawable.bg_night
                                        } else {

                                            weather.icon.let { icon ->


                                                setDynamicallyIcon(icon)


                                            }


                                        }

                                        binding.bgImg.load(image) {
                                            crossfade(true)
                                            crossfade(100)
                                        }




                                }

                            }


                        }


                        data.main.let { main->


                            val temp=main.temp.toInt()

                            val minTemp=main.tempMin.toInt()
                           val maxTemp=main.tempMax.toInt()


                            binding.tempTxt.text="${temp}${getString(R.string.degreeCelsius)}"

                            binding.TempInfoTxt.text="${minTemp}${getString(R.string.degree)}"  +
                                    "     ${maxTemp}${getString(R.string.degree)}"








                        }

                    }

                }


                is NetworkRequest.Error -> {
                    binding.loading.visibility = View.GONE

                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }
    }

    private fun loadAllCities() {
        viewModel.getAllCitiesLiveData.onceObserve(viewLifecycleOwner) {


            if (it.isNotEmpty()) {

                binding.container.visibility=View.VISIBLE
         //      binding.emptyLay.visibility = View.GONE
                viewModel.callGetCurrentWeather("$API_KEY", it[0].lat!!, it[0].lon!!)
                viewModel.callGetForecast("${API_KEY}",it[0].lat!!,it[0].lon!!)


            } else {

                binding.container.visibility=View.GONE
                findNavController().navigate(R.id.action_mainFragment_to_addCityFragment)

             //   binding.emptyLay.visibility = View.VISIBLE



            }


        }
    }


    private fun isNightNow(): Boolean {


        return calendar.get(Calendar.HOUR_OF_DAY) >= 18


    }


    private fun setDynamicallyIcon(icon: String): Int {

        return when (icon.dropLast(1)) {


            "01" -> {
                R.drawable.bg_sun
            }


            "02", "03", "04" -> {


                R.drawable.bg_cloud


            }


            "09", "10", "11" -> {


                R.drawable.bg_rain


            }

            "13" -> {


                R.drawable.bg_snow


            }


            "50" -> {


                R.drawable.bg_haze


            }


            else -> 0


        }


    }

}
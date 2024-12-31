package com.example.weatherapp.ui.info

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.data.model.info.PollutionModel
import com.example.weatherapp.data.model.info.ResponsePollution
import com.example.weatherapp.databinding.FragmentCitiesListBinding
import com.example.weatherapp.databinding.FragmentInfoBinding
import com.example.weatherapp.utils.API_KEY
import com.example.weatherapp.utils.BASE_URL_IMAGE
import com.example.weatherapp.utils.PNG_IMAGE
import com.example.weatherapp.utils.isVisible
import com.example.weatherapp.utils.network.NetworkChecker
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.viewmodel.InfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import javax.inject.Inject

@AndroidEntryPoint
class InfoFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentInfoBinding

    @Inject
    lateinit var networkChecker: NetworkChecker


    override fun getTheme() = R.style.RemoveDialogBackground


    @Inject
    lateinit var pollutionAdapter: PollutionAdapter

    private val args by navArgs<InfoFragmentArgs>()


    private val viewModel by viewModels<InfoViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        args.let { data ->


            lifecycleScope.launch {

                kotlinx.coroutines.delay(500)
                data.info.coord.let {


                    viewModel.callAirPollution("$API_KEY", it.lat, it.lon)


                }

            }







            data.info.weather.let { weather ->

                if (weather.isNotEmpty()) {

                    weather[0].let {

                        binding.infoTxt.text = it.description


                        val image = "${BASE_URL_IMAGE}${it.icon}${PNG_IMAGE}"


                        binding.iconImg.load(image) {
                            crossfade(true)
                            crossfade(100)
                        }


                    }


                }


            }


            data.info.main.let {


                val temp = it.temp.toInt()

                binding.tempTxt.text = "${temp} ${getString(R.string.degreeCelsius)}"


                val minTemp = it.tempMin.toInt()

                val maxTemp = it.tempMax.toInt()


                binding.TempInfoTxt.text = "${minTemp}${getString(R.string.degree)}     " +
                        "${maxTemp}${getString(R.string.degree)}"








                binding.weatherLay.pressureCountTxt.text = "${it.humidity}%"


                val fellCount = it.feelsLike.toInt()
                binding.weatherLay.feelCountTxt.text =
                    "${fellCount}${getString(R.string.degreeCelsius)}"


            }





            data.info.wind.let {


                binding.weatherLay.windCountTxt.text = "${it.speed} ${getString(R.string.km_s)}"


            }


        }
        loadAirPollution()
    }

    private fun loadAirPollution() {
        viewModel.getAirPollutionLiveData.observe(viewLifecycleOwner) { response ->


            when (response) {


                is NetworkRequest.Loading -> {
                    binding.loading.isVisible(true, binding.pollutionCard)
                }


                is NetworkRequest.Success -> {

                    binding.loading.isVisible(false, binding.pollutionCard)

                    response.data?.let { data ->


                        data.list.let { list ->

                            if (list.isEmpty().not()) {

                                list[0].let { myData ->

                                    binding.pollutionLay.apply {


                                        pollutionIconImg.apply {

                                            setImageResource(pollutionIcon(myData.main))

                                            imageTintList = ColorStateList.valueOf(
                                                ContextCompat.getColor(
                                                    requireContext(),
                                                    pollutionColor(myData.main)
                                                )
                                            )
                                        }

                                        pollutionInfoTxt.apply {


                                            text = pollutionMessage(myData.main)

                                            setTextColor(
                                                ContextCompat.getColor(
                                                    requireContext(),
                                                    pollutionColor(myData.main)
                                                )
                                            )


                                        }

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {


                                            binding.pollutionCard.apply {

                                                outlineSpotShadowColor = ContextCompat.getColor(
                                                    requireContext(),
                                                    pollutionColor(myData.main)
                                                )

                                                outlineAmbientShadowColor = ContextCompat.getColor(
                                                    requireContext(),
                                                    pollutionColor(myData.main)
                                                )


                                            }
                                        }


                                        pollutionAdapter.setData(fillPollutionData(myData.components))
                                        binding.pollutionLay.pollutionList.adapter =
                                            pollutionAdapter

                                        binding.pollutionLay.pollutionList.layoutManager =
                                            LinearLayoutManager(
                                                requireContext(),
                                                LinearLayoutManager.VERTICAL,
                                                false
                                            )
                                    }
                                }

                            }

                        }


                    }


                }


                is NetworkRequest.Error -> {
                    binding.loading.isVisible(false, binding.pollutionCard)

                    Toast.makeText(requireContext(), response.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }


    }

    private fun pollutionColor(data: ResponsePollution.Item0.Main): Int {


        return when (data.aqi) {

            1 -> R.color.green


            2 -> R.color.yellow


            3 -> R.color.orange
            4 -> R.color.red


            5 -> R.color.purple
            else -> 0


        }


    }


    private fun pollutionIcon(data: ResponsePollution.Item0.Main): Int {


        return when (data.aqi) {

            1 -> R.drawable.face_smile_hearts


            2, 3 -> R.drawable.face_clouds


            4, 5 -> R.drawable.face_mask


            else -> 0


        }


    }


    private fun pollutionMessage(data: ResponsePollution.Item0.Main): String {


        return when (data.aqi) {

            1 -> getString(R.string.messageAQI1)


            2 -> getString(R.string.messageAQI2)


            3, 4 -> getString(R.string.messageAQI3_4)

            5 -> getString(R.string.messageAQI5)

            else -> ""


        }


    }


    private fun fillPollutionData(data: ResponsePollution.Item0.Components): MutableList<PollutionModel> {

        val list = mutableListOf<PollutionModel>()

        list.add(PollutionModel(getString(R.string.co), data.co))
        list.add(PollutionModel(getString(R.string.pm2_5), data.pm25))

        list.add(PollutionModel(getString(R.string.pm10), data.pm10))
        list.add(PollutionModel(getString(R.string.o3), data.o3))
        list.add(PollutionModel(getString(R.string.no2), data.no2))
        list.add(PollutionModel(getString(R.string.so2), data.so2))

        return list
    }





}
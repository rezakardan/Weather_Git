package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.db.CitiesEntity
import com.example.weatherapp.data.model.ResponseCitiesList
import com.example.weatherapp.data.repository.AddCityRepository
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddCityViewModel@Inject constructor(private val repository: AddCityRepository):ViewModel() {



    val getAddCitiesListLiveData=MutableLiveData<NetworkRequest<ResponseCitiesList>>()

    fun callAddCities(q:String)=viewModelScope.launch {


getAddCitiesListLiveData.value=NetworkRequest.Loading()


        val response=repository.addCity(q)

        getAddCitiesListLiveData.value=NetworkResponse(response).generateResponse()

    }





    fun saveCity(entity: CitiesEntity)=viewModelScope.launch {

        repository.saveCity(entity)




    }





}
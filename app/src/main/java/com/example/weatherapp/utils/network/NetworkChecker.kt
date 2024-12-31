package com.example.weatherapp.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.telecom.ConnectionService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class NetworkChecker @Inject constructor(
    val manager: ConnectivityManager,
    val request: NetworkRequest
) : ConnectivityManager.NetworkCallback() {


 private   val isNetworkAvailable=MutableStateFlow(false)

private var networkCapabilities:NetworkCapabilities?=null



    fun checkNetwork():MutableStateFlow<Boolean>{


        manager.registerNetworkCallback(request,this)


        val activeNetwork=manager.activeNetwork


        if (activeNetwork==null){

            isNetworkAvailable.value=false

            return isNetworkAvailable



        }


        networkCapabilities=manager.getNetworkCapabilities(activeNetwork)


if (networkCapabilities==null){

    isNetworkAvailable.value=false

    return isNetworkAvailable

}


isNetworkAvailable.value=when{


networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true

networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true

else->false

}

return isNetworkAvailable


    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
    isNetworkAvailable.value=true}


    override fun onLost(network: Network) {
        super.onLost(network)
    isNetworkAvailable.value=false
    }

}
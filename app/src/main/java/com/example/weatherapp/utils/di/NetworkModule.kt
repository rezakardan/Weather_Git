package com.example.weatherapp.utils.di

import com.example.weatherapp.data.network.ApiServices
import com.example.weatherapp.utils.API_KEY
import com.example.weatherapp.utils.APPID
import com.example.weatherapp.utils.BASE_URL
import com.example.weatherapp.utils.CONNECTION_TIME
import com.example.weatherapp.utils.NAMED_PING
import com.example.weatherapp.utils.PING_INTERVAL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.internal.Providers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


@Provides
@Singleton
fun provideBaseUrl()= BASE_URL


    @Provides
    @Singleton
    fun provideTimeOUt()= CONNECTION_TIME


    @Provides
    @Singleton
    fun provideInterceptor()=HttpLoggingInterceptor().apply {

        level=HttpLoggingInterceptor.Level.BODY




    }

    @Provides
    @Singleton
    @Named(NAMED_PING)
    fun providePing()= PING_INTERVAL

    @Provides
    @Singleton
    fun provideGson():Gson=GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideApiClient(time:Long,@Named(NAMED_PING)ping:Long,interceptor: HttpLoggingInterceptor):OkHttpClient=OkHttpClient.Builder()





//        .addInterceptor {chain->
//
//
//            chain.proceed(chain.request().newBuilder().also {
//
//
//                it.addHeader(APPID, API_KEY)
//
//
//
//
//
//
//
//            }.build())
//
//
//
//
//
//
//
//        }.also {client->
//
//            client.addInterceptor(interceptor)
//
//        }
        .addInterceptor(interceptor)



        .connectTimeout(time,TimeUnit.SECONDS)

        .readTimeout(time,TimeUnit.SECONDS)
        .writeTimeout(time,TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .pingInterval(ping,TimeUnit.SECONDS)










        .build()





    @Provides
    @Singleton
    fun provideRetrofit(client:OkHttpClient,gson: Gson,url:String):ApiServices=

       Retrofit.Builder()
           .client(client)
           .addConverterFactory(GsonConverterFactory.create(gson))
           .baseUrl(url)
           .build()
           .create(ApiServices::class.java)















}
package com.picpay.desafio.android.framework.retrofit

import com.picpay.desafio.android.domain.constants.Constants.Companion.BASE_URL
import com.picpay.desafio.android.infrastructure.interfaces.network.PicPayService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        private val retrofit by lazy {
            val client = OkHttpClient.Builder().build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: PicPayService by lazy {
            retrofit.create(PicPayService::class.java)
        }
    }
}
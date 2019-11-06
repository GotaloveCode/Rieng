package com.miriga.rieng.services

import com.miriga.rieng.utils.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    //    create logger
    private val logger = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    //    create Okhttp client
    private val client = OkHttpClient.Builder().addInterceptor(logger)


    private val builder = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())

    private var retrofit = builder.build()

    private val interceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .header("Accept-Type", "application/json")
            .header("Content-Type", "application/json")
            .build()

        return@Interceptor chain.proceed(request)
    }

    fun <S> buildService(serviceType: Class<S>): S {
        if (!client.interceptors().contains(interceptor)) {
            client.addInterceptor(interceptor)

            builder.client(client.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceType)
    }


}

package com.example.githubuserapi.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    fun getApiService(): ApiService {

        val client = OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "token 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")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

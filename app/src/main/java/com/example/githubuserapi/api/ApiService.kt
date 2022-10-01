package com.example.githubuserapi.api

import com.example.githubuserapi.BuildConfig
import com.example.githubuserapi.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: $secretKey")
    fun getSearchUser(
        @Query("q") query: String,

    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: $secretKey")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/{typeFollow}")
    @Headers("Authorization: $secretKey")
    fun getFollow(
        @Path("username") username: String,
        @Path("typeFollow") typeFollow: String
    ): Call<ArrayList<FollowItem>>

    companion object {
        private const val secretKey = BuildConfig.KEY
    }
}
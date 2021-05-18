package com.example.wordscount.data.remote

import okhttp3.HttpUrl
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getWords(@Url url: HttpUrl): ResponseBody
}
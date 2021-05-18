package com.example.wordscount.data.repository

import android.content.Context
import com.example.wordscount.data.remote.ApiClient
import com.example.wordscount.data.remote.ApiResponse
import com.example.wordscount.data.room.RoomManger

open class BaseRepository(context: Context) {

    var apiService = ApiClient.getInstance()

    val roomDao = RoomManger.getInstance(context).roomDao()


    //---------------------------------------- ApiResponse Methods ---------------------------------

    fun getApiError(throwable: Throwable) = ApiResponse().getErrorBody(throwable)

}
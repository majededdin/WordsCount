package com.example.wordscount.data.repository

import android.content.Context
import com.example.wordscount.data.consts.AppConst
import com.example.wordscount.data.remote.ApiResponse
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import com.example.wordscount.data.remote.ApiStatus
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

class WordsRepo(context: Context) : BaseRepository(context) {

    fun getWords() = flow {
        emit(ApiResponse(ApiStatus.OnLoading))

        val wordsResponse =
            ApiResponse(
                apiService.getWords(AppConst.instance.appBaseUrl.toHttpUrlOrNull()!!).string()
            )

        emit(wordsResponse.getApiResult())
    }.catch { emit(getApiError(it)) }
}
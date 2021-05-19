package com.example.wordscount.data.repository

import android.content.Context
import com.example.wordscount.data.consts.AppConst
import com.example.wordscount.data.remote.ApiResponse
import com.example.wordscount.data.remote.ApiStatus
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

class WordsRepo(context: Context) : BaseRepository(context) {

    fun getWords() = flow {
        emit(ApiResponse(ApiStatus.OnLoading))

        val wordsResponse =
            ApiResponse(
                apiService.getWords(AppConst.instance.appBaseUrl.toHttpUrlOrNull()!!).string()
            )

        roomDao!!.deleteAllWord()
        roomDao.insertAllWord(wordsResponse.listOfWords)

        println("eeeeeeeeeeeee" + roomDao.getAllWords().size)

        emit(wordsResponse.getApiResult())
    }.catch { emit(getApiError(it)) }


    fun getWordsFromRoom() = flow {
        val wordsResponse = ApiResponse(
            roomDao!!.getAllWords()
        )

        emit(wordsResponse.getApiResult())
    }.catch { emit(getApiError(it)) }

}
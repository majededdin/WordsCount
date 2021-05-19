package com.example.wordscount.data.remote

import android.util.Log
import com.example.wordscount.BuildConfig
import com.example.wordscount.data.consts.Params.Companion.Message
import com.example.wordscount.data.model.Word
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import kotlin.collections.ArrayList

class ApiResponse {

    lateinit var message: String
    lateinit var apiStatus: ApiStatus
    var listOfWords: ArrayList<Word> = ArrayList()

    private lateinit var responseString: String

    //------------------------------------------ Public Constructor --------------------------------


    constructor()


    constructor(apiStatus: ApiStatus) {
        this.apiStatus = apiStatus
    }


    constructor(list: List<Word>) {
        try {
            showResponse(list.toString())
            this.listOfWords = list.toCollection(ArrayList())
        } catch (ex: Exception) {
            showResponse(ex.message)
        }
    }


    constructor(responseBodyAsString: String) {
        try {

            responseString = responseBodyAsString

            val doc: Document = Jsoup.parse(responseBodyAsString)

            showResponse("wwwww" + doc.text())

            listOfWords = getListOfWordsFromHtml(doc.html())


        } catch (throwable: Throwable) {
            showResponse("Throwable", throwable.message)
        }
    }


    //------------------------------------------ Public Methods ------------------------------------


    fun getApiResult(): ApiResponse {
        return try {
            handleJsonResult()
        } catch (throwable: Throwable) {
            getErrorBody(throwable)
        }
    }

    //------------------------------------------ Private Methods -----------------------------------


    private fun handleJsonResult(): ApiResponse {
        return when {

            listOfWords.isNotEmpty() -> {
                ApiResponse(ApiStatus.OnSuccess, listOfWords)
            }

            else -> ApiResponse(ApiStatus.OnSuccess)
        }
    }


    internal fun getErrorBody(throwable: Throwable): ApiResponse {
        return when (throwable) {
            is HttpException -> {
                val throwableMessage = throwable.response()!!.errorBody()!!.string()
                val jsonObject = JSONObject(throwableMessage)
                when (throwable.code()) {
                    401 -> ApiResponse(ApiStatus.OnAuth)

                    404 -> {
                        showResponse("Not Found", throwableMessage)
                        ApiResponse(ApiStatus.OnNotFound, jsonObject.getString(Message))
                    }

                    400 -> {
                        showResponse("bad Request", throwableMessage)
                        ApiResponse(ApiStatus.OnBadRequest, jsonObject.getString(Message))
                    }

                    422 -> {
                        showResponse("errorBody", throwableMessage)

                        ApiResponse(ApiStatus.OnError, jsonObject.getString(Message))
                    }

                    500 -> {
                        showResponse("backEndException", throwableMessage)
                        ApiResponse(ApiStatus.OnBackEndError, throwableMessage)
                    }

                    else -> {
                        showResponse("HttpExceptionMsg", throwableMessage)
                        ApiResponse(ApiStatus.OnHttpException, throwableMessage)
                    }
                }
            }

            is UnknownHostException -> ApiResponse(
                ApiStatus.OnUnknownHost,
                throwable.message!!
            )

            is ConnectException -> ApiResponse(
                ApiStatus.OnConnectException,
                throwable.message!!
            )

            is SocketTimeoutException -> ApiResponse(
                ApiStatus.OnTimeoutException,
                throwable.message!!
            )

            else -> {
                val throwableMsg = throwable.message
                showResponse("throwableMsg", throwableMsg)
                ApiResponse(ApiStatus.OnFailure, throwableMsg!!)
            }
        }
    }


    //------------------------------------------ Private Setting Methods ---------------------------

    private fun showResponse(message: String?) {
        if (BuildConfig.DEBUG)
            Log.e("ApiResponse ", "JSONResponse: $message")
    }


    private fun showResponse(key: String, message: String?) {
        if (BuildConfig.DEBUG)
            Log.e("ApiResponse ", "$key: $message")
    }

    //------------------------------------------ Private Parsing Methods ---------------------------


    private fun getListOfWordsFromHtml(responseBody: String): ArrayList<Word> {
        val re = Regex("[^A-Za-zء-ي ]")
        val newResponseBody = re.replace(responseBody, " ").trim() // works

        val words = newResponseBody.split("\\W+".toRegex())
        val list: ArrayList<String> = words.toCollection(ArrayList())
        val arrayListOfWord: ArrayList<Word> = ArrayList()

        for (i in 0 until list.size) {
            if (!arrayListOfWord.contains(Word(list[i], Collections.frequency(list, list[i]))))
                arrayListOfWord.add(Word(list[i], Collections.frequency(list, list[i])))
        }



        return arrayListOfWord
    }


    //------------------------------------------ Private Constructor --------------------------------


    private constructor(apiStatus: ApiStatus, message: String) {
        this.apiStatus = apiStatus
        this.message = message
    }


    private constructor(apiStatus: ApiStatus, listOfModel: ArrayList<Word>) {
        this.apiStatus = apiStatus
        this.listOfWords = listOfModel
    }

}
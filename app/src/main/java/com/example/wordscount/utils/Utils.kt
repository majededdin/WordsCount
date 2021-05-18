package com.example.wordscount.utils

import android.util.Log
import com.example.wordscount.data.consts.AppConst
import org.json.JSONArray
import org.json.JSONException

class Utils {
    companion object {

        fun errorResponseHandler(arr: JSONArray): String {
            val errorKey = StringBuilder()
            for (i in 0 until arr.length()) {
                try {
                    if (AppConst.instance.isDebug)
                        Log.e("ErrorResponseHandler:", "arr " + arr.getString(i))
                    errorKey.append(arr[i])
                    if (i != arr.length() - 1) errorKey.append("\n")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            return errorKey.toString()
        }

    }

}
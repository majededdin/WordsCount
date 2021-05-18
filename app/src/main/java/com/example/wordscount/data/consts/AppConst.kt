package com.example.wordscount.data.consts

import android.app.Application

class AppConst {

    var isDebug = false
    lateinit var appInstance: Application
    lateinit var appBaseUrl: String

    companion object {
        var instance = AppConst()
    }

}
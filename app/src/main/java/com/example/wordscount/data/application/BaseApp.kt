package com.example.wordscount.data.application

import android.app.Application
import com.example.wordscount.BuildConfig
import com.example.wordscount.data.consts.AppConst

open class BaseApp : Application() {

    private lateinit var appConst: AppConst

    companion object {
        var instance: BaseApp = BaseApp()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appConst = AppConst.instance
        initAppConst()
    }


    private fun initAppConst() {
        appConst.appInstance = this
        appConst.isDebug = BuildConfig.DEBUG
        appConst.appBaseUrl = BuildConfig.BASE_URL
    }

}
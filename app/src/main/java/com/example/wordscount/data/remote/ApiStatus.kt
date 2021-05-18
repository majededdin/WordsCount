package com.example.wordscount.data.remote

enum class ApiStatus {
    OnAuth,
    OnBackEndError,
    OnConnectException,
    OnError,
    OnFailure,
    OnHttpException,
    OnLoading,
    OnNotFound,
    OnBadRequest,
    OnSuccess,
    OnTimeoutException,
    OnUnknownHost
}
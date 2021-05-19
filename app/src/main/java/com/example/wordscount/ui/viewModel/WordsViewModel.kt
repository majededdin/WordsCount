package com.example.wordscount.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wordscount.data.remote.ApiResponse
import com.example.wordscount.data.repository.WordsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WordsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: WordsRepo = WordsRepo(application.applicationContext)

    private val wordsResponse = MutableLiveData<ApiResponse>()

    val wordsResult: LiveData<ApiResponse>
        get() = wordsResponse


    fun getWords() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getWords().collect { wordsResponse.postValue(it) }
        }
    }

    fun getWordsFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getWordsFromRoom().collect { wordsResponse.postValue(it) }
        }
    }

}
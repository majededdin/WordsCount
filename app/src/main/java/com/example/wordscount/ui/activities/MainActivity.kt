package com.example.wordscount.ui.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordscount.data.model.ErrorHandler
import com.example.wordscount.data.model.Word
import com.example.wordscount.data.remote.ApiResponse
import com.example.wordscount.data.remote.ApiStatus
import com.example.wordscount.databinding.ActivityMainBinding
import com.example.wordscount.ui.adapters.WordsAdapter
import com.example.wordscount.ui.base.BaseActivity
import com.example.wordscount.ui.viewModel.WordsViewModel
import com.example.wordscount.utils.extentionUtils.toGone
import com.example.wordscount.utils.extentionUtils.toVisible
import com.example.wordscount.utils.recyclerUtils.SimpleDividerItemDecoration
import java.util.*

class MainActivity : BaseActivity<WordsViewModel>() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var wordsVM: WordsViewModel

    private lateinit var adapter: WordsAdapter

    private var apiResponse: ApiResponse = ApiResponse()


    override fun getViewModel(): Class<WordsViewModel> = WordsViewModel::class.java


    override fun viewModelInstance(viewModel: WordsViewModel?) {
        wordsVM = viewModel!!
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()

        updateView()

        wordsVM.wordsResult.observe(this, this::wordsResult)
    }


    override fun updateView() {
        apiResponse.listOfWords.clear()
        adapter.clear()

        wordsVM.getWords()
    }


    private fun wordsResult(apiResponse: ApiResponse) {
        this.apiResponse = apiResponse
        handleApiResponse(apiResponse) { updateView() }
        if (apiResponse.apiStatus == ApiStatus.OnSuccess) {
            setResponseResult(apiResponse.listOfWords)
        }
    }


    private fun setResponseResult(list: ArrayList<Word>) {
        if (list.isNotEmpty()) {
            binding.layoutEmptyState.toGone()
            binding.recyclerWords.toVisible()
            adapter.addAll(list)
        } else {
            binding.recyclerWords.toGone()
            binding.layoutEmptyState.toVisible()
            apiResponse.listOfWords.clear()
            adapter.clear()
        }
    }


    override fun setErrorHandler(handler: ErrorHandler) {
    }


    override fun viewInit() {
        adapter = WordsAdapter()

        binding.recyclerWords.adapter = adapter
        binding.recyclerWords.layoutManager = LinearLayoutManager(this)
        binding.recyclerWords.addItemDecoration(SimpleDividerItemDecoration(this))

    }

}
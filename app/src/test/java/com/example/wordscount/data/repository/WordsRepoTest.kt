package com.example.wordscount.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wordscount.TestCoroutineRule
import com.example.wordscount.data.consts.AppConst
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WordsRepoTest : TestCase() {

    private lateinit var dataSource: WordsRepo

    private var allOldWords = 0
    private var allNewWords = 0

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var job1: Job
    private lateinit var job2: Job


    @Before
    public override fun setUp() {
        AppConst.instance.appBaseUrl = "https://www.alalmiyalhura.com/"

        dataSource =
            WordsRepo(ApplicationProvider.getApplicationContext())

        Dispatchers.setMain(testDispatcher)


    }


    @After
    public override fun tearDown() {
        // 2
        Dispatchers.resetMain()
        // 3
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun getAndStoreAllWords() = runBlocking {

        val flow = dataSource.getWords()

        // Verify
        job1 = launch {
            flow.collect {
                this@WordsRepoTest.allOldWords = it.listOfWords.size

                val flow2 = dataSource.getWordsFromRoom()

                job2 = launch {
                    flow2.collect {
                        this@WordsRepoTest.allNewWords = it.listOfWords.size

                        Truth.assertThat(this@WordsRepoTest.allOldWords)
                            .isEqualTo(this@WordsRepoTest.allNewWords)

                        job1.cancel()
                        job2.cancel()

                    }
                }
            }
        }

    }
}
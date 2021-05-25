package com.example.wordscount.ui.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wordscount.TestCoroutineRule
import com.example.wordscount.data.consts.AppConst
import com.example.wordscount.data.model.Word
import com.example.wordscount.data.remote.ApiResponse
import com.example.wordscount.data.remote.ApiStatus
import com.example.wordscount.data.repository.WordsRepo
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WordsViewModelTest {

    private lateinit var viewModel: WordsViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Mock
    private lateinit var wordsResponseObserver: Observer<ApiResponse>


    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        AppConst.instance.appBaseUrl = "https://www.masrawy.com/"

        viewModel = WordsViewModel(ApplicationProvider.getApplicationContext())
    }


    @Test
    fun `when fetching results ok then return a list successfully`() {
        val emptyList = arrayListOf<Word>()

        testCoroutineRule.runCatching {

            viewModel.wordsResult.observeForever(wordsResponseObserver)

            val mock: WordsRepo = mock(WordsRepo::class.java)


            `when`(mock.getWords()).thenAnswer {
                ApiResponse(ApiStatus.OnSuccess, emptyList)
            }

            viewModel.getWords()
            assertNotNull(viewModel.wordsResult.value)
            assertEquals(ApiResponse(ApiStatus.OnSuccess, emptyList), viewModel.wordsResult.value)
        }
    }

    @Test
    fun `when calling for results then return loading`() {
        testCoroutineRule.runCatching {
            viewModel.wordsResult.observeForever(wordsResponseObserver)
            viewModel.getWords()
            verify(wordsResponseObserver).onChanged(ApiResponse(ApiStatus.OnLoading))
        }
    }

    @Test
    fun `when fetching results fails then return an error`() {
        val exception = mock(HttpException::class.java)
        testCoroutineRule.runCatching {

            val mock: WordsRepo = mock(WordsRepo::class.java)

            viewModel.wordsResult.observeForever(wordsResponseObserver)
            `when`(mock.getWords()).thenAnswer {
                ApiResponse(ApiStatus.OnError, exception.message())
            }
            viewModel.getWords()
            assertNotNull(viewModel.wordsResult.value)
            assertEquals(
                ApiResponse(ApiStatus.OnError, exception.message()),
                viewModel.wordsResult.value
            )
        }
    }

    @After
    fun tearDown() {
        viewModel.wordsResult.removeObserver(wordsResponseObserver)
    }
}
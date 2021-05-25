package com.example.wordscount.data.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wordscount.data.model.Word
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class RoomMangerTest : TestCase() {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var roomDatabase: RoomManger
    private lateinit var roomDao: RoomDao

    private val listOfWords: List<Word> = listOf(Word("title1", 5), Word("title2", 2))


    @Before
    public override fun setUp() {
        roomDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomManger::class.java
        ).allowMainThreadQueries()
            .build()

        roomDao = roomDatabase.roomDao()!!
    }


    // Override function closeDb() and annotate it with @After
    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        roomDatabase.close()
    }

    @Test
    fun insertAllWords(): Unit = runBlocking {
        roomDao.insertAllWord(listOfWords).apply {
            Truth.assertThat(this.size).isGreaterThan(0)
        }
    }


    @Test
    fun deleteAllWords(): Unit = runBlocking {
        insertAllWords()
        roomDao.deleteAllWord().apply {
            Truth.assertThat(this).isGreaterThan(0)
        }
    }


    @Test
    fun getAllWords(): Unit = runBlocking {
        insertAllWords()
        roomDao.getAllWords().apply {
            Truth.assertThat(this.size).isGreaterThan(0)
            Truth.assertThat(this[0]).isEqualTo(listOfWords[0])
        }
    }
}
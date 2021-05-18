package com.example.wordscount.data.room

import androidx.room.*
import com.example.wordscount.data.model.Word

@Dao
interface RoomDao {

    @Query("select * from wordTable")
    suspend fun getAllWords(): List<Word>

    @Insert
    suspend fun insertAllWord(words: Word): Long

    @Update
    suspend fun updateWord(note: Word): Int

    @Delete
    suspend fun deleteWord(note: Word): Int

}
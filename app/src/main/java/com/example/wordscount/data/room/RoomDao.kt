package com.example.wordscount.data.room

import androidx.room.*
import com.example.wordscount.data.model.Word

@Dao
interface RoomDao {

    @Query("select * from wordTable")
    suspend fun getAllWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAllWord(words: List<Word>): List<Long>

    @Update
    suspend fun updateWord(note: Word): Int

    @Delete
    suspend fun deleteWord(note: Word): Int

}
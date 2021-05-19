package com.example.wordscount.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wordscount.data.model.Word

@Dao
interface RoomDao {

    @Query("select * from wordTable")
    suspend fun getAllWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAllWord(words: List<Word>): List<Long>

    @Query("Delete from wordTable")
    suspend fun deleteAllWord(): Int

}
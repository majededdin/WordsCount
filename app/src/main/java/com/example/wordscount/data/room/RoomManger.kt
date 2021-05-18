package com.example.wordscount.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wordscount.data.consts.Params.Companion.WORDS_DATABASE
import com.example.wordscount.data.model.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class RoomManger : RoomDatabase() {

    abstract fun roomDao(): RoomDao?

    companion object {

        @Volatile
        private var INSTANCE: RoomManger? = null

        fun getInstance(context: Context): RoomManger {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, RoomManger::class.java, WORDS_DATABASE)
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}
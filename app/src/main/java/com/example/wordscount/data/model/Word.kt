package com.example.wordscount.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wordscount.data.consts.Params.Companion.COUNT
import com.example.wordscount.data.consts.Params.Companion.WORD
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity(tableName = "wordTable")
@Parcelize
open class Word(
    @ColumnInfo(name = WORD)
    var word: String,
    @ColumnInfo(name = COUNT)
    var count: Int
) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor() : this("", 0)

    override fun toString(): String {
        return "Word(word=$word, count=$count)"
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Word

        if (word != other.word) return false

        return true
    }

    override fun hashCode(): Int {
        return word.hashCode()
    }


}
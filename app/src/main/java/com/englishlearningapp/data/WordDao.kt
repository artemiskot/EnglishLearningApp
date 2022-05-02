package com.englishlearningapp.data
import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englishlearningapp.Word

interface WordDao {
    @Query("SELECT * FROM word_table ORDER BY id ASC")
    fun readAllData() : LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWord(word: Word)

    @Query("SELECT * FROM word_table ORDER BY RANDOM() LIMIT 1")
    fun getRandomWord() : Word

    @Query("SELECT RussianWord FROM word_table ORDER BY RANDOM() LIMIT 1")
    fun getRussian() : String

    @Query("SELECT * FROM word_table ORDER BY RANDOM() LIMIT 4")
    fun getRandomWord4() : LiveData<List<Word>>
}
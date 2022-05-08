package com.englishlearningapp.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englishlearningapp.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM word_table ORDER BY id ASC")
    fun readAllData() : Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWords(order: List<Word>)

    @Query("SELECT * FROM word_table ORDER BY RANDOM() LIMIT 1")
    fun getRandomWord() : Flow<Word>

    @Query("SELECT RussianWord FROM word_table ORDER BY RANDOM() LIMIT 1")
    fun getRussian() : Flow<String>

    @Query("SELECT DISTINCT * FROM word_table ORDER BY RANDOM() LIMIT 4")
    fun getRandomWord4() : Flow<List<Word>>

}
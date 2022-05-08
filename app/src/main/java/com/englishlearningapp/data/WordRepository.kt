package com.englishlearningapp.data

import androidx.lifecycle.LiveData
import com.englishlearningapp.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val readAllData: Flow<List<Word>> = wordDao.readAllData()

    suspend fun addWord(word: Word){
        wordDao.addWord(word)
    }

    val getRandomWord: Flow<Word> = wordDao.getRandomWord()

    val getRussian: Flow<String> = wordDao.getRussian()

    val getRandomWord4: Flow<List<Word>> = wordDao.getRandomWord4()
}
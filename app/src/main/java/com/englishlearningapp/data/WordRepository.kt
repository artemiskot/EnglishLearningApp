package com.englishlearningapp.data

import androidx.lifecycle.LiveData
import com.englishlearningapp.Word

class WordRepository(private val wordDao: WordDao) {

    val readAllData: LiveData<List<Word>> = wordDao.readAllData()

    suspend fun addWord(word: Word){
        wordDao.addWord(word)
    }

    val getRandomWord: Word = wordDao.getRandomWord()

    val getRussian: String = wordDao.getRussian()

    val getRandomWord4: LiveData<List<Word>> = wordDao.getRandomWord4()
}
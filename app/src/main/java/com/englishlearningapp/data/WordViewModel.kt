package com.englishlearningapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.englishlearningapp.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application): AndroidViewModel(application) {

    val readAllWord: LiveData<List<Word>>
    val getRandomWord: Word
    val getRussian: String
    val getRandomWord4: LiveData<List<Word>>
    private val repository: WordRepository

    init {
        val wordDao = WordDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordDao)
        readAllWord = repository.readAllData
        getRandomWord = repository.getRandomWord
        getRussian = repository.getRussian
        getRandomWord4 = repository.getRandomWord4
    }

    fun addWord(word: Word){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWord(word)
        }
    }
}
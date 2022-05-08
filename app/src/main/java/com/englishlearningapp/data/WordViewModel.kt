package com.englishlearningapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.englishlearningapp.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class WordViewModel(application: Application): AndroidViewModel(application) {

    val readAllWord: Flow<List<Word>>
    val getRandomWord: Flow<Word>
    val getRussian: Flow<String>
    val getRandomWord4: Flow<List<Word>>
    private val repository: WordRepository

    init {
        val wordDao = WordDatabase.getInstance(application).getAppDao()
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

    fun getRandomWord():Flow<Word>{
        lateinit var tmpWord:Flow<Word>
        //viewModelScope.launch(Dispatchers.IO) {
            tmpWord = repository.getRandomWord
        //}
        return tmpWord
    }

    fun wordsPractice():Flow<List<Word>>{
        lateinit var tmpList:Flow<List<Word>>
        //viewModelScope.launch(Dispatchers.IO) {
        tmpList = repository.getRandomWord4
        //}
        return tmpList
    }
    suspend fun <Word> Flow<List<Word>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()
}
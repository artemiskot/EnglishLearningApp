package com.englishlearningapp.ui.Statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatisticsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is stats section. All info about learnt words will appear here later"
    }
    val text: LiveData<String> = _text
}
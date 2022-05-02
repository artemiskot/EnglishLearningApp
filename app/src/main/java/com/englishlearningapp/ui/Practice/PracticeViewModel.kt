package com.englishlearningapp.ui.Practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PracticeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Word will appear soon..."
    }
    val text: LiveData<String> = _text
}
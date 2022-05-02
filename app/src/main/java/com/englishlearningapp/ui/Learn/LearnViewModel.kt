package com.englishlearningapp.ui.Learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LearnViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        //value = "Welcome to learning section!"
    }
    val text: LiveData<String> = _text
}
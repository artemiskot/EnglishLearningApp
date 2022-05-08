package com.englishlearningapp.ui.Learn

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AnyThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.englishlearningapp.Word
import com.englishlearningapp.data.WordDao
import com.englishlearningapp.data.WordDatabase
import com.englishlearningapp.data.WordViewModel
import com.englishlearningapp.databinding.FragmentLearnBinding
import kotlinx.android.synthetic.main.fragment_learn.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class LearnFragment : Fragment() {
    private lateinit var allWords: List<Word>
    private lateinit var dao: WordDao
    private var _binding: FragmentLearnBinding? = null
    private lateinit var mWordViewModel: WordViewModel
    private lateinit var currentWord: Word
    private lateinit var db: WordDatabase

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val learnViewModel =
            ViewModelProvider(this).get(LearnViewModel::class.java)
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        val root: View = binding.root


        mWordViewModel =
                ViewModelProvider(this).get(WordViewModel::class.java)

        dao = WordDatabase.getInstance(requireContext()).getAppDao()

        getWordDb()
        binding.nextWord.setOnClickListener {
            getWordDb()
        }

        return root
    }

    /* private fun getAllWords(){
        lifecycleScope.launch(){
            allWords = mWordViewModel.readAllData()
        }
    } */

    private fun setWord(){
        text_word.text = currentWord.EnglishWord
        text_word_translation.text = currentWord.RussianWord

        currentWord = allWords.random()
        allWords = allWords.minusElement(currentWord)
        if(allWords.size == 1)
            getWordDb()
    }

    private fun insertWord(russianWord: String, englishWord: String) {
        lifecycleScope.launch {
            val word = Word(0,russianWord, englishWord)
            dao.addWord(word)
        }
    }

    private fun getWordDb(){
        lifecycleScope.launch {
            mWordViewModel.getRandomWord().collect { value: Word ->
                currentWord = value
                text_word.text = currentWord.EnglishWord
                text_word_translation.text = currentWord.RussianWord
            }
        }
    }

    suspend fun <Word> Flow<List<Word>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

    fun writeDb(){
        lifecycleScope.launch {
            //currentWord =  mWordViewModel.getRandomWord()
            text_word.text = currentWord.EnglishWord
            text_word_translation.text = currentWord.RussianWord
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.englishlearningapp.ui.Practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.englishlearningapp.data.MyDatabase
import com.englishlearningapp.data.Word
import com.englishlearningapp.data.WordDao
import com.englishlearningapp.data.WordViewModel
import com.englishlearningapp.databinding.FragmentPracticeBinding
import kotlinx.android.synthetic.main.fragment_practice.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
var totalWords: Int = 0
class PracticeFragment : Fragment() {

    private var _binding: FragmentPracticeBinding? = null

    private lateinit var db: MyDatabase
    private lateinit var dao: WordDao
    private lateinit var mWordViewModel: WordViewModel

    private lateinit var wordList: List<Word>
    private lateinit var randWord: Word
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(PracticeViewModel::class.java)

        _binding = FragmentPracticeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mWordViewModel =
            ViewModelProvider(this).get(WordViewModel::class.java)

        val textView: TextView = binding.textWordPractice
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        dao = MyDatabase.getInstance(requireContext()).wordDao()

        setButtonText()


        binding.word1.setOnClickListener{
            checkCorrect(word1.text.toString())
        }
        binding.word2.setOnClickListener{
            checkCorrect(word2.text.toString())
        }
        binding.word3.setOnClickListener{
            checkCorrect(word3.text.toString())
        }
        binding.word4.setOnClickListener{
            checkCorrect(word4.text.toString())
        }
        return root
    }

    fun checkCorrect(answer: String){
        if(answer == randWord.RussianWord) {
            Toast.makeText(activity, "Правильно!", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(activity, "Неправильно :( Ответ был: $answer", Toast.LENGTH_SHORT).show()
        totalWords += 1
        setButtonText()
    }

    fun setButtonText(){
     lifecycleScope.launch {
            mWordViewModel.wordsPractice().collect { value: List<Word> ->
                wordList = value
                randWord = wordList.random()
                text_word_practice.text = randWord.EnglishWord
                word1.text = wordList[0].RussianWord
                word2.text = wordList[1].RussianWord
                word3.text = wordList[2].RussianWord
                word4.text = wordList[3].RussianWord
            }
        }
    }

    suspend fun <Word> Flow<List<Word>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
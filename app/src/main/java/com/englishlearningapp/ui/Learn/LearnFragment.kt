package com.englishlearningapp.ui.Learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.englishlearningapp.Word
import com.englishlearningapp.data.WordViewModel
import com.englishlearningapp.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private lateinit var mWordViewModel: WordViewModel
    private lateinit var Words: LiveData<List<Word>>
    private lateinit var currentWord: Word
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

        val textView: TextView = binding.textWord
        learnViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        Words = mWordViewModel.readAllWord

        return root
    }

    fun getWordDb(){

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
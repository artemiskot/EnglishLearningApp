package com.englishlearningapp.ui.Practice

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.englishlearningapp.AddWordActivity
import com.englishlearningapp.GameModeActivity
import com.englishlearningapp.data.*
import com.englishlearningapp.databinding.FragmentPracticeBinding
import kotlinx.android.synthetic.main.fragment_practice.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
var totalWords: Int = 0
class PracticeFragment : Fragment() {

    private var _binding: FragmentPracticeBinding? = null

    private lateinit var dao: WordDao
    private lateinit var mWordViewModel: WordViewModel

    private lateinit var mUserGameStatisticsViewModel: UserGameStatisticsViewModel
    private lateinit var mUserGameStatisticsRepository: UserGameStatisticsRepository
    private lateinit var mUserGameStatisticsDao: UserGameStatisticsDao

    private lateinit var mGameStatisticsViewModel: GameStatisticsViewModel
    private lateinit var mGameStatisticsRepository: GameStatisticsRepository
    private lateinit var mGameStatisticsDao: GameStatisticsDao

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mUserRepository: UserRepository
    private lateinit var mDaoUser: UserDao


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

        val emailId = arguments?.getString("email_id") ?: ""
        val startGameButton = binding.startGame.setOnClickListener {
            val intent = Intent(requireActivity(), GameModeActivity::class.java)
            intent.putExtra("email_id",emailId)
            startActivity(intent)
        }

        return root
    }


    suspend fun <Word> Flow<List<Word>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()


}
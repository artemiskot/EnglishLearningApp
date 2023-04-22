package com.englishlearningapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.englishlearningapp.R
import com.englishlearningapp.data.*
import com.englishlearningapp.databinding.FragmentLearnBinding
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LearnModeActivity : AppCompatActivity() {

    private lateinit var allWords: List<Word>
    private lateinit var daoWord: WordDao
    private var _binding: FragmentLearnBinding? = null
    //DB Access
    private lateinit var mWordViewModel: WordViewModel
    private lateinit var mUserLearnStatisticsViewModel: UserLearnStatisticsViewModel
    private lateinit var mUserLearnStatisticsRepository: UserLearnStatisticsRepository
    private lateinit var mUserLearnStatisticsDao: UserLearnStatisticsDao

    private lateinit var mLearnStatisticsViewModel: LearnStatisticsViewModel
    private lateinit var mLearnStatisticsRepository: LearnStatisticsRepository
    private lateinit var mLearnStatisticsDao: LearnStatisticsDao

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mUserRepository: UserRepository
    private lateinit var mDaoUser: UserDao

    private lateinit var currentWord: Word
    private lateinit var emailId:String
    private lateinit var db: MyDatabase
    private lateinit var wordList:List<Word>
    private lateinit var currentUser:User
    private var currentLearnStatistics: LearnStatistics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_mode)
        mWordViewModel =
            ViewModelProvider(this)[WordViewModel::class.java]

        val moduleId = intent.getIntExtra("MODULE_ID", -1)
        if (moduleId == -1) {
            finish()
            return
        }
        emailId = intent.getStringExtra("email_id") ?: ""
        mUserLearnStatisticsDao = MyDatabase.getInstance(this).userLearnStatisticsDao()
        mUserLearnStatisticsRepository = UserLearnStatisticsRepository(mUserLearnStatisticsDao)
        mUserLearnStatisticsViewModel = UserLearnStatisticsViewModel(mUserLearnStatisticsRepository)

        mLearnStatisticsDao = MyDatabase.getInstance(this).learnStatisticsDao()
        mLearnStatisticsRepository = LearnStatisticsRepository(mLearnStatisticsDao)
        mLearnStatisticsViewModel = LearnStatisticsViewModel(mLearnStatisticsRepository)

        mDaoUser = MyDatabase.getInstance(this).userDao()
        mUserRepository = UserRepository(mDaoUser)
        mUserViewModel = UserViewModel(mUserRepository)

        mWordViewModel.getWordsByModule(moduleId).observe(this) { words ->
            wordList = words
        }
        getUserInfo()
        val nextWord:Button = findViewById<Button>(R.id.next_word)
        nextWord.text = "Начать изучение"
        nextWord.setOnClickListener {
            nextWord.text = "Следующее слово"
            getWordDb(this)
        }
    }

    private fun getWordDb(context: Context) {
        lifecycleScope.launch {
            if (!wordList.isEmpty()) {
                insertLearnStatistics()
                currentWord = wordList.random()
                wordList = wordList.filterNot { it == currentWord }
            } else {
                Toast.makeText(context, "Все слова из этой категории выучены", LENGTH_SHORT).show()
                finish()
            }
            val engWord: TextView = findViewById<TextView>(R.id.text_word)
            val rusWord: TextView = findViewById<TextView>(R.id.text_word_translation)
            engWord.text = currentWord.EnglishWord
            rusWord.text = currentWord.RussianWord
        }
    }
    private fun getUserInfo(){
        lifecycleScope.launch{
            mUserViewModel.getUserByEmail(emailId).collect(){
                if(it!=null) {
                    currentUser = it
                    getUserCurrentLearnStatistics()
                }
            }
        }
    }
    private fun getUserCurrentLearnStatistics(){
        lifecycleScope.launch{
            mLearnStatisticsViewModel.getLearnStatisticsById(currentUser.id!!).collect{
                currentLearnStatistics = it
            }
        }
    }

    private fun insertLearnStatistics(){
        lifecycleScope.launch{
            if (currentLearnStatistics!=null) {
                mLearnStatisticsViewModel.updateLearnStatistics(
                    LearnStatistics(
                        currentUser.id!!,
                        currentLearnStatistics!!.total_learnt + 1
                    )
                )
            } else{
                mLearnStatisticsViewModel.insertLearnStatistics(LearnStatistics(currentUser.id!!,1))
                mUserLearnStatisticsViewModel.insertUserLearnStatistics(UserLearnStatistics(user_id = currentUser.id!!, learn_statistics_id = currentUser.id!!))
            }
        }
    }
}

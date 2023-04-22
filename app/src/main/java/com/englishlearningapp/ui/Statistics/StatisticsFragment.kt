package com.englishlearningapp.ui.Statistics

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.englishlearningapp.databinding.FragmentStatisticsBinding
import com.englishlearningapp.AddWordActivity
import com.englishlearningapp.data.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mUserRepository: UserRepository
    private lateinit var mDaoUser: UserDao

    private lateinit var mLearnStatisticsViewModel: LearnStatisticsViewModel
    private lateinit var mLearnStatisticsRepository: LearnStatisticsRepository
    private lateinit var mLearnStatisticsDao: LearnStatisticsDao
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var currentUser: User
    private lateinit var emailId:String
    private lateinit var password:String
    private lateinit var currentLearnStatistics:LearnStatistics


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(StatisticsViewModel::class.java)

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mDaoUser = MyDatabase.getInstance(requireContext()).userDao()
        mUserRepository = UserRepository(mDaoUser)
        val textView: TextView = binding.statsText
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        mUserViewModel = UserViewModel(mUserRepository)
        emailId = arguments?.getString("email_id") ?: ""
        password = arguments?.getString("password") ?: ""
        mLearnStatisticsDao = MyDatabase.getInstance(requireContext()).learnStatisticsDao()
        mLearnStatisticsRepository = LearnStatisticsRepository(mLearnStatisticsDao)
        mLearnStatisticsViewModel = LearnStatisticsViewModel(mLearnStatisticsRepository)
        val insertButton = binding.insertWordBtn.setOnClickListener {
            val intent = Intent(requireActivity(), AddWordActivity::class.java)
            intent.putExtra("email_id",emailId)
            startActivity(intent)
        }


        binding.insertWordBtn.visibility = View.GONE
        binding.statsText.text = "Current user :${emailId}"
        getUserInfo()
        binding.getUserInfo.setOnClickListener {
            getUserInfo()
        }
        binding.getUserLearnInfo.setOnClickListener {
            getUserCurrentLearnStatistics()
        }
        return root
    }

    private fun getUserInfo(){
        lifecycleScope.launch{
            mUserViewModel.getUserByEmail(emailId).collect(){
                if(it!=null) {
                    currentUser = it
                    binding.statsText.text = "Current user:${currentUser.email}, role:${currentUser.role}, password:${currentUser.password}"
                    if(currentUser.role=="moderator")
                        binding.insertWordBtn.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun getUserCurrentLearnStatistics(){
        lifecycleScope.launch{
            mLearnStatisticsViewModel.getLearnStatisticsById(currentUser.id!!).collect{
                currentLearnStatistics = it
                if(it!=null) {
                    binding.statsText.text = "Currently learnt: ${it.total_learnt} words"
                } else {
                    Toast.makeText(activity,"На данный момент ни одного слова не изучено", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getUserLearnStats(){
        lifecycleScope.launch {

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        getUserInfo()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        
    }
}
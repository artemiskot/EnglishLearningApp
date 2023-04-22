package com.englishlearningapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.NavGraphNavigator
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.englishlearningapp.data.MyDatabase
import com.englishlearningapp.data.WordDao
import com.englishlearningapp.databinding.ActivityBottomNavBinding
import com.englishlearningapp.ui.Learn.LearnFragment
import com.englishlearningapp.ui.Practice.PracticeFragment
import com.englishlearningapp.ui.Statistics.StatisticsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBinding
    private lateinit var dao: WordDao
    private lateinit var db: MyDatabase
    private lateinit var emailId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailId = intent.getStringExtra("email_id") ?: ""
        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_nav)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_learn, R.id.navigation_practice, R.id.navigation_stats
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)

        // Custom OnNavigationItemSelectedListener
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_stats -> {
                    val bundle = Bundle().apply {
                        putString("email_id", emailId)
                    }
                    val statsFragment = StatisticsFragment()
                    statsFragment.arguments = bundle
                    navController.navigate(R.id.navigation_stats, bundle)
                    true
                }
                R.id.navigation_learn -> {
                    val bundle = Bundle().apply {
                        putString("email_id", emailId)
                    }
                    val statsFragment = LearnFragment()
                    statsFragment.arguments = bundle
                    navController.navigate(R.id.navigation_learn, bundle)
                    true
                }
                R.id.navigation_practice -> {
                    val bundle = Bundle().apply {
                        putString("email_id", emailId)
                    }
                    val statsFragment = PracticeFragment()
                    statsFragment.arguments = bundle
                    navController.navigate(R.id.navigation_practice, bundle)
                    true
                }
                else -> {
                    navController.navigate(item.itemId)
                    true
                }
            }
        }


    }
}
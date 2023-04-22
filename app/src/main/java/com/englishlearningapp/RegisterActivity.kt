package com.englishlearningapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.englishlearningapp.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_login_email
import kotlinx.android.synthetic.main.activity_register.et_login_password

class RegisterActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userDao = MyDatabase.getInstance(this).userDao()
        userRepository = UserRepository(userDao)
        userViewModel = UserViewModel(userRepository)
        tv_login.setOnClickListener {
            onBackPressed()
        }

        btn_register.setOnClickListener {
            when{
                TextUtils.isEmpty(et_login_email.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                                "Please enter email",
                                Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_login_password.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                                    val email: String = et_login_email.text.toString().trim{ it <= ' '}
                                    val password: String = et_login_password.text.toString().trim{ it <= ' '}

                                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                val firebaseuser: FirebaseUser = task.result!!.user!!

                                                Toast.makeText(
                                                    this@RegisterActivity,
                                                    "Registered succesfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                val newUser = User(email = email, password = password, role = "user")
                                userViewModel.insertUser(newUser)
                                intent.putExtra("password",password)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else{
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }

    }
}
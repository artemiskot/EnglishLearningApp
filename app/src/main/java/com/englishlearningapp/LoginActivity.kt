package com.englishlearningapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.englishlearningapp.data.MyDatabase
import com.englishlearningapp.data.UserDao
import com.englishlearningapp.data.UserRepository
import com.englishlearningapp.data.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userDao = MyDatabase.getInstance(this).userDao()
        userRepository = UserRepository(userDao)
        userViewModel = UserViewModel(userRepository)
        val etLogin = findViewById<EditText>(R.id.et_login_email)
        etLogin.setText("moderator@moderator.com")
        val etPass = findViewById<EditText>(R.id.et_login_password)
        etPass.setText("moderator")
        tv_register_new.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
            btn_login.setOnClickListener {
                when {
                    TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' ' }) -> {
                        Toast.makeText(
                            this@LoginActivity,
                            "Please enter email",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' ' }) -> {
                        Toast.makeText(
                            this@LoginActivity,
                            "Please enter password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else ->
                    {
                        val email: String = et_login_email.text.toString().trim { it <= ' ' }
                        val password: String = et_login_password.text.toString().trim { it <= ' ' }

                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val firebaseuser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Logged in succesfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseuser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
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


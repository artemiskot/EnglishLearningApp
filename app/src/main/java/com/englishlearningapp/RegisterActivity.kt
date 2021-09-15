package com.englishlearningapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_login_email
import kotlinx.android.synthetic.main.activity_register.et_login_password
import kotlinx.android.synthetic.main.activity_register.tv_register


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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
                                intent.putExtra("user_id",firebaseuser.uid)
                                intent.putExtra("email_id", firebaseuser.email)
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
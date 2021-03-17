package com.eureka.eurekaapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.eureka.eurekaapp.MainActivity
import com.eureka.eurekaapp.R
import com.eureka.eurekaapp.base.BaseActivity
import com.eureka.eurekaapp.databinding.ActivityLoginBinding
import com.eureka.eurekaapp.signup.SignUpActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        super.onCreate(savedInstanceState)

        onClickListener()
    }

    private fun onClickListener() {
        binding.btnLogin.setOnClickListener {
//            login()
//            loginUser()
            showToast("Test")
        }
        binding.btnSignUp.setOnClickListener {
            intent<SignUpActivity>(this)
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.etEmail.error = "Please input your email"
            binding.etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Email is not valid"
            binding.etEmail.requestFocus()
            return
        }

        if (password.isEmpty() || password.length < 8) {
            binding.etPassword.error = "Password must be more than 8 characters"
            binding.etPassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Login Successfull!")
                intent<MainActivity>(this)
                finish()
            } else {
                showToast("Login failed!")
            }
        }
    }

    private fun showToast(message : String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}
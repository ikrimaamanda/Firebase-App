package com.eureka.eurekaapp.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.eureka.eurekaapp.MainActivity
import com.eureka.eurekaapp.R
import com.eureka.eurekaapp.base.BaseActivity
import com.eureka.eurekaapp.databinding.ActivityLoginBinding
import com.eureka.eurekaapp.forgotpassword.ForgotPasswordFragment
import com.eureka.eurekaapp.signup.SignUpActivity


class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var fragment : ForgotPasswordFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        super.onCreate(savedInstanceState)

        setView()
        onClickListener()

    }

    private fun setView() {
        binding.svScrollView.isVerticalScrollBarEnabled = false
    }

    private fun callFragment(fragment: ForgotPasswordFragment) {
        binding.flContainer.visibility = View.VISIBLE
//        binding.btnLogin.visibility = View.GONE
//        binding.tvSignUp.visibility = View.GONE
//        binding.tvForgotPassword.visibility = View.GONE

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_OPEN
        ).commit()
    }

    private fun onClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.checkPassword.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                // show password
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                // hide password
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }

        binding.btnLogin.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            loginUser()
        }

        binding.tvSignUp.setOnClickListener {
            intent<SignUpActivity>(this)
        }

        binding.tvForgotPassword.setOnClickListener {
                fragment = ForgotPasswordFragment()
                callFragment(fragment)
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.etEmail.error = "Please input your email"
            binding.etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.progressBar.visibility = View.GONE
            binding.etEmail.error = "Email is not valid"
            binding.etEmail.requestFocus()
            return
        }

        if (password.isEmpty() || password.length < 8) {
            binding.progressBar.visibility = View.GONE
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
                binding.progressBar.visibility = View.GONE
                showToast("Login failed!")
            }
        }
    }

}
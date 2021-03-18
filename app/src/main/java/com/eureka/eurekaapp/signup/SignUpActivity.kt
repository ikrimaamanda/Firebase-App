package com.eureka.eurekaapp.signup

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import com.eureka.eurekaapp.R
import com.eureka.eurekaapp.base.BaseActivity
import com.eureka.eurekaapp.databinding.ActivitySignUpBinding
import com.eureka.eurekaapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {

    private val gender = arrayOf("Choose Gender", "Perempuan", "Laki-laki")

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_sign_up
        super.onCreate(savedInstanceState)

        setView()
        configSpinnerGender()
        onClickListener()
    }

    private fun setView() {
        binding.svScrollView.isVerticalScrollBarEnabled = false
    }


    private fun configSpinnerGender() {
            binding.spinnerGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender)
    }

    private fun onClickListener() {
        binding.checkPassword.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                // show password
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                // hide password
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }

        binding.btnSignUp.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            signUpUser()
        }

        binding.btnLogin.setOnClickListener {
            intent<LoginActivity>(this)
        }
    }

    private fun signUpUser() {

        val fullName = binding.etFullname.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val province = binding.etProvinsi.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val userGender = binding.spinnerGender.selectedItem.toString()

        if (fullName.isEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.etFullname.error = "Please input your full name"
            binding.etFullname.requestFocus()
            return
        }

        if (city.isEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.etCity.error = "Please input your city"
            binding.etCity.requestFocus()
            return
        }

        if (province.isEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.etProvinsi.error = "Please input your province"
            binding.etProvinsi.requestFocus()
            return
        }

        if (phone.isEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.etPhone.error = "Please input your phone number"
            binding.etPhone.requestFocus()
            return
        }

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

        if ( userGender == "Choose Gender") {
            binding.progressBar.visibility = View.GONE
            showToast("Please choose a gender")
            binding.spinnerGender.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = SignUpModel(fullName, userGender, city, province, phone, email, password)
                    databaseReference.child(FirebaseAuth.getInstance().currentUser.uid)
                            .setValue(user)
                            .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showToast("Register Succcessfull!")
                                intent<LoginActivity>(this)
                                finish()
                            } else {
                                showToast("Failed to register! Try again!")
                            }
                        }
                } else {
                    binding.progressBar.visibility = View.GONE
                    showToast("Failed to register!")
                }
            }
    }
}
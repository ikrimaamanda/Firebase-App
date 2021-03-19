package com.eureka.eurekaapp.signup

import android.graphics.PorterDuff
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
    private val userType = arrayOf("Choose Type of User","Student", "Teacher")
    private val major = arrayOf("Choose Major", "Science", "Social", "Language")
    private val grade = arrayOf("Choose Grade", "Grade 10", "Grade 11", "Grade 12")

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_sign_up
        super.onCreate(savedInstanceState)

        setView()
        configSpinner()
        onClickListener()
    }

    private fun setView() {
        binding.svScrollView.isVerticalScrollBarEnabled = false
    }


    private fun configSpinner() {
        binding.spinnerGender.background.setColorFilter(resources.getColor(R.color.soft_purple), PorterDuff.Mode.SRC_ATOP);
        binding.spinnerGender.adapter = ArrayAdapter(this, R.layout.spinner_item, gender)

        binding.spinnerUserType.background.setColorFilter(resources.getColor(R.color.soft_purple), PorterDuff.Mode.SRC_ATOP);
        binding.spinnerUserType.adapter = ArrayAdapter(this, R.layout.spinner_item, userType)

        binding.spinnerMajor.background.setColorFilter(resources.getColor(R.color.soft_purple), PorterDuff.Mode.SRC_ATOP);
        binding.spinnerMajor.adapter = ArrayAdapter(this, R.layout.spinner_item, major)

        binding.spinnerGrade.background.setColorFilter(resources.getColor(R.color.soft_purple), PorterDuff.Mode.SRC_ATOP);
        binding.spinnerGrade.adapter = ArrayAdapter(this, R.layout.spinner_item, grade)
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

        binding.btnSignUp.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            signUpUser()
        }

        binding.tvSignIn.setOnClickListener {
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
        val schoolName = binding.etSchoolName.text.toString().trim()
        val userType = binding.spinnerUserType.selectedItem.toString()
        val userMajor = binding.spinnerMajor.selectedItem.toString()
        val userGrade = binding.spinnerGrade.selectedItem.toString()

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

        if ( userGender == "Choose Type of User") {
            binding.progressBar.visibility = View.GONE
            showToast("Please choose type of user")
            binding.spinnerUserType.requestFocus()
            return
        }

        if (schoolName.isEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.etSchoolName.error = "Please input your school name"
            binding.etSchoolName.requestFocus()
            return
        }

        if ( userType == "Choose Type of User") {
            binding.progressBar.visibility = View.GONE
            showToast("Please choose type of user")
            binding.spinnerUserType.requestFocus()
            return
        }

        if ( userMajor == "Choose Major") {
            binding.progressBar.visibility = View.GONE
            showToast("Please choose major")
            binding.spinnerMajor.requestFocus()
            return
        }

        if ( userGrade == "Choose Grade") {
            binding.progressBar.visibility = View.GONE
            showToast("Please choose your grade")
            binding.spinnerGrade.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userProfile = ProfileDataModel(fullName, userGender, city, province, phone, email)
                    val userSchool = SchoolDataModel(schoolName, userType, userMajor, userGrade)
                    databaseReference.child("Users").child(FirebaseAuth.getInstance().currentUser.uid).child("Profile")
                            .setValue(userProfile)
                            .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showToast("Register Successfully!")
                                intent<LoginActivity>(this)
                                finish()
                            } else {
                                showToast("Failed to register! Try again!")
                            }
                        }

                    databaseReference.child("Users").child(FirebaseAuth.getInstance().currentUser.uid).child("School")
                            .setValue(userSchool)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    showToast("Register Successfully!")
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
package com.eureka.eurekaapp.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.eureka.eurekaapp.MainActivity
import com.eureka.eurekaapp.R
import com.eureka.eurekaapp.base.BaseActivity
import com.eureka.eurekaapp.databinding.ActivitySignUpBinding
import com.eureka.eurekaapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_sign_up
        super.onCreate(savedInstanceState)

        onClickListener()
    }

    private fun onClickListener() {
        binding.btnSignUp.setOnClickListener {
//            registerUser()
            showToast("Test")
        }
        binding.btnLogin.setOnClickListener {
            intent<LoginActivity>(this)
        }
    }

    private fun registerUser() {

        val fullName = binding.etFullname.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val province = binding.etProvinsi.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (fullName.isEmpty()) {
            binding.etFullname.error = "Please input your full name"
            binding.etFullname.requestFocus()
            return
        }

        if (city.isEmpty()) {
            binding.etCity.error = "Please input your city"
            binding.etCity.requestFocus()
            return
        }

        if (province.isEmpty()) {
            binding.etProvinsi.error = "Please input your province"
            binding.etProvinsi.requestFocus()
            return
        }

        if (phone.isEmpty()) {
            binding.etPhone.error = "Please input your phone number"
            binding.etPhone.requestFocus()
            return
        }

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

//        val id = databaseReference.push().key

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = SignUpModel(fullName, city, province, phone, email)
                    databaseReference.child(FirebaseAuth.getInstance().currentUser.uid)
                        .setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                showToast("Register Succcessfull!")
                                intent<MainActivity>(this)
                            } else {
                                showToast("Failed to register! Try again!")
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    showToast("Failed to register!")
                }

            }
    }

    private fun showToast(message : String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
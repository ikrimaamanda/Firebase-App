package com.eureka.eurekaapp.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.eureka.eurekaapp.R
import com.eureka.eurekaapp.databinding.FragmentForgotPasswordBinding
import com.eureka.eurekaapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding : FragmentForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        onClikListener()

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun onClikListener() {
        binding.btnForgotPassword.setOnClickListener {
            sendEmailToResetPassword()
        }
    }

    private fun sendEmailToResetPassword() {
        val email = binding.etEmail.text.toString().trim()


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

        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Check your email to reset your password")
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

    }

    private fun showToast(message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
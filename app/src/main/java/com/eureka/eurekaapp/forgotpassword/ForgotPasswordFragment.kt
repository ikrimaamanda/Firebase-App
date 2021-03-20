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
import androidx.lifecycle.ViewModelProvider
import com.eureka.eurekaapp.R
import com.eureka.eurekaapp.databinding.FragmentForgotPasswordBinding
import com.eureka.eurekaapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding : FragmentForgotPasswordBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel : ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        viewModel.setFirebaseAuth(auth)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        onClickListener()
        subscribeForgotPasswordLiveData()

        return binding.root
    }


    override fun onPause() {
        super.onPause()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun onClickListener() {
        binding.btnForgotPassword.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            sendEmailToResetPassword()
        }
    }

    private fun sendEmailToResetPassword() {
        val email = binding.etEmail.text.toString().trim()

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

        viewModel.setForgotPassword(email)

//        auth.sendPasswordResetEmail(email).addOnCompleteListener {
//            if (it.isSuccessful) {
//                showToast("Check your email to reset your password")
//                val intent = Intent(requireContext(), LoginActivity::class.java)
//                startActivity(intent)
//                activity?.finish()
//            } else {
//                binding.progressBar.visibility = View.GONE
//                showToast("Email is not registered!")
//            }
//        }
    }

    private fun subscribeForgotPasswordLiveData() {
        viewModel.isEmailSend.observe(this, {
            if (it) {
                showToast("Check your email to reset your password")
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                binding.progressBar.visibility = View.GONE
                showToast("Email is not registered!")
            }
        })
    }

    private fun showToast(message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
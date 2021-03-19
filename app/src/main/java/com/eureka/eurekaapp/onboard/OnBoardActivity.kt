package com.eureka.eurekaapp.onboard

import android.os.Bundle
import com.eureka.eurekaapp.R
import com.eureka.eurekaapp.base.BaseActivity
import com.eureka.eurekaapp.databinding.ActivityOnBoardBinding
import com.eureka.eurekaapp.login.LoginActivity
import com.eureka.eurekaapp.signup.SignUpActivity

class OnBoardActivity : BaseActivity<ActivityOnBoardBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_on_board
        super.onCreate(savedInstanceState)

        onClickListener()
    }

    private fun onClickListener() {
        binding.btnLogin.setOnClickListener {
            intent<LoginActivity>(this)
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            intent<SignUpActivity>(this)
            finish()
        }

    }
}
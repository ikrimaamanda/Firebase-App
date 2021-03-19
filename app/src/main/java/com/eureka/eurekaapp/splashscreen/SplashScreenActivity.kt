package com.eureka.eurekaapp.splashscreen

import android.os.Bundle
import android.os.Handler
import com.eureka.eurekaapp.MainActivity
import com.eureka.eurekaapp.R
import com.eureka.eurekaapp.`interface`.UpdateUserID
import com.eureka.eurekaapp.base.BaseActivity
import com.eureka.eurekaapp.databinding.ActivitySplashScreenBinding
import com.eureka.eurekaapp.onboard.OnBoardActivity
import com.google.firebase.auth.FirebaseUser

class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>(), UpdateUserID {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_splash_screen
        super.onCreate(savedInstanceState)

        val        currentUser = auth.currentUser

        val handler = Handler(mainLooper)
        handler.postDelayed({
                            updateUI(currentUser)
        }, 3000)
    }

    override fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            showToast("Welcome!")
            intent<MainActivity>(this)
            finish()
        } else {
            intent<OnBoardActivity>(this)
            finish()
        }
    }

}
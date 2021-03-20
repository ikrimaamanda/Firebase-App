package com.eureka.eurekaapp.forgotpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ForgotPasswordViewModel : ViewModel(), CoroutineScope {

    val isEmailSend = MutableLiveData<Boolean>()
    private lateinit var auth : FirebaseAuth

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setFirebaseAuth(auth : FirebaseAuth) {
        this.auth = auth
    }

    fun setForgotPassword(email : String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                auth.sendPasswordResetEmail(email)
            }

            result.addOnCompleteListener {
                isEmailSend.value = it.isSuccessful
            }
        }
    }
}
package com.eureka.eurekaapp.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope {

    val isLoginLiveData = MutableLiveData<Boolean>()
    private lateinit var auth: FirebaseAuth

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setFirebaseAuth(auth : FirebaseAuth) {
        this.auth = auth
    }

    fun setFirebase(email : String, password: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                    auth.signInWithEmailAndPassword(email, password)
            }

            result.addOnCompleteListener{
                isLoginLiveData.value = it.isSuccessful
            }
        }
    }
}
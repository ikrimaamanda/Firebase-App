package com.eureka.eurekaapp.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SignUpViewModel : ViewModel(), CoroutineScope {

    val isSignUpLiveData = MutableLiveData<Boolean>()
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference : DatabaseReference

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setFirebaseAuth(auth : FirebaseAuth) {
        this.auth = auth
    }

    fun setDatabaseReference(databaseReference : DatabaseReference) {
        this.databaseReference = databaseReference
    }

    fun setSignUpUser(email : String, password : String, fullName : String, userGender : String, city : String, province : String, phone : String, schoolName : String, userType : String, userMajor : String, userGrade : String ) {
        launch {
            val result = withContext(Dispatchers.IO) {
                auth.createUserWithEmailAndPassword(email, password)
            }

            result.addOnCompleteListener {
                if (it.isSuccessful) {
                    val userProfile = ProfileDataModel(fullName, userGender, city, province, phone, email, schoolName, userType, userMajor, userGrade)
                    databaseReference.child("Users").child(FirebaseAuth.getInstance().currentUser.uid).child("Profile")
                            .setValue(userProfile)
                            .addOnCompleteListener {task->
                                isSignUpLiveData.value = task.isSuccessful
                            }
                } else {
                    isSignUpLiveData.value = false
                }
            }
        }
    }
}
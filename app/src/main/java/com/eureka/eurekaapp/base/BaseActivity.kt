package com.eureka.eurekaapp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


abstract class BaseActivity<ActivityBinding : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding : ActivityBinding
    protected var setLayout = 0
    protected lateinit var database : FirebaseDatabase
    protected lateinit var databaseReference : DatabaseReference
    protected lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, setLayout)

        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance("https://my-application-307c4-default-rtdb.firebaseio.com/")
        databaseReference = FirebaseDatabase.getInstance().getReference("Eureka App").child("Users")
    }

    protected inline fun <reified ActivityClass> intent(context: Context) {
        context.startActivity(Intent(context, ActivityClass::class.java))
    }

//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser: FirebaseUser = auth.getCurrentUser()
//        updateUI(currentUser)
//    }
//
//    private fun updateUI(currentUser: FirebaseUser) {
//
//    }

}
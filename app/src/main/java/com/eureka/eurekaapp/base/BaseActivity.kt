package com.eureka.eurekaapp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.firebase.auth.FirebaseAuth
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
        databaseReference = FirebaseDatabase.getInstance().getReference("Eureka App").child("Users")
    }

    protected inline fun <reified ActivityClass> intent(context: Context) {
        context.startActivity(Intent(context, ActivityClass::class.java))
    }

    protected fun showToast(message : String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

}
package com.eureka.eurekaapp.maincontent

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.eureka.eurekaapp.R
import com.eureka.eurekaapp.base.BaseActivity
import com.eureka.eurekaapp.databinding.ActivityMainBinding
import com.eureka.eurekaapp.login.LoginActivity
import com.eureka.eurekaapp.signup.ProfileDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main
        super.onCreate(savedInstanceState)

        setData()
        onClickListener()
    }

    private fun setData() {
        databaseReference.child("Users").child(FirebaseAuth.getInstance().currentUser.uid).child("Profile").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(ProfileDataModel::class.java)
                binding.progressBar.visibility = View.VISIBLE

                if (userProfile != null) {
                    binding.tvEmail.text = userProfile.email
                    binding.tvName.text = userProfile.fullName
                    binding.tvSchool.text = userProfile.schoolName
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Something wrong happend...")
            }

        })

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        showToast("Please click BACK again to exit")
        Handler(mainLooper).postDelayed( { doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun onClickListener() {
        binding.btnLogOut.setOnClickListener {
            auth.signOut()
            intent<LoginActivity>(this)
            finish()
        }
    }
}
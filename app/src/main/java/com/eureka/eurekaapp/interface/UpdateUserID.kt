package com.eureka.eurekaapp.`interface`

import com.google.firebase.auth.FirebaseUser

interface UpdateUserID {
    fun updateUI(currentUser: FirebaseUser?)
}
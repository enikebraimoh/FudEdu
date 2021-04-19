package com.enike.fudedu.UI.Registeration.StudentReg

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class StudentRegViewModel : ViewModel() {

    var userAuth = Firebase.auth
    private lateinit var database: DatabaseReference


    fun isEmailCorrect(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun createAccountWithEMailPassword(email: String, password: String, model: StudentsDetailsModel) {
        userAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d("eshoo", "createUserWithEmail:success")
                        saveStudentsDetails(model)
                    } else {
                        Log.d("eshoo", "createUserWithEmail:failure", task.exception)
                    }
                }
    }

    fun saveStudentsDetails(details: StudentsDetailsModel) {
        database = Firebase.database.reference
        database.child("users").child(userAuth.currentUser?.uid!!).setValue(details)
    }


}
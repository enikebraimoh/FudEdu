package com.enike.fudedu.ui.login

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enike.fudedu.utils.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null
    var mDatastate: DataState? = null


    // database Init
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    // errors fields
    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError
    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> get() = _emailError

    val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> get() = _loggedIn


    fun loggedIn() {
        _loggedIn.value = firebaseAuth.currentUser != null
    }


    fun login() {
        if (verifyEmail()) {
            if (verifyPassword()) {
                mDatastate?.let { datastate ->
                    datastate.loading()
                    firebaseAuth.signInWithEmailAndPassword(email!!, password!!)
                        .addOnCompleteListener() { task ->
                            if (task.isSuccessful) {
                                    Log.d("eshoo", "signInUserWithEmail:success")
                                    whoAreYou(firebaseAuth.currentUser.uid)
                                } else {
                                    datastate.error(task.exception?.localizedMessage.toString())
                                    Log.d("eshoo", "signInUserWithEmail:failure", task.exception)
                                }
                            }
                    }
            }
        }
    }

    private fun whoAreYou(id: String) {
        val none: DataState? = null
        Log.d("eshoo", "am in whoAreYou method")
        mDatastate?.let { datastate ->
            database.child("Lecturers Information").addValueEventListener(object :
                    ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (postSnapshot in dataSnapshot.children) {
                        if (dataSnapshot.hasChild(id)) {

                            // you are a Lecturer
                            datastate.success("Lecturer")

                            break
                        } else {

                            // you are a Student
                            datastate.success("Student")
                            break
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                    datastate.error(databaseError.message)
                }
            })
        }
    }

    private fun verifyEmail(): Boolean {
        return if (email == null || email == "") {
            _emailError.value = "field must not be blank"
            false
        } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailError.value = null
            true
        } else {
            _emailError.value = "invalid email"
            false
        }
    }

    private fun verifyPassword(): Boolean {
        return if (password == null || password == "") {
            _passwordError.value = "Password field cannot be blank"
            false
        } else {
            _passwordError.value = null
            true
        }
    }


}
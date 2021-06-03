package com.enike.fudedu.ui.lecturerReg

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enike.fudedu.network.models.LecturerDetailsModel
import com.enike.fudedu.network.models.StudentsModel
import com.enike.fudedu.utils.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LecturerRegViewModel : ViewModel() {
    // database initialization
    private val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private var database: DatabaseReference = Firebase.database.reference

    //error live data
    private val _firstNameError = MutableLiveData<String>()
    val firstNameError: LiveData<String> get() = _firstNameError
    private val _lastNameError = MutableLiveData<String>()
    val lastNameError: LiveData<String> get() = _lastNameError
    private val _phoneNumberError = MutableLiveData<String>()
    val phoneNumberError: LiveData<String> get() = _phoneNumberError
    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> get() = _emailError
    private val _genderError = MutableLiveData<String>()
    val genderError: LiveData<String> get() = _genderError
    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError
    private val _confirmPasswordError = MutableLiveData<String>()
    val confirmPasswordError: LiveData<String> get() = _confirmPasswordError

    // input fields
    var dataState: DataState? = null
    var firstname: String? = null
    var lastname: String? = null
    var phonenumber: String? = null
    var email: String? = null
    var gender: String? = null
    var password: String? = null
    var confirmPassword: String? = null

    private fun verifyEmail(): Boolean {
        return if (email == "" || email == null) {
            _emailError.value = "field must not be blank"
            false
        } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email!!).matches()) {
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
        } else if (password!!.length < 6) {
            _passwordError.value = "Password field must have at least 6 characters"
            false
        } else {
            _passwordError.value = null
            true
        }
    }

    private fun confirmPassword(): Boolean {
        return if (password == confirmPassword) {
            _confirmPasswordError.value = null
            true
        } else {
            _confirmPasswordError.value = "doesn't match password field"
            false
        }
    }

    private fun validateFirstName(): Boolean {
        return if (firstname == null || firstname!! == "") {
            _firstNameError.value = "this field cannot be left blank"
            false
        } else if (firstname!!.contains("[0-9]".toRegex())) {
            _firstNameError.value = "name cannot contain numbers"
            false
        } else if ((firstname!!.contains("[^A-Za-z0-9 ]".toRegex()))) {
            _firstNameError.value = "name cannot contain special characters"
            false
        } else if (firstname!!.contains("\\s+".toRegex())) {
            _firstNameError.value = "name cannot contain white space"
            false
        } else {
            _firstNameError.value = null
            true
        }
    }

    private fun validateLastName(): Boolean {
        return if (lastname == null || lastname == "") {
            _lastNameError.value = "this field cannot be left blank"
            false
        } else if (lastname!!.contains("[0-9]".toRegex())) {
            _lastNameError.value = "name cannot contain numbers"
            false
        } else if ((lastname!!.contains("[^A-Za-z0-9 ]".toRegex()))) {
            _lastNameError.value = "name cannot contain special characters"
            false
        } else if (lastname!!.contains("\\s+".toRegex())) {
            _lastNameError.value = "name cannot contain white space"
            false
        } else {
            _lastNameError.value = null
            true
        }
    }

    private fun validatePhoneNUmber(): Boolean {
        return if (phonenumber == "" || phonenumber == null) {
            _phoneNumberError.value = "this field cannot be left blank"
            false
        } else if (phonenumber!!.length < 11) {
            _phoneNumberError.value = "invalid phone number"
            false
        } else {
            _phoneNumberError.value = null
            true
        }

    }

    private fun validateGender(): Boolean {
        return if (gender == null || gender == "") {
            _genderError.value = "select your Gender"
            false
        } else {
            _genderError.value = null
            true
        }
    }

    fun registerButton() {
        if (validateFirstName()) {
            if (validateLastName()) {
                if (validatePhoneNUmber()) {
                    if (verifyEmail()) {
                        if (validateGender()) {
                            if (verifyPassword()) {
                                if (confirmPassword()) {
                                    databaseOperations()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun databaseOperations() {
        dataState?.let { dataState ->
            dataState.loading()
            val model = LecturerDetailsModel(
                phonenumber!!, firstname!!, lastname!!, email!!, gender!!, password!!
            )

            userAuth.createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d("eshoo", "createUserWithEmail:success")
                        database.child("Lecturers Information")
                            .child(userAuth.currentUser?.uid!!).setValue(model)
                        dataState.success("Account created Successfully")
                    } else {
                        Log.d("eshoo", "createUserWithEmail:failure", task.exception)
                        dataState.error(task.exception?.localizedMessage!!)
                    }
                }
        }
    }


}
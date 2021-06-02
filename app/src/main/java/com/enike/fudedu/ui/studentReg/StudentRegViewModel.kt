package com.enike.fudedu.ui.studentReg

import android.app.Application
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import com.enike.fudedu.R
import com.enike.fudedu.network.StudentsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StudentRegViewModel(application: Application) : AndroidViewModel(application) {

    // database initialization
    private val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private var database: DatabaseReference = Firebase.database.reference


    val selectGender = application.resources.getStringArray(R.array.Gender)
    val faculties = application.resources.getStringArray(R.array.Faculties)
    val genderArrayAdapter =
        ArrayAdapter(application.applicationContext, R.layout.dropdown_item, selectGender)
    val facultyArrayAdapter =
        ArrayAdapter(application.applicationContext, R.layout.dropdown_item, faculties)


    var groupcode: String? = null
    var firstname: String? = null
    var lastname: String? = null
    var phonenumber: String? = null
    var email: String? = null
    var gender: String? = null
    var faculty: String? = null
    var department: String? = null
    var password: String? = null
    var confirmPassword: String? = null


    private fun saveStudentsDetails(details: StudentsModel) {
        database.child("Students Information").child(userAuth.currentUser?.uid!!).setValue(details)

    }

    /* private fun createAccountWithEMailPassword(
         email: String, password: String, model: StudentsModel) {
         userAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener() { task ->
                 if (task.isSuccessful) {
                     Log.d("eshoo", "createUserWithEmail:success")
                     saveStudentsDetails(model)
                     binding.spinKit.visibility = View.INVISIBLE
                     binding.registerStudentsBnt.visibility = View.VISIBLE
                     showSnackBar("Account Created Successfully ")

                 } else {
                     Log.d("eshoo", "createUserWithEmail:failure", task.exception)
                     binding.spinKit.visibility = View.INVISIBLE
                     binding.registerStudentsBnt.visibility = View.VISIBLE
                     showSnackBar(task.exception?.message!!)
                 }
             }
     }

     private fun createAnAccount(email: String, confirmPassword: String) {
         val phonenumber = binding.phonenumberfield.text.toString()
         val firstname = binding.firstnamefield.text.toString()
         val lastname = binding.lastnamefield.text.toString()
         val gender = binding.genderfield.text.toString()
         val faculty = binding.facultyfield.text.toString()
         val department = binding.departmentfield.text.toString()
         val password = binding.passwordfield.text.toString()
         val studentModel = StudentsModel(
             phonenumber,
             firstname,
             lastname,
             email,
             gender,
             faculty,
             department,
             password
         )
         createAccountWithEMailPassword(email, confirmPassword, studentModel)
     }*/


}
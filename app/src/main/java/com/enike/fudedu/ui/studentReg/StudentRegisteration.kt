package com.enike.fudedu.ui.studentReg

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.enike.fudedu.R
import com.enike.fudedu.databinding.FragmentStudentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StudentRegisteration : Fragment() {

    private lateinit var binding: FragmentStudentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_student_register, container, false)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = StudentRegFactory(application)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(StudentRegViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { view ->
            binding.toolbar.findNavController()?.popBackStack()
        }

        /* binding.registerStudentsBnt.setOnClickListener {
             val email = binding.emailfield.text.toString()
             val password = binding.passwordfield.text.toString()
             val confirmPassword = binding.confirmpasswordfield.text.toString()
             val firstName = binding.firstnamefield.text.toString()
             val lastName = binding.lastnamefield.text.toString()
             val phoneNumber = binding.phonenumberfield.text.toString()
             val gender = binding.genderfield.text.toString()
             val faculty = binding.facultyfield.text.toString()
             val dpt = binding.departmentfield.text.toString()
             if (validateFirstName(firstName)) {
                 if (validateLastName(lastName)) {
                     if (validatePhoneNUmber(phoneNumber)) {
                         if (isEmailCorrect(email)) {
                             if (validateGender(gender)) {
                                 if (validateFaculty(faculty)) {
                                     if (validateDepartment(dpt)) {
                                         if (validatePassword(password, confirmPassword)) {
                                             binding.spinKit.visibility = View.VISIBLE
                                             binding.registerStudentsBnt.visibility = View.INVISIBLE
                                             createAnAccount(email, confirmPassword)
                                         }
                                     }
                                 }
                             }
                         }
                     }
                 }
             }
         }*/

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireContext(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun isEmailCorrect(email: String): Boolean {
        return if (email == "") {
            binding.Email.isErrorEnabled = true
            binding.Email.error = "field must not be blank"
            false
        } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.Email.isErrorEnabled = false
            true

        } else {
            binding.Email.isErrorEnabled = true
            binding.Email.error = "invalid email"
            false
        }
    }

    private fun validateFaculty(faculty: String): Boolean {
        return if (faculty != "") {
            binding.faculty.isErrorEnabled = false
            true
        } else {
            binding.faculty.error = "select your Faculty"
            binding.faculty.isErrorEnabled = true
            false
        }
    }

    private fun validateGender(gender: String): Boolean {
        return if (gender != "") {
            binding.gender.isErrorEnabled = false
            true
        } else {
            binding.gender.error = "select your Gender"
            binding.gender.isErrorEnabled = true
            false
        }
    }

    private fun validatePassword(password: String, confirmPassword: String): Boolean {
        return if (password == "") {
            binding.firstPassword.isErrorEnabled = true
            binding.firstPassword.error = "Password field cannot be blank"
            false
        } else if (password.length < 6) {
            binding.firstPassword.isErrorEnabled = true
            binding.firstPassword.error = "Password field must have at least 6 characters"
            false
        } else if (password == confirmPassword) {
            binding.firstPassword.isErrorEnabled = false
            binding.confirmPassword.isErrorEnabled = false
            true
        } else {
            binding.firstPassword.isErrorEnabled = false
            binding.confirmPassword.isErrorEnabled = true
            binding.confirmPassword.error = "doesn't match password field"
            false
        }
    }

    private fun validateFirstName(name: String): Boolean {
        return if (name == "") {
            binding.firstname.isErrorEnabled = true
            binding.firstname.error = "this field cannot be left blank"
            false
        } else if (name.contains("[0-9]".toRegex())) {
            binding.firstname.isErrorEnabled = true
            binding.firstname.error = "name cannot contain numbers"
            false
        } else if ((name.contains("[^A-Za-z0-9 ]".toRegex()))) {
            binding.firstname.isErrorEnabled = true
            binding.firstname.error = "name cannot contain special characters"
            false
        } else if (name.contains("\\s+".toRegex())) {
            binding.firstname.isErrorEnabled = true
            binding.firstname.error = "name cannot contain white space"
            false
        } else {
            binding.firstname.isErrorEnabled = false
            true
        }

    }

    private fun validateLastName(name: String): Boolean {
        return if (name == "") {
            binding.lastName.isErrorEnabled = true
            binding.lastName.error = "this field cannot be left blank"
            false
        } else if (name.contains("[0-9]".toRegex())) {
            binding.lastName.isErrorEnabled = true
            binding.lastName.error = "name cannot contain numbers"
            false
        } else if ((name.contains("[^A-Za-z0-9 ]".toRegex()))) {
            binding.lastName.isErrorEnabled = true
            binding.lastName.error = "name cannot contain special characters"
            false
        } else if (name.contains("\\s+".toRegex())) {
            binding.lastName.isErrorEnabled = true
            binding.lastName.error = "name cannot contain white space"
            false
        } else {
            binding.lastName.isErrorEnabled = false
            true
        }

    }

    private fun validatePhoneNUmber(phone: String): Boolean {
        return if (phone == "") {
            binding.phonenumber.isErrorEnabled = true
            binding.phonenumber.error = "this field cannot be left blank"
            false
        } else if (phone.length < 11) {
            binding.phonenumber.isErrorEnabled = true
            binding.phonenumber.error = "invalid phone number"
            false
        } else {
            binding.phonenumber.isErrorEnabled = false
            true
        }

    }

    private fun validateDepartment(dept: String): Boolean {
        return if (dept == "") {
            binding.department.isErrorEnabled = true
            binding.department.error = "this field cannot be left blank"
            false
        } else {
            binding.department.isErrorEnabled = false
            true
        }
    }
}

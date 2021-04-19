package com.enike.fudedu.UI.Registeration.StudentReg

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.enike.fudedu.R
import com.enike.fudedu.databinding.FragmentStudentRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class StudentRegisteration : Fragment() {

    private lateinit var binding: FragmentStudentRegisterBinding

    private val studentViewModel: StudentRegViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_register, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { view ->
            binding.toolbar.findNavController()?.popBackStack()

        }

        binding.registerStudentsBnt.setOnClickListener {
            val email = binding.emailfield.text.toString()
            val password = binding.passwordfield.text.toString()
            val confirmPassword = binding.confirmpasswordfield.text.toString()
            if (studentViewModel.isEmailCorrect(email)) {
                binding.Email.isErrorEnabled = false
                if (studentViewModel.validatePassword(password, confirmPassword)) {
                    binding.confirmPassword.isErrorEnabled = false
                    createAnAccount(email, confirmPassword)
                } else {
                    binding.confirmPassword.isErrorEnabled = true
                    binding.confirmPassword.error = "password fields must match"
                }
            } else {
                binding.Email.isErrorEnabled = true
                binding.Email.error = "incorrect Email"
            }
        }

    }

    private fun createAnAccount(email: String, confirmPassword: String) {
        val username = binding.usernamefield.text.toString()
        val firstname = binding.firstnamefield.text.toString()
        val lastname = binding.lastnamefield.text.toString()
        val gender = binding.genderfield.text.toString()
        val faculty = binding.facultyfield.text.toString()
        val department = binding.departmentfield.text.toString()
        val password = binding.passwordfield.text.toString()
        val student = StudentsDetailsModel(username, firstname, lastname, email, gender, faculty, department, password)
        studentViewModel.createAccountWithEMailPassword(email, confirmPassword, student)
    }

}
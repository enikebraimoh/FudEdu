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
import com.enike.fudedu.utils.DataState
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StudentRegisteration : Fragment(), DataState {

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
        viewModel.dataState = this

        return binding.root
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireContext(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun loading() {
        binding.registerStudentsBnt.visibility = View.INVISIBLE
        binding.spinKit.visibility = View.VISIBLE
    }

    override fun error(error: String) {
        binding.spinKit.visibility = View.INVISIBLE
        binding.registerStudentsBnt.visibility = View.VISIBLE
        showSnackBar(error)
    }

    override fun <T> success(message: T?) {
        val msg = message as String
        binding.spinKit.visibility = View.INVISIBLE
        binding.registerStudentsBnt.visibility = View.VISIBLE
        showSnackBar(msg)
    }


}

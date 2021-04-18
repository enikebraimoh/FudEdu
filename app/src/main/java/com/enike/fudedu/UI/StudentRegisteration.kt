package com.enike.fudedu.UI

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.enike.fudedu.R
import com.enike.fudedu.databinding.FragmentStudentRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class StudentRegisteration : Fragment() {

    private lateinit var binding: FragmentStudentRegisterBinding
    private lateinit var Aauth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Aauth = Firebase.auth
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
            createAccount(binding.emailfield.text.toString(), binding.passwordfield.text.toString())
        }


    }

    private fun createAccount(email: String, password: String) {
        Toast.makeText(requireContext(), email, Toast.LENGTH_SHORT).show()
        Aauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("eshoo", "createUserWithEmail:success")
                        val user = Aauth.currentUser
                    } else {
                        Log.d("eshoo", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(requireContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

}
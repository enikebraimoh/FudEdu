package com.enike.fudedu.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.enike.fudedu.R
import com.enike.fudedu.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginbtn.setOnClickListener {

        }

        binding.iAmALecturer.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToLecturerRegisteration()
            binding.iAmALecturer.findNavController().navigate(action)
        }

        binding.iAmAStudent.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisteration()
            binding.iAmAStudent.findNavController().navigate(action)
        }

    }



}
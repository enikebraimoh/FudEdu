package com.enike.fudedu.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.enike.fudedu.R
import com.enike.fudedu.utils.DataState
import com.enike.fudedu.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment(), DataState {

    private lateinit var binding: FragmentLoginBinding
    lateinit var model: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        model = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = model
        binding.lifecycleOwner = this
        model.mDatastate = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.iAmALecturer.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToLecturerRegisteration()
            binding.iAmALecturer.findNavController().navigate(action)
        }

        binding.iAmAStudent.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisteration()
            binding.iAmAStudent.findNavController().navigate(action)
        }
    }


    private fun showSnackBar(message: String) {
        Snackbar.make(requireContext(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun loading() {
        binding.loginbtn.visibility = View.INVISIBLE
        binding.spinKit.visibility = View.VISIBLE
    }

    override fun waiting() {
        binding.spinKit.visibility = View.INVISIBLE
        binding.loginbtn.visibility = View.VISIBLE
    }

    override fun error(error: String) {
        binding.spinKit.visibility = View.INVISIBLE
        binding.loginbtn.visibility = View.VISIBLE
        showSnackBar(error)
    }

    override fun <T> success(message: T?) {
        val msg = message as String
        binding.spinKit.visibility = View.INVISIBLE
        binding.loginbtn.visibility = View.VISIBLE
        showSnackBar(msg)
    }
}
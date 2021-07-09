package com.enike.fudedu.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container,
            false)
        model = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = model
        binding.lifecycleOwner = this
        model.mDatastate = this

        model.loggedIn()
        model.loggedIn.observe(viewLifecycleOwner, { Obj ->
            if (Obj == true) {
                model._loggedIn.value = false
                findNavController().navigate(LoginFragmentDirections
                    .actionLoginFragmentToHomeFragment())
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // for testing purpose
//        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//        binding.iAmAStudent.findNavController().navigate(action)


        binding.iAmALecturer.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToLecturerRegisteration()
            binding.iAmALecturer.findNavController().navigate(action)
        }

        binding.iAmAStudent.setOnClickListener { view ->
            val action = LoginFragmentDirections.actionLoginFragmentToRegisteration()
            view.findNavController().navigate(action)
        }
    }


    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }


    override fun loading() {
        binding.loginbtn.visibility = View.INVISIBLE
        binding.spinKit.visibility = View.VISIBLE
    }

    override fun error(error: String) {
        binding.spinKit.visibility = View.INVISIBLE
        binding.loginbtn.visibility = View.VISIBLE
        showSnackBar(error)
    }

    override fun <T> success(message: T?) {
        val msg = message as String
        if (message == "Student") {

        }
        binding.spinKit.visibility = View.INVISIBLE
        binding.loginbtn.visibility = View.VISIBLE
        //showSnackBar(msg)
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        binding.iAmAStudent.findNavController().navigate(action)
    }
}
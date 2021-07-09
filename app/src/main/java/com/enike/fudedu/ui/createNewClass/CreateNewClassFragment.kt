package com.enike.fudedu.ui.createNewClass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.enike.fudedu.R
import com.enike.fudedu.databinding.FragmentCreateNewClassBinding
import com.enike.fudedu.utils.DataState
import com.google.android.material.snackbar.Snackbar

class CreateNewClassFragment : Fragment(), DataState {

    lateinit var binding: FragmentCreateNewClassBinding
    lateinit var viewmodel: CreateClassViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_create_new_class, container, false)

        viewmodel = ViewModelProvider(this).get(CreateClassViewModel::class.java)
        binding.model = viewmodel
        binding.lifecycleOwner = this
        viewmodel.dataState = this

        viewmodel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == "Student"){
                    binding.description.visibility = View.GONE
                    binding.descroptionfield.visibility = View.GONE
                    binding.createbtn.text = "Join Class"

                }
            }
        })

        return binding.root
    }


    private fun showSnackBar(message: String) {
        Snackbar.make(requireContext(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun loading() {
        binding.createbtn.visibility = View.INVISIBLE
        binding.spinKit.visibility = View.VISIBLE
    }

    override fun error(error: String) {
        binding.spinKit.visibility = View.INVISIBLE
        binding.createbtn.visibility = View.VISIBLE
        showSnackBar(error)
    }

    override fun <T> success(message: T?) {
        val msg = message as String
        binding.spinKit.visibility = View.INVISIBLE
        binding.createbtn.visibility = View.VISIBLE
        showSnackBar(msg)
        findNavController().navigate(CreateNewClassFragmentDirections.actionCreateNewClassFragmentToHomeFragment())
    }


}
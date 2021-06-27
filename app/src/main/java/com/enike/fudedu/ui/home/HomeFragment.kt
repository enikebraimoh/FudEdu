package com.enike.fudedu.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.enike.fudedu.R
import com.enike.fudedu.databinding.FragmentHomeBinding
import com.enike.fudedu.utils.DataState
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment(), DataState {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var model: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.createNewClassid.setOnClickListener { view ->
            view.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToCreateNewClassFragment())
        }

        model = ViewModelProvider(this).get(HomeViewModel::class.java)
        model.mDatastate = this
        model.findUser()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun loading() {
        TODO("Not yet implemented")
    }

    override fun error(error: String) {

    }

    override fun <T> success(message: T?) {
        if (message.toString() == "Student") {
            binding.createNewClassid.visibility = View.GONE
        }
    }
}

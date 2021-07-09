package com.enike.fudedu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.enike.fudedu.databinding.FragmentGroupChatBinding
import com.enike.fudedu.databinding.FragmentLoginBinding

class GroupChatFragment : Fragment() {

    private lateinit var binding: FragmentGroupChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arguments = GroupChatFragmentArgs.fromBundle(requireArguments())
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_chat, container,
            false)

        binding.testtext.text  = arguments.className

        return binding.root
    }

}
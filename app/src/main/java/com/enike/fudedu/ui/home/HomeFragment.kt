package com.enike.fudedu.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.enike.fudedu.R
import com.enike.fudedu.databinding.FragmentHomeBinding
import com.enike.fudedu.network.models.StudentsModel
import com.enike.fudedu.ui.createNewClass.GroupModel
import com.enike.fudedu.utils.DataState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Delay


class HomeFragment : Fragment(), DataState {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var model: HomeViewModel

    var listOfDataClasses = ArrayList<GroupModel>()
    var stringOfClassList = ArrayList<String>()


    // database Init
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
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
        getClasses()

    }

    override fun loading() {
        TODO("Not yet implemented")
    }

    override fun error(error: String) {

    }

    override fun <T> success(message: T?) {
        if (message.toString() == "Student") {
            //binding.createNewClassid.visibility = View.GONE
            binding.createclass.text = "Join a class"
        }
    }

    fun getClasses(){


        var user = FirebaseAuth.getInstance()
        database.child("Students Information").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("testing", "looping through the class")

                var data = dataSnapshot.child(user.currentUser.uid)

                for (shot in data.child("groups").children) {
                   // stringOfClassList.add((shot.value.toString()))
                    looping(shot.value.toString())
                }


            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // invalid class
                Log.d("testing", "failed")
            }
        })
    }

    fun  looping (aclass : String){
       // stringOfClassList.forEach { aclass ->
            // from the main group now
            database.child("Class Groups").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(mydataSnapshot: DataSnapshot) {
                    if (mydataSnapshot.hasChild(aclass)){
                        val className =  mydataSnapshot.child(aclass).child("groupName").value.toString()
                        val classDescription =  mydataSnapshot.child(aclass).child("groupDescription").value.toString()
                        val classCreator =  mydataSnapshot.child(aclass).child("groupCreator").value.toString()

                        var classMembers = ArrayList<String>()
                        for (shot in mydataSnapshot.child("groupMembers").children) {
                            classMembers.add(shot.value.toString())
                        }

                        val classModel = GroupModel(className,classDescription,classCreator,classMembers)
                        //Log.d("testing_groups",classModel.groupDescription)
                        listOfDataClasses.add(classModel)
                        Log.d("testing", listOfDataClasses[0].groupCreator)

                        var adapter = HomeAdapter(listOfDataClasses,HomeAdapter.ClickListener{
                            classId -> anItemClicked(classId)
                        })

                        binding.classGroupsRecyclerview.adapter = adapter

                    }

                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                    // invalid class
                    Log.d("testing", "failed")
                }
            })

        }

    fun anItemClicked(className : String){
      findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGroupChatFragment(className))
    }

    //}


}

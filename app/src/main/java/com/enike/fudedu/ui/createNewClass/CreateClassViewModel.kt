package com.enike.fudedu.ui.createNewClass

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enike.fudedu.network.models.StudentsModel
import com.enike.fudedu.utils.DataState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class CreateClassViewModel : ViewModel() {

    var groupName: String? = null
    var groupDescription: String? = null
    var dataState: DataState? = null


    private var _user = MutableLiveData<String>()
    val user : LiveData<String> get() = _user

    // database initialization
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    private  val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    var list = ArrayList<String>()
    var groups = ArrayList<String>()

    init {
        findUser()
    }

    fun findUser() {
        var user = FirebaseAuth.getInstance()
            database.child("Lecturers Information").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (postSnapshot in dataSnapshot.children) {
                        if (dataSnapshot.hasChild(user.currentUser.uid)) {

                            // you are a Lecturer
                            _user.value = "Lecturer"

                            break
                        } else {

                            // you are a Student
                            _user.value = "Student"
                            break
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                }
            })
    }

    fun action(){
        if (_user.value == "Lecturer" ){
            createGroup()
        }else{
            joinGroup()
        }
    }

    fun createGroup() {
        var list = ArrayList<String>()
        list.add(userAuth.currentUser.uid)
        dataState?.let { datastate ->
            datastate.loading()
            val model = GroupModel(
                groupName!!, groupDescription!!,
                userAuth.currentUser.uid, list
            )
            database.child("Class Groups")//.removeValue()
                .child(model.groupName!!).setValue(model).addOnCompleteListener {
                    datastate.success("Class group created Successfully")
                }.addOnFailureListener {
                    datastate.error("Class group failed")
                }
        }
    }

    fun joinGroup() {
        Log.d("testing", "clicked join a class")
        // To find and loop through the database for classes and find a matching class
        var user = FirebaseAuth.getInstance()
        database.child("Class Groups").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("testing", "looping through the class")

                    if (dataSnapshot.hasChild(groupName!!)) {
                        var data = dataSnapshot.child(groupName!!)
                        val classCreator =  data.child("groupCreator").value.toString()
                        val classDescription =  data.child("groupDescription").value.toString()

                        // class is available
                        for (shot in data.child("groupMembers").children) {
                            list.add(shot.value.toString())
                        }
                        
                        list.add(user.currentUser.uid)

                        val newModel = GroupModel(groupName!!,
                            classDescription, classCreator, list)

                        database.child("Class Groups")
                            .child(groupName!!).setValue(newModel).addOnCompleteListener {
                                Log.d("testing", "works")
                                addToInfo()
                            }.addOnFailureListener {
                                Log.d("testing", "fails")
                            }
                    }
                    else {
                        Log.d("testing", "couldn't find the class group")
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

    fun addToInfo(){
        var user = FirebaseAuth.getInstance()
        database.child("Students Information").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("testing", "looping through the class")

                    var data = dataSnapshot.child(user.currentUser.uid)
                    val department = data.child("department").value.toString()
                    val faculty =  data.child("faculty").value.toString()
                    val gender =  data.child("gender").value.toString()
                    val firstname =  data.child("firstName").value.toString()
                    val lastname =  data.child("lastName:").value.toString()
                    val email =  data.child("email").value.toString()
                    val password =  data.child("password").value.toString()
                    val phonenumber =  data.child("phoneNumber").value.toString()

                var groups = ArrayList<String>()
                    for (shot in data.child("groups").children) {
                        groups.add(shot.value.toString())
                    }

                    groups.add(groupName!!)

                    val newModel = StudentsModel (phonenumber,
                        firstname,
                        lastname,
                        email,
                        gender,
                        faculty,
                        department,
                        password,
                        groups)

                    database.child("Students Information")
                        .child(user.currentUser.uid).setValue(newModel).addOnCompleteListener {
                            Log.d("testing", "updated user")
                        }.addOnFailureListener {
                            Log.d("testing", "updated user failed")
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

}
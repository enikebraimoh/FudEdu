package com.enike.fudedu.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.enike.fudedu.utils.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeViewModel : ViewModel() {

    // database Init
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    var mDatastate: DataState? = null

    val id = firebaseAuth.currentUser.uid

    fun findUser() {
        mDatastate?.let { datastate ->
            database.child("Lecturers Information").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (postSnapshot in dataSnapshot.children) {
                        if (dataSnapshot.hasChild(id)) {

                            // you are a Lecturer
                            datastate.success("Lecturer")

                            break
                        } else {

                            // you are a Student
                            datastate.success("Student")
                            break
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                    datastate.error(databaseError.message)
                }
            })
        }
    }
}
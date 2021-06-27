package com.enike.fudedu.ui.createNewClass

import android.util.Log
import androidx.lifecycle.ViewModel
import com.enike.fudedu.utils.DataState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateClassViewModel : ViewModel() {

    var groupName: String? = null
    var groupDescription: String? = null
    var dataState: DataState? = null

    // database initialization
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }

    private val userAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    fun createGroup() {
        var list = mutableListOf<String>(userAuth.currentUser.email)
        dataState?.let { datastate ->
            datastate.loading()
            val model = GroupModel(
                groupName!!, groupDescription!!,
                userAuth.currentUser.uid, list
            )
            database.child("Class Groups")
                .child(model.groupName).setValue(model).addOnCompleteListener {
                    datastate.success("Class group created Successfully")
                }.addOnFailureListener {
                    datastate.error("Class group failed")
                }
        }
    }

}
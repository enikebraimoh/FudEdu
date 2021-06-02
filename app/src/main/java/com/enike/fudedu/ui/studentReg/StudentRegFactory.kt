package com.enike.fudedu.ui.studentReg

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentRegFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentRegViewModel::class.java)) {
            return StudentRegViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
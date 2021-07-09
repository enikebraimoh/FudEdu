package com.enike.fudedu.utils

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import com.google.android.gms.common.util.Strings
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("verify_field")
fun verifyField(field: TextInputLayout, error: String?) {
    if (error != null) {
        field.isErrorEnabled = true
        field.error = error
    } else {
        field.isErrorEnabled = false
    }
}

@BindingAdapter("selectGender")
fun selectGender(genderField: AutoCompleteTextView, gender: ArrayAdapter<String>) {
    genderField.setAdapter(gender)
}

@BindingAdapter("selectFaculty")
fun selectFaculty(facultyField: AutoCompleteTextView, faculty: ArrayAdapter<String>) {
    facultyField.setAdapter(faculty)

}
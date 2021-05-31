package com.enike.fudedu.Utils

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("verify_password")
fun verifyPassword(field: TextInputLayout, error: String?) {
    if (error != null) {
        field.isErrorEnabled = true
        field.error = error
    } else {
        field.isErrorEnabled = false
    }
}

@BindingAdapter("verify_email")
fun verifyEmail(field: TextInputLayout, error: String?) {
    if (error == null) {
        field.isErrorEnabled = false
    } else {
        field.isErrorEnabled = true
        field.error = error
    }

}
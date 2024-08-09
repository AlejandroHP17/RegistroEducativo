package com.mx.liftechnology.registroeducativo.main.viewextensions

import android.content.Context
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.model.util.ModelSelectorForm

/** Toast generic to Fragments */
fun EditText.verify(inputLayout: TextInputLayout, context:Context, option:ModelSelectorForm): Boolean {
    when{
        this.text.isNullOrEmpty()->{
            inputLayout.isErrorEnabled = true
            inputLayout.error = context.getString(R.string.form_error_generic)
        }
        option == ModelSelectorForm.CURP -> {
            if(this.text.length == 13){
                inputLayout.isErrorEnabled = false
                inputLayout.error = ""
            }else{
                inputLayout.isErrorEnabled = true
                inputLayout.error = context.getString(R.string.form_error_characters)
            }
        }
        option == ModelSelectorForm.PHONE -> {
            if(this.text.length == 10){
                inputLayout.isErrorEnabled = false
                inputLayout.error = ""
            }else{
                inputLayout.isErrorEnabled = true
                inputLayout.error = context.getString(R.string.form_error_characters)
            }
        }
        else -> {
            inputLayout.isErrorEnabled = false
            inputLayout.error = ""
        }
    }
    return this.text.isNullOrEmpty()
}
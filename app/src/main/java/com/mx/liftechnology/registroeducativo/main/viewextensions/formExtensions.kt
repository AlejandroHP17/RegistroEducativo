package com.mx.liftechnology.registroeducativo.main.viewextensions

import android.content.Context
import android.widget.EditText
import androidx.core.content.ContextCompat.getString
import com.google.android.material.textfield.TextInputLayout
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.util.ModelSelectorForm
import com.mx.liftechnology.registroeducativo.R

/** Check the input text, and show the errors or not
 * @author pelkidev
 * @since 1.0.0
 */
fun EditText.verify(
    inputLayout: TextInputLayout,
    context: Context,
    option: ModelSelectorForm
): Boolean {
    when {
        this.text.isNullOrEmpty() -> {
            inputLayout.isErrorEnabled = true
            inputLayout.error = context.getString(R.string.form_error_generic)
        }

        option == ModelSelectorForm.CURP -> {
            if (this.text.length == 18) {
                inputLayout.isErrorEnabled = false
                inputLayout.error = ""
            } else {
                inputLayout.isErrorEnabled = true
                inputLayout.error = context.getString(R.string.form_error_characters)
            }
        }

        option == ModelSelectorForm.PHONE -> {
            if (this.text.length == 10) {
                inputLayout.isErrorEnabled = false
                inputLayout.error = ""
            } else {
                inputLayout.isErrorEnabled = true
                inputLayout.error = context.getString(R.string.form_error_characters)
            }
        }

        else -> {
            inputLayout.isErrorEnabled = false
            inputLayout.error = ""
        }
    }
    return !this.text.isNullOrEmpty()
}

fun TextInputLayout.successET() {
    this.error = null
}

fun TextInputLayout.errorET(codeError: Int) {
    this.error = when (codeError) {
        ModelCodeError.ET_EMPTY -> {
            getString(context, R.string.text_empty)
        }

        ModelCodeError.ET_FORMAT -> {
            getString(context, R.string.text_email_format_incorrect)
        }

        ModelCodeError.ET_DIFFERENT -> {
            getString(context, R.string.text_pass_not_match)
        }

        ModelCodeError.ET_NOT_FOUND -> {
            getString(context, R.string.text_cct_not_found)
        }

        ModelCodeError.ET_INCORRECT_FORMAT -> {
            getString(context, R.string.text_incorrect_format)
        }

        ModelCodeError.ET_MISTAKE_EMAIL -> {
            getString(context, R.string.text_email_incorrect)
        }

        ModelCodeError.ET_MISTAKE_PASS -> {
            getString(context, R.string.text_pass_incorrect)
        }

        ModelCodeError.ET_MISTAKE_PASS_RULES -> {
            getString(context, R.string.text_incorrect_format)
        }

        ModelCodeError.ET_MISTAKE_CURP -> {
            getString(context, R.string.text_curp_incorrect)
        }

        ModelCodeError.ET_MISTAKE_PHONE_NUMBER -> {
            getString(context, R.string.text_phone_number_incorrect)
        }

        else -> {
            getString(context, R.string.text_incorrect_data)
        }
    }
}
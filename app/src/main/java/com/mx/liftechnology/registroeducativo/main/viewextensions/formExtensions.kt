package com.mx.liftechnology.registroeducativo.main.viewextensions

import com.google.android.material.textfield.TextInputLayout

/** Check the input text, and show the errors or not
 * @author pelkidev
 * @since 1.0.0
 */
fun TextInputLayout.successET() {
    this.error = null
}

fun TextInputLayout.errorET(codeError: String) {
    this.error = codeError
}
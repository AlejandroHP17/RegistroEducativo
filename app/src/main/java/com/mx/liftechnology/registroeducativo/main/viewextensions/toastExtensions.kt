package com.mx.liftechnology.registroeducativo.main.viewextensions

import android.widget.Toast
import androidx.fragment.app.Fragment

/** Toast generic to Fragments */
fun Fragment.toastFragment(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
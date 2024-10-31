package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mx.liftechnology.registroeducativo.databinding.FragmentForgetPasswordBinding

/** MenuFragment - Show the different available option that the user has
 * @author pelkidev
 * @since 1.0.0
 */
class ForgetPasswordFragment : Fragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)

        return binding.root}
}
package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.core.util.ErrorState
import com.mx.liftechnology.core.util.ModelCodeError
import com.mx.liftechnology.core.util.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentLoginBinding
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


/** MenuFragment - Show the different available option that the user has
 * @author pelkidev
 * @since 1.0.0
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        initView()
        initObservers()
        initListeners()
        return binding.root
    }

    private fun initView(){
        binding.apply {
            includeHeader.btnReturn.visibility = View.GONE
            includeHeader.tvTitle.text = getString(R.string.log_welcome)
            includeHeader.tvInsert.text = getString(R.string.log_insert)
        }
    }

    private fun initObservers(){
        loginViewModel.emailField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> { binding.inputEmail.error = null }
                is ErrorState -> {
                    binding.inputEmail.error = when(state.result){
                        ModelCodeError.ET_EMPTY -> { getString(R.string.text_empty)}
                        ModelCodeError.ET_FORMAT -> { getString(R.string.text_email_format_incorrect)}
                        ModelCodeError.ET_MISTAKE -> { getString(R.string.text_email_incorrect)}
                        else -> { getString(R.string.text_email_incorrect)}
                    }
                }
                else -> { binding.inputEmail.error = getString(R.string.text_email_incorrect) }
            }
        }

        loginViewModel.passField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> { binding.inputPassword.error = null }
                is ErrorState -> {
                    binding.inputPassword.error = when(state.result){
                        ModelCodeError.ET_EMPTY -> { getString(R.string.text_empty)}
                        ModelCodeError.ET_MISTAKE -> { getString(R.string.text_pass_incorrect)}
                        else -> { getString(R.string.text_pass_incorrect)}
                    }
                }
                else -> { binding.inputPassword.error = getString(R.string.text_pass_incorrect) }
            }
        }

        loginViewModel.responseLogin.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish() }
                else -> {
                    // Mostrar un error
                }
            }
        }
    }

    /** initListeners - Build the click on the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                loginViewModel.validateFields(etEmail.text.toString(), etPassword.text.toString())
            }

            tvForgot.setOnClickListener {
                val nav = LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment()
                findNavController().navigate(nav)
            }

            tvRegister.setOnClickListener {
                val nav = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(nav)
            }
        }
    }


}
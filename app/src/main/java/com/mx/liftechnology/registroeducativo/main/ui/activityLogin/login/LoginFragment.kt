package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentLoginBinding
import com.mx.liftechnology.registroeducativo.main.ui.activityMain.MainActivity
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.successET
import com.mx.liftechnology.registroeducativo.main.viewextensions.toastFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/** LoginFragment - User can login, or select the register account or forget password
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

    /** initView - Build the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initView(){
        binding.apply {
            includeHeader.btnReturn.visibility = View.GONE
            includeHeader.tvTitle.text = getString(R.string.log_welcome)
            includeHeader.tvInsert.text = getString(R.string.log_insert)
            includeButton.btnAction.text = getString(R.string.log_logIn)
            includeButton.btnRecord.visibility = View.GONE
        }
    }

    /** initObservers - focus in the variables from viewmodel
     * @author pelkidev
     * @since 1.0.0
     * @param emailField check the email and set the correct view
     * @param passField check the password and set the correct view
     * @param responseLogin check the response of service, do actions
     * */
    private fun initObservers(){
        loginViewModel.emailField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> { binding.inputEmail.successET() }
                is ErrorState -> { binding.inputEmail.errorET(state.result) }
                else -> { binding.inputEmail.errorET( ModelCodeError.ET_MISTAKE_EMAIL) }
            }
        }

        loginViewModel.passField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {binding.inputPassword.successET() }
                is ErrorState -> {binding.inputPassword.errorET(state.result) }
                else -> { binding.inputPassword.errorET(ModelCodeError.ET_MISTAKE_PASS) }
            }
        }

        /**
         * SuccesState - navigate to MainActivity, enter the application
         * ServiceError - show an error of service
         * AnotherState - set errors on inputs
         * */
        loginViewModel.responseLogin.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                is ErrorState -> {
                    toastFragment(getString(R.string.toast_error_service))
                }
                else -> {
                    binding.inputPassword.errorET(ModelCodeError.ET_MISTAKE_PASS)
                    binding.inputEmail.errorET(ModelCodeError.ET_MISTAKE_EMAIL)
                    toastFragment(getString(R.string.toast_error_editext))
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
            includeButton.btnAction.setOnClickListener {
                loginViewModel.validateFields(etEmail.text.toString(), etPassword.text.toString(), binding.cbRemember.isChecked)
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
package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

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
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterBinding
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.successET
import com.mx.liftechnology.registroeducativo.main.viewextensions.toastFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


/** MenuFragment - Show the different available option that the user has
 * @author pelkidev
 * @since 1.0.0
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModel.getCCT()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        initView()
        initObservers()
        initListeners()
        return binding.root
    }

    /** initView - Build the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initView() {
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.reg_welcome)
            includeHeader.tvInsert.text = getString(R.string.reg_insert)

            val listRules = context?.resources?.getStringArray(R.array.rules_pass)
            val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
            tvRegister.text = stringBuilder
        }
    }

    /** initObservers - focus in the variables from viewmodel
     * @author pelkidev
     * @since 1.0.0
     * @param emailField check the email and set the correct view
     * @param passField check the password and set the correct view
     * @param repeatPassField check the password and set the correct view
     * @param cctField check the cct and set the correct view
     * @param codeField check the code and set the correct view
     * @param responseLogin check the response of service, do actions
     * */
    private fun initObservers() {
        registerViewModel.emailField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    binding.inputEmail.successET()
                }

                is ErrorState -> {
                    binding.inputEmail.errorET(state.result)
                }

                else -> {
                    binding.inputEmail.errorET(ModelCodeError.ET_MISTAKE_EMAIL)
                }
            }
        }

        registerViewModel.passField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    binding.inputPassword.successET()
                }

                is ErrorState -> {
                    binding.inputPassword.errorET(state.result)
                }

                else -> {
                    binding.inputPassword.errorET(ModelCodeError.ET_MISTAKE_PASS)
                }
            }
        }

        registerViewModel.repeatPassField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    binding.inputRepeatPassword.successET()
                }

                is ErrorState -> {
                    binding.inputRepeatPassword.errorET(state.result)
                }

                else -> {
                    binding.inputRepeatPassword.errorET(ModelCodeError.ET_DIFFERENT)
                }
            }
        }

        registerViewModel.cctField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    binding.inputCct.successET()
                }

                is ErrorState -> {
                    binding.inputCct.errorET(state.result)
                }

                else -> {
                    binding.inputCct.errorET(ModelCodeError.ET_EMPTY)
                }
            }
        }

        registerViewModel.codeField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    binding.inputCode.successET()
                }

                is ErrorState -> {
                    binding.inputCode.errorET(ModelCodeError.ET_NOT_FOUND)
                }

                else -> {
                    binding.inputCode.errorET(ModelCodeError.ET_EMPTY)
                }
            }
        }

        registerViewModel.responseRegister.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    findNavController().popBackStack()
                }

                else -> {
                    toastFragment(getString(R.string.toast_error_register))
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
            btnRegister.setOnClickListener {
                registerViewModel.validateFields(
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etRepeatPassword.text.toString(),
                    etCct.text.toString(),
                    etCode.text.toString()
                )
            }

            includeHeader.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
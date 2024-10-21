package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.mx.liftechnology.core.util.ErrorState
import com.mx.liftechnology.core.util.ModelCodeError
import com.mx.liftechnology.core.util.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterBinding
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

    private fun initView() {
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.reg_welcome)
            includeHeader.tvInsert.text = getString(R.string.reg_insert)
        }
    }

    private fun initObservers() {
        registerViewModel.emailField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    handlerSuccess(binding.inputEmail)
                }

                is ErrorState -> {
                    handlerErrorEmail(binding.inputEmail, state.result)
                }

                else -> {
                    handlerErrorEmail(binding.inputEmail, ModelCodeError.ET_MISTAKE)
                }
            }
        }

        registerViewModel.passField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    handlerSuccess(binding.inputPassword)
                }

                is ErrorState -> {
                    handlerErrorPass(binding.inputPassword, state.result)
                }

                else -> {
                    handlerErrorPass(binding.inputPassword, ModelCodeError.ET_MISTAKE)
                }
            }
        }

        registerViewModel.repeatPassField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    handlerSuccess(binding.inputRepeatPassword)
                }

                is ErrorState -> {
                    handlerErrorPass(binding.inputRepeatPassword, state.result)
                }

                else -> {
                    handlerErrorPass(binding.inputRepeatPassword, ModelCodeError.ET_DIFFERENT)
                }
            }
        }

        registerViewModel.cctField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    handlerSuccess(binding.inputCct)
                }

                is ErrorState -> {
                    handlerErrorCCT(binding.inputCct, state.result)
                }

                else -> {
                    handlerErrorCCT(binding.inputCct, ModelCodeError.ET_MISTAKE)
                }
            }
        }

        registerViewModel.codeField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    handlerSuccess(binding.inputCode)
                }

                is ErrorState -> {
                    handlerErrorCCT(binding.inputCode, ModelCodeError.ET_EMPTY)
                }

                else -> {
                    handlerErrorCCT(binding.inputCode, ModelCodeError.ET_EMPTY)
                }
            }
        }
    }

    private fun handlerSuccess(inputText: TextInputLayout) {
        inputText.error = null
    }

    private fun handlerErrorEmail(inputText: TextInputLayout, result: Int) {
        inputText.error = when (result) {
            ModelCodeError.ET_EMPTY -> {
                getString(R.string.text_empty)
            }

            ModelCodeError.ET_FORMAT -> {
                getString(R.string.text_email_format_incorrect)
            }

            ModelCodeError.ET_MISTAKE -> {
                getString(R.string.text_email_incorrect)
            }

            else -> {
                getString(R.string.text_email_incorrect)
            }
        }
    }

    private fun handlerErrorPass(inputText: TextInputLayout, result: Int) {
        inputText.error = when (result) {
            ModelCodeError.ET_EMPTY -> {
                getString(R.string.text_empty)
            }

            ModelCodeError.ET_MISTAKE -> {
                getString(R.string.text_pass_incorrect)
            }

            ModelCodeError.ET_DIFFERENT -> {
                getString(R.string.text_pass_not_match)
            }

            else -> {
                getString(R.string.text_pass_incorrect)
            }
        }
    }

    private fun handlerErrorCCT(inputText: TextInputLayout, result: Int) {
        inputText.error = when (result) {
            ModelCodeError.ET_EMPTY -> {
                getString(R.string.text_empty)
            }

            ModelCodeError.ET_MISTAKE -> {
                getString(R.string.text_cct_not_found)
            }

            else -> {
                getString(R.string.text_cct_not_found)
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
package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterBinding
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastFailed
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastSuccess
import com.mx.liftechnology.registroeducativo.main.viewextensions.successET
import org.koin.androidx.viewmodel.ext.android.viewModel

/** RegisterFragment - A new user can register in the app, like teacher or student
 * @author pelkidev
 * @since 1.0.0
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val registerViewModel: RegisterViewModel by viewModel()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        initView()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animationHandler = null
        _binding = null
    }

    /** initView - Build the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initView() {
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.reg_welcome)
            includeHeader.tvInsert.text = getString(R.string.reg_insert)
            includeButton.btnAction.text = getString(R.string.reg_welcome)
            includeButton.btnRecord.visibility = View.GONE

            val listRules = context?.resources?.getStringArray(R.array.rules_pass)
            val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
            tvRegister.text = stringBuilder
        }
    }

    /** initObservers - focus in the variables from viewmodel
     * @author pelkidev
     * @since 1.0.0
     * ### Observed Variables:
     * `emailField` to validate the email input and update the UI accordingly.
     * `passField` to validate the password input and update the UI accordingly.
     * `repeatPassField` to validate the password input and update the UI accordingly.
     * `cctField` to validate the cct input and update the UI accordingly.
     * `codeField` to validate the code input and update the UI accordingly.
     * `responseLogin` to validate the response of service, do actions
     * */
    private fun initObservers() {
        registerViewModel.emailField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputEmail.successET()
                is ErrorUserState -> binding.inputEmail.errorET(state.result)
                else -> binding.inputEmail.errorET(ModelCodeInputs.ET_PASS_FORMAT_MISTAKE)
            }
        }

        registerViewModel.passField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputPassword.successET()
                is ErrorUserState -> binding.inputPassword.errorET(state.result)
                else -> binding.inputPassword.errorET(ModelCodeInputs.ET_PASS_FORMAT_MISTAKE)
            }
        }

        registerViewModel.repeatPassField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputRepeatPassword.successET()
                is ErrorUserState -> binding.inputRepeatPassword.errorET(state.result)
                else -> binding.inputRepeatPassword.errorET(ModelCodeInputs.ET_PASS_DIFFERENT_MISTAKE)
            }
        }

        registerViewModel.codeField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputCode.successET()
                is ErrorUserState -> binding.inputCode.errorET(state.result)
                else -> binding.inputCode.errorET(ModelCodeInputs.ET_EMPTY)
            }
        }

        registerViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }
                else -> animationHandler?.hideLoadingAnimation()
            }
        }

        /**
         * SuccessState - navigate to login
         * ErrorState - log an error of service
         * ErrorUserState - show the error to user
         * */
        registerViewModel.responseRegister.observe(viewLifecycleOwner) { state ->
            log(state.toString())
            when (state) {
                is SuccessState -> {
                    findNavController().popBackStack()
                    showCustomToastSuccess(requireActivity(), state.result.toString())
                }
                is ErrorUserState -> showCustomToastFailed(requireActivity(), state.result)
                else -> {
                    // Nothing
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
                registerViewModel.validateFields(
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etRepeatPassword.text.toString(),
                    etCode.text.toString()
                )
            }

            includeHeader.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
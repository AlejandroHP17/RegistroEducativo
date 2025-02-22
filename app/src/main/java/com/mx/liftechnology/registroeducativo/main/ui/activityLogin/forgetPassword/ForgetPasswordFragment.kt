package com.mx.liftechnology.registroeducativo.main.ui.activityLogin.forgetPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentForgetPasswordBinding
import com.mx.liftechnology.registroeducativo.main.viewextensions.errorET
import com.mx.liftechnology.registroeducativo.main.viewextensions.successET
import org.koin.androidx.viewmodel.ext.android.viewModel

/** ForgetPasswordFragment - The user can recovery his password
 * @author pelkidev
 * @since 1.0.0
 */
class ForgetPasswordFragment : Fragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val forgetPassViewModel: ForgetPasswordViewModel by viewModel()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
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
            includeHeader.tvTitle.text = getString(R.string.forget_pass_welcome)
            includeHeader.tvInsert.text = getString(R.string.forget_pass_insert)
            includeButton.btnAction.text = getString(R.string.forget_pass_button)
            includeButton.btnRecord.visibility = View.GONE

            val listRules = context?.resources?.getStringArray(R.array.rules_forget_pass)
            val stringBuilder = listRules?.joinToString(separator = "\n").orEmpty()
            tvRegister.text = stringBuilder
        }
    }

    /** initObservers - focus in the variables from viewmodel
     * @author pelkidev
     * @since 1.0.0
     * ### Observed Variables:
     * `emailField` to validate the email input and update the UI accordingly.
     *
     * */
    private fun initObservers() {
        forgetPassViewModel.emailField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.inputEmail.successET()
                is ErrorState -> binding.inputEmail.errorET(state.result)
                else -> binding.inputEmail.errorET(ModelCodeError.ET_MISTAKE_EMAIL)
            }
        }

        forgetPassViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }
                else -> animationHandler?.hideLoadingAnimation()
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
                forgetPassViewModel.validateFields(etEmail.text.toString())
            }

            includeHeader.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.partial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterPartialBinding
import com.mx.liftechnology.registroeducativo.main.adapters.PeriodAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.PeriodClickListener
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.main.util.ModelSpinnerSelect
import com.mx.liftechnology.registroeducativo.main.viewextensions.fillItem
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastFailed
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

/** RegisterPartialFragment - Accept the data of the school
 * @author pelkidev
 * @since 1.0.0
 */
class RegisterPartialFragment : Fragment() {

    private var _binding: FragmentRegisterPartialBinding? = null
    private val binding get() = _binding!!

    private val registerPartialViewModel: RegisterPartialViewModel by viewModel()
    private lateinit var adapterPeriods: PeriodAdapter
    private var listPeriods : MutableList<ModelDatePeriodDomain>? = null


    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterPartialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationHandler = context as? AnimationHandler
        initViewAdapter()
        initView()
        initListeners()
        showLogicSpinner()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        registerPartialViewModel.loaderState(true)
        registerPartialViewModel.getListPartial()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        animationHandler = null
    }

    private fun showLogicSpinner() {
        binding.apply {
            includeSpinnerPeriod.spinner.fillItem(
                requireContext(),
                ModelSpinnerSelect.PERIOD,
                null
            )
            includeSpinnerPeriod.tvDemostration.visibility = View.GONE
            includeSpinnerPeriod.spinner.visibility = View.VISIBLE
        }
    }

    /** initView - Build the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initView() {
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.register_partial)
            includeHeader.tvInsert.text = getString(R.string.register_partial_description)
            includeSpinnerPeriod.tvDemostration.text = getString(R.string.register_partial_period)
        }
    }

    /** initObservers - focus in the variables from viewmodel
     * @author pelkidev
     * @since 1.0.0
     * `numberPeriod` check the number of periods selected
     * `datePeriod` check the  date and post the date in correct view
     * */
    private fun initObservers() {
        registerPartialViewModel.datePeriod.observe(viewLifecycleOwner) { data ->
            adapterPeriods.updateDate(data)
        }

        registerPartialViewModel.adapterField.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {}
                is ErrorUserState -> {
                    showCustomToastFailed(requireActivity(),state.result)
                }
                else-> {}
            }
        }

        registerPartialViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }
                else -> animationHandler?.hideLoadingAnimation()
            }
        }

        registerPartialViewModel.responseRegisterPartial.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> {
                    showCustomToastSuccess(requireActivity(), state.result.toString())
                    findNavController().popBackStack()
                }
                is ErrorUserState -> showCustomToastFailed(requireActivity(), state.result)
                else -> log(state.toString())
            }
        }

        registerPartialViewModel.getPartialField.observe(viewLifecycleOwner){state ->
            initSpinner()
            when(state) {
                is SuccessState -> {
                    state.result?.size?.let { size ->
                        binding.includeSpinnerPeriod.spinner.setSelection(size)
                        listPeriods = state.result
                        initAdapterPeriod(size)
                    }
                }
                is ErrorUserState ->{
                    registerPartialViewModel.loaderState(false)
                    showCustomToastFailed(requireActivity(), state.result)
                }

                else -> {
                    registerPartialViewModel.loaderState(false)
                    log(state.toString())
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
            includeHeader.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }

            includeButton.btnAction.setOnClickListener {
                registerPartialViewModel.validateFields(adapterPeriods.getList())
            }
        }
    }

    /** initAdapterPeriod - Adapter specialized in the number of periods of user can select
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initViewAdapter() {
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.register_partial)
            includeHeader.tvInsert.text = getString(R.string.register_partial_description)
            includeSpinnerPeriod.tvDemostration.text = getString(R.string.register_partial_period)

            val clickListener = PeriodClickListener(
                onItemClick = { item ->
                    registerPartialViewModel.initDatePicker(item, parentFragmentManager, context)
                }
            )

            adapterPeriods = PeriodAdapter(clickListener)
            binding.rvCardPeriod.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCardPeriod.adapter = adapterPeriods
        }
    }


    private fun initAdapterPeriod(period: Int) {
        val newList = MutableList(period) { index ->
            ModelDatePeriodDomain(position = index, date = "Parcial ${index + 1}")
        }
        adapterPeriods.submitList(newList)  // Enviar lista con submitList()
        registerPartialViewModel.loaderState(false)
    }

    private fun initSpinner(){
        binding.includeSpinnerPeriod.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                parent?.getItemAtPosition(position)?.toString()?.toIntOrNull()?.let { period ->
                    initAdapterPeriod(period)
                    registerPartialViewModel.savePeriod(period.toString())
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


    }
}
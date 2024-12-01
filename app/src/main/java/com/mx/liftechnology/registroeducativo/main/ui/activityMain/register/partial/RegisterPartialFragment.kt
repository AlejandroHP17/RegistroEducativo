package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.partial

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.domain.interfaces.AnimationHandler
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterPartialBinding
import com.mx.liftechnology.registroeducativo.main.adapters.PeriodAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.PeriodClickListener
import com.mx.liftechnology.domain.model.ModelDatePeriod
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.util.ModelSpinnerSelect
import com.mx.liftechnology.registroeducativo.main.viewextensions.fillItem
import org.koin.androidx.viewmodel.ext.android.viewModel

/** RegisterPartialFragment - Accept the data of the school
 * @author pelkidev
 * @since 1.0.0
 */
class RegisterPartialFragment : Fragment() {

    private var _binding: FragmentRegisterPartialBinding? = null
    private val binding get() = _binding!!

    private val registerPartialViewModel: RegisterPartialViewModel by viewModel()
    private var adapterPeriods : PeriodAdapter? = null

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    /**
     * block to accept the animation if the fragment is attached to the activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        animationHandler = context as? AnimationHandler
    }
    override fun onDetach() {
        super.onDetach()
        animationHandler = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterPartialBinding.inflate(inflater, container, false)
        initView()
        initListener()
        initObserver()

        return binding.root
    }

    /** initView - Build the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initView(){
        binding.apply {
            includeHeader.tvTitle.text = getString(R.string.register_partial)
            includeHeader.tvInsert.text = getString(R.string.register_partial_description)
            includeSpinnerPeriod.spinner.fillItem(requireContext(), ModelSpinnerSelect.PERIOD)
        }
    }

    /** initObservers - focus in the variables from viewmodel
     * @author pelkidev
     * @since 1.0.0
     * `numberPeriod` check the number of periods selected
     * `datePeriod` check the  date and post the date in correct view
     * */
    private fun initObserver(){
        registerPartialViewModel.periodNumber.observe(viewLifecycleOwner) { period ->
            initAdapterPeriod(period)
        }

        registerPartialViewModel.datePeriod.observe(viewLifecycleOwner){data ->
            adapterPeriods?.updateDate(data)
        }

        registerPartialViewModel.periodField.observe(viewLifecycleOwner) { period ->
            period.log()
        }

        registerPartialViewModel.adapterField.observe(viewLifecycleOwner){adapter ->
            adapter.log()
        }
    }

    /** initListeners - Build the click on the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initListener(){
        binding.apply {
            includeHeader.btnReturn.setOnClickListener {
                findNavController().popBackStack()
            }

            includeButton.btnAction.setOnClickListener {
                registerPartialViewModel.validateFields(adapterPeriods?.getList())

            }

            /** Spinner Section*/
            includeSpinnerPeriod.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedValue = parent?.getItemAtPosition(position).toString()
                    registerPartialViewModel.savePeriod(selectedValue)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //Nothing
                }
            }
        }
    }

    /** initAdapterPeriod - Adapter specialized in the number of periods of user can select
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initAdapterPeriod(period:Int){
        val clickListener = PeriodClickListener(
            onItemClick = { item ->
                /* Open the calendar */
                registerPartialViewModel.initDatePicker(item, parentFragmentManager, context)
            }
        )
        val list = MutableList(period) { index -> ModelDatePeriod(position = index , date = "Parcial ${index + 1}") }

        /* Build the adapter */
        adapterPeriods = PeriodAdapter(list, clickListener)
        binding.rvCardPeriod.layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            rvCardPeriod.adapter = adapterPeriods
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        adapterPeriods = null
        animationHandler = null
    }
}
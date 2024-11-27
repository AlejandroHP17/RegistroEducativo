package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.partial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentRegisterPartialBinding
import com.mx.liftechnology.registroeducativo.main.adapters.PeriodAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.PeriodClickListener
import com.mx.liftechnology.registroeducativo.main.util.ModelDatePeriod
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
     * @param [cctField] check the cct and  fill other fields
     * @param numberPerdiod check the number of periods selected
     * @param datePeriod check the  date and post the date in correct view
     * */
    private fun initObserver(){
        registerPartialViewModel.periodNumber.observe(viewLifecycleOwner) { period ->
            initAdapterPeriod(period)
        }

        registerPartialViewModel.datePeriod.observe(viewLifecycleOwner){data ->
            adapterPeriods?.updateDate(data)
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
}
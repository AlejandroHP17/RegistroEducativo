package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.data.model.ModelAdapterMenu
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentMenuBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter
import com.mx.liftechnology.registroeducativo.main.funextensions.log
import com.mx.liftechnology.registroeducativo.main.model.ModelSelectorMenu
import com.mx.liftechnology.registroeducativo.main.ui.activityLogin.LoginActivity
import com.mx.liftechnology.registroeducativo.main.util.AnimationHandler
import com.mx.liftechnology.registroeducativo.main.util.DialogSelectGroup
import com.mx.liftechnology.registroeducativo.main.viewextensions.showCustomToastFailed
import org.koin.androidx.viewmodel.ext.android.viewModel

/** MenuFragment - Show the different available option that the user has
 * @author pelkidev
 * @since 1.0.0
 */
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    /* View Model variable */
    private val menuViewModel: MenuViewModel by viewModel()

    /* loader variable */
    private var animationHandler: AnimationHandler? = null

    companion object {
        const val ADAPTER_CONTROL = "Área de Control"
        const val ADAPTER_CONTROL_REGISTER = "Área de Registro"
        const val ADAPTER_CONTROL_EVALUATION = "Área de evaluación"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
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
        menuViewModel.getGroup()
        menuViewModel.getControlMenu()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animationHandler = null
        _binding = null
    }

    /** initialView - Print the correct view
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initView() {
        binding.apply {
            tvGretting.text = getString(R.string.menu_grettins, "Profesor")
            tvName.text = getString(R.string.menu_empty)
        }
    }

    /** initObservers - Read variable from viewmodel and do something
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initObservers() {
        /* Show all the options from menu, or if any error occur, show the error */
        menuViewModel.controlMenu.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> initBaseControlAdapter(state.result)
                else -> {
                    binding.includeControl.setTitle(ADAPTER_CONTROL)
                    binding.includeControl.setEmptyState()
                    //Nothing
                }
            }
        }

        /* Show all the options from menu, or if any error occur, show the error */
        menuViewModel.controlMenuRegister.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> initControlRegisterAdapter(state.result)
                else -> {
                    binding.includeRegister.setTitle(ADAPTER_CONTROL_REGISTER)
                    binding.includeRegister.setEmptyState()
                }
            }
        }

        /* Show all the options from menu, or if any error occur, show the error */
        menuViewModel.controlMenuEvaluation.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> initControlEvaluationAdapter(state.result)
                else -> {
                    binding.includeEvaluation.setTitle(ADAPTER_CONTROL_EVALUATION)
                    binding.includeEvaluation.setEmptyState()
                }
            }
        }

        /* Show all the options from menu, or if any error occur, show the error */
        menuViewModel.selectedGroup.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> binding.tvName.text = state.result.nameItem
                is ErrorState -> {
                    log(state.result)
                }

                is ErrorUserState -> {
                    showCustomToastFailed(requireActivity(), state.result)
                }

                is ErrorUnauthorizedState -> {
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                }

                else -> {
                    //Nothing
                }
            }
        }

        menuViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if (state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }

                else -> animationHandler?.hideLoadingAnimation()
            }
        }
    }

    private fun initListeners() {
        binding.cvNameCourse.setOnClickListener {
            showDialog()
        }
    }

    private fun initBaseControlAdapter(items: List<ModelAdapterMenu>){
        val adapter = MenuAdapter { item ->
            val direction: NavDirections? = when (item.id) {
                ModelSelectorMenu.CONTROL.value -> MenuFragmentDirections.actionMenuFragmentToRegisterSchoolFragment()
                ModelSelectorMenu.PROFILE.value -> MenuFragmentDirections.actionMenuFragmentToProfileFragment()
                else -> null
            }
            direction?.let { findNavController().navigate(it) }
        }

        binding.includeControl.setTitle(ADAPTER_CONTROL)
        binding.includeControl.setAdapter(adapter)
        adapter.submitList(items)
    }


    /** inflateAdapter - Build the adapter of menu
     * @author pelkidev
     * @since 1.0.0
     * @param items list the option from menu
     * */
    private fun initControlRegisterAdapter(items: List<ModelAdapterMenu>) {
        val adapter = MenuAdapter { item ->
            val direction: NavDirections? = when (item.id) {
                ModelSelectorMenu.SCHOOL.value -> MenuFragmentDirections.actionMenuFragmentToRegisterSchoolFragment()
                ModelSelectorMenu.STUDENTS.value -> MenuFragmentDirections.actionMenuFragmentToListStudentFragment()
                ModelSelectorMenu.SUBJECTS.value -> MenuFragmentDirections.actionMenuFragmentToListSubjectFragment()
                ModelSelectorMenu.PARTIALS.value -> MenuFragmentDirections.actionMenuFragmentToRegisterPartialFragment()
                else -> null
            }
            direction?.let { findNavController().navigate(it) }
        }

        binding.includeRegister.setTitle(ADAPTER_CONTROL_REGISTER)
        binding.includeRegister.setAdapter(adapter)
        adapter.submitList(items)
    }

    private fun initControlEvaluationAdapter(items: List<ModelAdapterMenu>) {
        val adapter = MenuAdapter { item ->
            val direction: NavDirections? = when (item.id) {
                ModelSelectorMenu.CALENDAR.value -> MenuFragmentDirections.actionMenuFragmentToCalendarFragment()
                ModelSelectorMenu.ESTUDENTS.value -> MenuFragmentDirections.actionMenuFragmentToListStudentFragment()
                ModelSelectorMenu.ESUBJECTS.value -> MenuFragmentDirections.actionMenuFragmentToListSubjectFragment()
                ModelSelectorMenu.EXPORT.value -> MenuFragmentDirections.actionMenuFragmentToRegisterPartialFragment()
                else -> null
            }
            direction?.let { findNavController().navigate(it) }
        }

        binding.includeEvaluation.setTitle(ADAPTER_CONTROL_EVALUATION)
        binding.includeEvaluation.setAdapter(adapter)
        adapter.submitList(items)
    }

    private fun showDialog() {
        val result = menuViewModel.listGroup.value
        if (result is SuccessState) {
            val dialogManager =
                DialogSelectGroup(requireContext(), result.result) { selectedItem ->
                    menuViewModel.updateGroup(selectedItem)
                }
            dialogManager.showDialog()

        }
    }
}
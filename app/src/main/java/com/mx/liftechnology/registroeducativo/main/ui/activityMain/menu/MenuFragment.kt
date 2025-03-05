package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.mx.liftechnology.data.model.ModelPrincipalMenuData
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

    private lateinit var menuAdapter: MenuAdapter
    private var animationHandler: AnimationHandler? = null

    companion object {
        const val ADAPTER_CONTROL = "Área de Control"
        const val ADAPTER_CONTROL_REGISTER = "Área de Registro y evaluación"
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
        animationHandler?.showLoadingAnimation()
        initControlRegisterAdapter()
        initView()
        initListeners()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        menuViewModel.getGroup()
        menuViewModel.getControlMenu()
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
                is SuccessState ->  initBaseControlAdapter(state.result)
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
                is SuccessState -> menuAdapter.submitList(state.result)
                else -> {
                    binding.includeRegister.setTitle(ADAPTER_CONTROL_REGISTER)
                    binding.includeRegister.setEmptyState()
                }
            }
        }

        /* Show all the options from menu, or if any error occur, show the error */
        menuViewModel.selectedGroup.observe(viewLifecycleOwner) { state ->
            log(state.toString())
            when (state) {
                is SuccessState -> binding.tvName.text = state.result.nameItem
                is ErrorUserState -> { showCustomToastFailed(requireActivity(), state.result) }
                is ErrorUnauthorizedState -> {
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
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

    private fun initBaseControlAdapter(items: List<ModelPrincipalMenuData>){
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
     * */
    private fun initControlRegisterAdapter() {
        menuAdapter = MenuAdapter { item ->
            val direction: NavDirections? = when (item.id) {
                ModelSelectorMenu.STUDENTS.value -> MenuFragmentDirections.actionMenuFragmentToListStudentFragment()
                ModelSelectorMenu.SUBJECTS.value -> MenuFragmentDirections.actionMenuFragmentToListSubjectFragment()
                ModelSelectorMenu.PARTIALS.value -> MenuFragmentDirections.actionMenuFragmentToRegisterPartialFragment()
                ModelSelectorMenu.CALENDAR.value -> MenuFragmentDirections.actionMenuFragmentToCalendarFragment()
                ModelSelectorMenu.EXPORT.value -> null
                else -> null
            }
            direction?.let { findNavController().navigate(it) }
        }

        binding.includeRegister.setTitle(ADAPTER_CONTROL_REGISTER)
        binding.includeRegister.setAdapter(menuAdapter)
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
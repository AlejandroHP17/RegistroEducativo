package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.core.model.ModelDialogStudentGroup
import com.mx.liftechnology.core.model.modelApi.DataGroupTeacher
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorStateUser
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.data.model.ModelSelectorMenu
import com.mx.liftechnology.domain.interfaces.AnimationHandler
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.FragmentMenuBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.MenuClickListener
import com.mx.liftechnology.registroeducativo.main.funextensions.log
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

    /* Adapter variable */
    private var adapterMenu: MenuAdapter? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuViewModel.getMenu(false)
        menuViewModel.getGroup()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        initialView()
        initObservers()
        return binding.root
    }

    /** initialView - Print the correct view
     * @author pelkidev
     * @since 1.0.0
     */
    private fun initialView() {
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
        menuViewModel.nameMenu.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState -> inflateAdapter(state.result)
                else -> {
                    //Nothing
                }
            }
        }

        /* Show all the options from menu, or if any error occur, show the error */
        menuViewModel.listGroup.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuccessState ->{
                    val result = state.result?.firstOrNull()
                    binding.tvName.text = "${result?.cct} - ${result?.group}${result?.name} - ${result?.shift}"
                    showDialog(state.result)
                }
                is ErrorState -> log(state.result)
                is ErrorStateUser -> showCustomToastFailed(requireActivity(), state.result.toString())
                else -> {
                    //Nothing
                }
            }
        }

        menuViewModel.animateLoader.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState -> {
                    if(state.result == true) animationHandler?.showLoadingAnimation()
                    else animationHandler?.hideLoadingAnimation()
                }
                else ->  animationHandler?.hideLoadingAnimation()
            }
        }
    }

    /** inflateAdapter - Build the adapter of menu
     * @author pelkidev
     * @since 1.0.0
     * @param items list the option from menu
     * */
    private fun inflateAdapter(items: List<ModelAdapterMenu>) {
        val clickListener = MenuClickListener { item ->
            val direction: NavDirections? = when (item.id) {
                ModelSelectorMenu.EVALUATION.value -> null
                ModelSelectorMenu.CONTROL.value -> MenuFragmentDirections.actionMenuFragmentToSubMenuFragment()
                ModelSelectorMenu.PROFILE.value -> MenuFragmentDirections.actionMenuFragmentToProfileFragment()
                ModelSelectorMenu.CONFIGURATION.value -> null
                else -> null
            }
            direction?.let { findNavController().navigate(it) }
        }

        /* Build the adapter */
        adapterMenu = MenuAdapter(items, clickListener)
        binding.rvCardMenu.layoutManager = GridLayoutManager(this.context, 2)
        binding.apply {
            rvCardMenu.adapter = adapterMenu
            contentMenu.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showDialog(result: List<DataGroupTeacher?>?) {
        // Convertimos la lista de DataGroupTeacher a una lista de ModelDialogStudentGroup
        val modelDialogStudentGroups: List<ModelDialogStudentGroup> = result!!.map { teacher ->
            ModelDialogStudentGroup(
                selected = false,  // Inicializamos el valor de selected como false
                item = teacher     // Asignamos el objeto DataGroupTeacher a la propiedad 'item'
            )
        }
        val dialogManager = DialogSelectGroup(requireContext(), modelDialogStudentGroups) { selectedItem ->
            binding.tvName.text = "${selectedItem?.item?.cct} - ${selectedItem?.item?.group}${selectedItem?.item?.name} - ${selectedItem?.item?.shift}"
        }
        dialogManager.showDialog()
    }

}
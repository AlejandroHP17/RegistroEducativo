package com.mx.liftechnology.registroeducativo.main.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.DialogCustomAddBinding
import com.mx.liftechnology.registroeducativo.main.ui.home.MenuViewModel
import com.mx.liftechnology.registroeducativo.model.util.ModelSelectorDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/** CustomAddDialog - Universal Dialog to add something
 * @author pelkidev
 * @since 1.0.0
 * */
class CustomAddDialog : DialogFragment() {

    private var bindingDialog: DialogCustomAddBinding? = null

    /* Argument variable*/
    private var argSelector: ModelSelectorDialog? = null

    /* View Model variable*/
    private val menuViewModel: MenuViewModel by sharedViewModel()

    companion object {
        fun newInstance(selector: ModelSelectorDialog): CustomAddDialog {
            val fragment = CustomAddDialog()
            val args = Bundle()
            args.putParcelable("selector", selector)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.CustomDialogAnimation

        bindingDialog = DialogCustomAddBinding.inflate(layoutInflater)

        getArgumentsNav()
        initView(dialog)
        initConfigurations(dialog)
        initListeners()

        return dialog
    }

    /** getArgumentsNav - Get the arguments
     * @author pelkidev
     * @since 1.0.0
     * @param argSelector variable of control to select which type show
     * */
    private fun getArgumentsNav() {
        argSelector = arguments?.getParcelable("selector")
    }

    /** initView - Initialize the view with the argSelector
     * @author pelkidev
     * @since 1.0.0
     * @param argSelector variable of control to select which type show
     * */
    private fun initView(dialog: AlertDialog) {
        dialog.setView(bindingDialog?.root)
        bindingDialog?.apply {
            when (argSelector) {
                ModelSelectorDialog.ADD -> {
                    tvTitle.text = getString(R.string.empty_dialog_title)
                }

                else -> {
                    // Nothing
                }
            }
        }
    }

    /** initConfigurations - Configure the dialog
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initConfigurations(dialog: AlertDialog) {
        dialog.setCanceledOnTouchOutside(false)
    }

    /** initListeners - Build the click on the view
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun initListeners() {
        bindingDialog?.btnAdd?.setOnClickListener {
            val text: String = bindingDialog?.etInsert?.text.toString()
            menuViewModel.saveNameCourse(text)
            dismiss()
        }
    }
}
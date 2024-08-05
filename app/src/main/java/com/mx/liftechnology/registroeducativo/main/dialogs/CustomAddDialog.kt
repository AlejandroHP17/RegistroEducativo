package com.mx.liftechnology.registroeducativo.main.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.DialogCustomAddBinding
import com.mx.liftechnology.registroeducativo.main.ui.home.viewmodel.MenuViewModel
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelSelectorDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CustomAddDialog : DialogFragment() {

    /* Variables de argumentos*/
    private var argSelector: ModelSelectorDialog? = null

    private var bindingDialog : DialogCustomAddBinding? = null
    private val menuViewModel: MenuViewModel by sharedViewModel()

    companion object {
        fun newInstance( selector:ModelSelectorDialog): CustomAddDialog {
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
        bindingDialog = DialogCustomAddBinding.inflate(layoutInflater)

        // Recuperar los argumentos
        getArgumentsNav()

        // Configura el estilo de animación personalizado
        dialog.window?.attributes?.windowAnimations = R.style.CustomDialogAnimation

        // Infla y configura tu contenido personalizado en el cuadro de diálogo
        initView(dialog)
        initConfigurations(dialog)
        initListeners()

        return dialog
    }

    private fun initConfigurations(dialog: AlertDialog) {
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun initListeners() {
        bindingDialog?.btnAdd?.setOnClickListener {
            val text: String = bindingDialog?.etInsert?.text.toString()
            menuViewModel.saveNameCourse(text)
            dismiss()
        }
    }

    private fun initView(dialog: AlertDialog) {
        // Configura la vista personalizada en el cuadro de diálogo
        dialog.setView(bindingDialog?.root)
        bindingDialog?.apply {
            when(argSelector){
                ModelSelectorDialog.ADD -> {
                    tvTitle.text = getString(R.string.empty_dialog_title)
                }
                else -> {

                }
            }

        }
    }

    private fun getArgumentsNav() {
        // Recuperar los argumentos
        argSelector = arguments?.getParcelable("selector")
    }
}
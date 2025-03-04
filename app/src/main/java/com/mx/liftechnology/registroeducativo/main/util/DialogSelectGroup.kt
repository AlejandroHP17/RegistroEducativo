package com.mx.liftechnology.registroeducativo.main.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.DialogSelectGroupBinding
import com.mx.liftechnology.registroeducativo.main.adapters.DialogGroupAdapter

/** DialogSelectGroup - Show the dialog with registers groups from teacher
 * @author pelkidev
 * @since 1.0.0
 */
class DialogSelectGroup(
    private val context: Context,
    private val items: List<ModelDialogStudentGroupDomain>,
    private val listener: (ModelDialogStudentGroupDomain?) -> Unit
) {
    private lateinit var dialog: Dialog
    private lateinit var adapter: DialogGroupAdapter
    private lateinit var binding: DialogSelectGroupBinding

    private var selectedItem: ModelDialogStudentGroupDomain? = null

    fun showDialog() {
        // Inicializamos el diálogo
        dialog = Dialog(context)

        // Establecemos el diseño del diálogo
        binding = DialogSelectGroupBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        initConfigsViews()
        initAdapter()

        // Botón Guardar enviará la selección al fragmento
        binding.includeButton.btnAction.setOnClickListener {
            selectedItem?.let { item ->
                listener(item)
                dialog.dismiss()
            }
        }

        // Mostramos el diálogo
        dialog.show()
    }

    private fun initConfigsViews() {
        binding.includeButton.btnRecord.visibility = View.GONE
        binding.includeButton.btnAction.text = context.getString(R.string.select)
    }

    private fun initAdapter() {
        adapter = DialogGroupAdapter(items) { selectedItem = it }
        binding.rvDialog.layoutManager = LinearLayoutManager(context)
        binding.rvDialog.adapter = adapter
    }
}

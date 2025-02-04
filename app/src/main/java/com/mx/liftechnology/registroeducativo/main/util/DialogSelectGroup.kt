package com.mx.liftechnology.registroeducativo.main.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.liftechnology.core.model.ModelDialogStudentGroup
import com.mx.liftechnology.registroeducativo.databinding.DialogSelectGroupBinding
import com.mx.liftechnology.registroeducativo.main.adapters.DialogGroupAdapter
import com.mx.liftechnology.registroeducativo.main.adapters.DialogGroupClickListener

class DialogSelectGroup(
    private val context: Context,
    private val items: List<ModelDialogStudentGroup>,
    private val listener: (ModelDialogStudentGroup) -> Unit
) {

    private lateinit var dialog: Dialog
    private lateinit var adapter: DialogGroupAdapter

    fun showDialog() {
        // Inicializamos el diálogo
        dialog = Dialog(context)
        val binding: DialogSelectGroupBinding = DialogSelectGroupBinding.inflate(LayoutInflater.from(context))

        // Establecemos el diseño del diálogo
        dialog.setContentView(binding.root)

        // Configuración del RecyclerView con el adaptador
        adapter = DialogGroupAdapter(items, object : DialogGroupClickListener {
            override fun onClick(item: ModelDialogStudentGroup) {
                // Llamamos al listener con el ítem seleccionado
                item.copy(nameItem = "${item.item?.cct} - ${item.item?.group}${item.item?.name} - ${item.item?.shift}")
                listener(item)
                dialog.dismiss()
            }
        })
        binding.recyclerViewDialog.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDialog.adapter = adapter

        // Mostramos el diálogo
        dialog.show()
    }
}

package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.core.model.ModelDialogStudentGroup
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardGroupBinding

/** MenuAdapter - Build the adapter for menu (home)
 * @author pelkidev
 * @since 1.0.0
 * @param items list to build
 * @param listener click on item's card
 * */
class DialogGroupAdapter(
    private val items: List<ModelDialogStudentGroup>,
    private val listener: DialogGroupClickListener
) : RecyclerView.Adapter<DialogGroupAdapter.ViewHolder>() {

    private var selectedPosition = -1

    // ViewHolder que maneja cada ítem
    inner class ViewHolder(private val binding: RecyclerCardGroupBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ModelDialogStudentGroup) {
            binding.apply {
                // Concatenar la información del ítem
                val stringBuilder = StringBuilder(item.item?.cct).append(" ")
                    .append(item.item?.name).append(" ").append(item.item?.group)
                tv.text = stringBuilder

                // Verificar si el ítem está seleccionado
                rb.isChecked = adapterPosition == selectedPosition

                // Manejo del clic del RadioButton
                rb.setOnClickListener {
                    if (selectedPosition != adapterPosition) {
                        val previousSelectedPosition = selectedPosition
                        selectedPosition = adapterPosition
                        notifyItemChanged(previousSelectedPosition) // Actualizar el ítem previamente seleccionado
                        notifyItemChanged(selectedPosition) // Actualizar el ítem actual

                        listener.onClick(item)
                    }
                }
            }
        }
    }

    // Crear el ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerCardGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Enlazar los datos al ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // Retornar el tamaño de la lista
    override fun getItemCount(): Int = items.size
}

interface DialogGroupClickListener {
    fun onClick(item: ModelDialogStudentGroup)
}

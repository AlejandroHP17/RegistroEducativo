package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.domain.model.student.ModelStudentRegisterAssignmentDomain
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardStudentAssignmentBinding

class StudentAssignmentAdapter (
    private val listener: StudentAssignmentClickListener
) : ListAdapter<ModelStudentRegisterAssignmentDomain, StudentAssignmentAdapter.ViewHolder>(StudentAssignmentAdapter){

    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelStudentRegisterAssignmentDomain>() {
        override fun areItemsTheSame(oldItem: ModelStudentRegisterAssignmentDomain, newItem: ModelStudentRegisterAssignmentDomain) =
            oldItem.studentId == newItem.studentId

        override fun areContentsTheSame(oldItem: ModelStudentRegisterAssignmentDomain, newItem: ModelStudentRegisterAssignmentDomain) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardStudentAssignmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelStudentRegisterAssignmentDomain, action: StudentAssignmentClickListener) {
            // Synchronize the item response with the view
            binding.apply {
                tvAssignmentDate.text = item.completeName
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            RecyclerCardStudentAssignmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, listener)
        }
    }

    /** Método para actualizar la lista de manera eficiente */
    fun updateList(newList: List<ModelStudentRegisterAssignmentDomain>) {
        submitList(newList)
    }

}

class StudentAssignmentClickListener(
    val onItemClick: (item: ModelStudentRegisterAssignmentDomain) -> Unit
) {
    fun onClick(item: ModelStudentRegisterAssignmentDomain) = onItemClick(item)
}
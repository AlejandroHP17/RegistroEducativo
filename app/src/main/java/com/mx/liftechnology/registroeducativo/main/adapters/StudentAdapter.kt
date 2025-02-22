package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.domain.model.ModelStudent
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardStudentBinding

class StudentAdapter(
    private var items: MutableList<ModelStudent?>?,
    private val listener: StudentClickListener
) : ListAdapter<ModelStudent, StudentAdapter.ViewHolder>(ItemsDiffCallBack) {
    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelStudent>() {
        override fun areItemsTheSame(oldItem: ModelStudent, newItem: ModelStudent) =
            oldItem.studentId == newItem.studentId

        override fun areContentsTheSame(oldItem: ModelStudent, newItem: ModelStudent) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelStudent, action: StudentClickListener) {
            // Synchronize the item response with the view
            binding.apply {
                tvNumberList.text = (adapterPosition + 1).toString()
                val textBuilder =
                    StringBuilder(item.lastName ?: "No name").append(" ")
                        .append(item.secondLastName ?: "").append(" ")
                        .append(item.name ?: "")
                tvNameList.text = textBuilder
                cvTouch.setOnClickListener { action.onClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerCardStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items?.get(position)
        if (currentItem != null) {
            holder.bind(currentItem, listener)
        }
    }

    override fun getItemCount(): Int = items?.size!!
}

class StudentClickListener(
    val onItemClick: (item: ModelStudent) -> Unit
) {
    fun onClick(item: ModelStudent) = onItemClick(item)
}
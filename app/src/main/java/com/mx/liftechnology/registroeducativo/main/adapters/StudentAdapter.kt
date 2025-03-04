package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardListBinding

/** StudentAdapter - Build the adapter to student (Register student)
 * @author pelkidev
 * @since 1.0.0
 * @param listener click on item's card
 * */
class StudentAdapter(
    private val listener: StudentClickListener
) : ListAdapter<ModelStudentDomain, StudentAdapter.ViewHolder>(ItemsDiffCallBack) {
    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelStudentDomain>() {
        override fun areItemsTheSame(oldItem: ModelStudentDomain, newItem: ModelStudentDomain) =
            oldItem.studentId == newItem.studentId

        override fun areContentsTheSame(oldItem: ModelStudentDomain, newItem: ModelStudentDomain) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelStudentDomain, action: StudentClickListener) {
            binding.apply {
                tvNumberList.text = (adapterPosition + 1).toString()
                val textBuilder =
                    StringBuilder(item.lastName ?: "No name").append(" ")
                        .append(item.secondLastName ?: "").append(" ")
                        .append(item.name ?: "")
                tvNameList.text = textBuilder
                ivImage.setOnClickListener { action.onClickMore(it, item) }
                cvTouch.setOnClickListener { action.onClick(item) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, listener)
        }
    }

    fun updateList(newList: List<ModelStudentDomain>) {
        submitList(newList)
    }

}

/** Click listener to StudentAdapter */
class StudentClickListener(
    val onItemClick: (item: ModelStudentDomain) -> Unit,
    val onItemMore: (view: View, item: ModelStudentDomain) -> Unit
) {
    fun onClick(item: ModelStudentDomain) = onItemClick(item)
    fun onClickMore(view: View, item: ModelStudentDomain) = onItemMore(view, item)
}
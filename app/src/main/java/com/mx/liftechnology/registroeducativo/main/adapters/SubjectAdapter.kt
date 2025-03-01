package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.domain.model.ModelFormatSubject
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardListBinding

class SubjectAdapter(
    private val listener: SubjectClickListener
) : ListAdapter<ModelFormatSubject, SubjectAdapter.ViewHolder>(ItemsDiffCallBack) {
    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelFormatSubject>() {
        override fun areItemsTheSame(oldItem: ModelFormatSubject, newItem: ModelFormatSubject) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: ModelFormatSubject, newItem: ModelFormatSubject) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelFormatSubject, action: SubjectClickListener) {
            // Synchronize the item response with the view
            binding.apply {
                tvNameList.text = item.name
                ivImage.setOnClickListener { action.onClickDelete(item) }
                cvTouch.setOnClickListener { action.onClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectAdapter.ViewHolder {
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

    fun updateList(newList: List<ModelFormatSubject>) {
        submitList(newList)
    }

}

class SubjectClickListener(
    val onItemClick: (item: ModelFormatSubject) -> Unit,
    val onItemDelete: (item: ModelFormatSubject) -> Unit
) {
    fun onClick(item: ModelFormatSubject) = onItemClick(item)
    fun onClickDelete(item: ModelFormatSubject) = onItemDelete(item)
}
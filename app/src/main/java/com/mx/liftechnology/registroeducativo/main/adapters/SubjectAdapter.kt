package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.domain.model.ModelSubject
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardSubjectBinding

class SubjectAdapter(
    private var items: MutableList<ModelSubject?>?,
    private val listener: SubjectClickListener
) : ListAdapter<ModelSubject, SubjectAdapter.ViewHolder>(ItemsDiffCallBack) {
    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelSubject>() {
        override fun areItemsTheSame(oldItem: ModelSubject, newItem: ModelSubject) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: ModelSubject, newItem: ModelSubject) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelSubject, action: SubjectClickListener) {
            // Synchronize the item response with the view
            binding.apply {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectAdapter.ViewHolder {
        val binding =
            RecyclerCardSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

class SubjectClickListener(
    val onItemClick: (item: ModelSubject) -> Unit
) {
    fun onClick(item: ModelSubject) = onItemClick(item)
}
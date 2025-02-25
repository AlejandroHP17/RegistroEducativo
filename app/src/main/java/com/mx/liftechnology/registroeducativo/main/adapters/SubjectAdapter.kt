package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardListBinding
import com.mx.liftechnology.registroeducativo.main.model.ModelSubject

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

    class ViewHolder(private val binding: RecyclerCardListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelSubject, action: SubjectClickListener) {
            // Synchronize the item response with the view
            binding.apply {
                tvNameList.text = item.name
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
package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardListBinding

/** SubjectAdapter - Build the adapter to subject (Register subject)
 * @author pelkidev
 * @since 1.0.0
 * @param listener click on item's card
 * */
class SubjectAdapter(
    private val listener: SubjectClickListener
) : ListAdapter<ModelFormatSubjectDomain, SubjectAdapter.ViewHolder>(ItemsDiffCallBack) {
    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelFormatSubjectDomain>() {
        override fun areItemsTheSame(oldItem: ModelFormatSubjectDomain, newItem: ModelFormatSubjectDomain) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: ModelFormatSubjectDomain, newItem: ModelFormatSubjectDomain) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelFormatSubjectDomain, action: SubjectClickListener) {
            binding.apply {
                tvNameList.text = item.name
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

    fun updateList(newList: List<ModelFormatSubjectDomain>) {
        submitList(newList)
    }

}

/** Click listener for SubjectAdapter */
class SubjectClickListener(
    val onItemClick: (item: ModelFormatSubjectDomain) -> Unit,
    val onItemMore: (view: View, item: ModelFormatSubjectDomain) -> Unit
) {
    fun onClick(item: ModelFormatSubjectDomain) = onItemClick(item)
    fun onClickMore(view: View, item: ModelFormatSubjectDomain) = onItemMore(view, item)
}
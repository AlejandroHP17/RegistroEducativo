package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardPeriodBinding

/** PeriodAdapter - Build the adapter to Periods (Register school)
 * @author pelkidev
 * @since 1.0.0
 * @param listener click on item's card
 * */
class PeriodAdapter(
    private val listener: PeriodClickListener
) : ListAdapter<ModelDatePeriodDomain, PeriodAdapter.ViewHolder>(ItemsDiffCallBack) {

    /** DiffUtil callback to optimize list updates */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelDatePeriodDomain>() {
        override fun areItemsTheSame(oldItem: ModelDatePeriodDomain, newItem: ModelDatePeriodDomain) =
            oldItem.position == newItem.position

        override fun areContentsTheSame(oldItem: ModelDatePeriodDomain, newItem: ModelDatePeriodDomain) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardPeriodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelDatePeriodDomain, action: PeriodClickListener) {
            binding.apply {
                tvCalendar.text = item.date
                ivCalendar.setOnClickListener { action.onClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerCardPeriodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

    fun updateDate(item: ModelDatePeriodDomain) {
        val newList = currentList.toMutableList()
        val index = newList.indexOfFirst { it.position == item.position }
        if (index != -1) {
            newList[index] = item
            submitList(newList)
        }
    }

    fun getList(): List<ModelDatePeriodDomain> = currentList
}

/** Click listener for PeriodAdapter */
class PeriodClickListener(
    val onItemClick: (item: ModelDatePeriodDomain) -> Unit
) {
    fun onClick(item: ModelDatePeriodDomain) = onItemClick(item)
}
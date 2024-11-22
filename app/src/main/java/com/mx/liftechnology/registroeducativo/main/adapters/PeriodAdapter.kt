package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardPeriodBinding
import com.mx.liftechnology.registroeducativo.main.util.ModelDatePeriod

/** PeriodAdapter - Build the adapter for Periods (Register schoool)
 * @author pelkidev
 * @since 1.0.0
 * @param items list to build
 * @param listener click on item's card
 * */
class PeriodAdapter(
    private var items: MutableList<ModelDatePeriod>,
    private val listener: PeriodClickListener
) : ListAdapter<ModelDatePeriod, PeriodAdapter.ViewHolder>(ItemsDiffCallBack) {
    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelDatePeriod>() {
        override fun areItemsTheSame(oldItem: ModelDatePeriod, newItem: ModelDatePeriod) =
            oldItem.position == newItem.position

        override fun areContentsTheSame(oldItem: ModelDatePeriod, newItem: ModelDatePeriod) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardPeriodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Method like a listener; bring the item and the action of click
        fun bind(item: ModelDatePeriod, action: PeriodClickListener) {
            // Synchronize the item response with the view
            binding.apply {
                tvCalendar.text = item.date
                ivCalendar.setOnClickListener { action.onClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerCardPeriodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, listener)
    }

    override fun getItemCount(): Int = items.size

    fun updateDate(item: ModelDatePeriod) {
        items[item.position] = ModelDatePeriod(item.position, item.date)
        notifyItemChanged(item.position)
    }
}

class PeriodClickListener(
    val onItemClick: (item: ModelDatePeriod) -> Unit,
) {
    fun onClick(item: ModelDatePeriod) = onItemClick(item)
}
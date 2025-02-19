package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.data.model.ModelAdapterMenu
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardMenuBinding

/** MenuAdapter - Build the adapter for menu (home)
 * @author pelkidev
 * @since 1.0.0
 * @param items list to build
 * @param listener click on item's card
 * */
class MenuAdapter(
    private val listener: (ModelAdapterMenu) -> Unit
) : ListAdapter<ModelAdapterMenu, MenuAdapter.ViewHolder>(ItemsDiffCallBack) {

    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelAdapterMenu>() {
        override fun areItemsTheSame(oldItem: ModelAdapterMenu, newItem: ModelAdapterMenu) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ModelAdapterMenu, newItem: ModelAdapterMenu) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ModelAdapterMenu, listener: (ModelAdapterMenu) -> Unit) {
            binding.apply {
                if (!item.isTouch) {
                    cvComplete.alpha = 0.6F
                    root.setOnClickListener(null)
                } else {
                    root.setOnClickListener { listener(item) }
                }
                item.image?.let {
                    ivImage.setImageResource(it)
                } ?: ivImage.setImageResource(R.drawable.ic_logo)
                tvTitleCard.text = item.titleCard
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerCardMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }
}

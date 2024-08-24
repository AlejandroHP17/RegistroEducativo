package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardMenuBinding
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelAdapterMenu

/** MenuAdapter - Build the adapter for menu (home)
 * @author pelkidev
 * @since 1.0.0
 * @param items list to build
 * @param listener click on item's card
 * */
class MenuAdapter(
    private val items: List<ModelAdapterMenu>,
    private val listener : MenuClickListener
):
ListAdapter<ModelAdapterMenu, MenuAdapter.ViewHolder>(ItemsDiffCallBack){

    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelAdapterMenu>() {
        override fun areItemsTheSame(oldItem: ModelAdapterMenu, newItem: ModelAdapterMenu) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ModelAdapterMenu, newItem: ModelAdapterMenu) =
            oldItem == newItem
    }

    class ViewHolder(private val binding : RecyclerCardMenuBinding): RecyclerView.ViewHolder(binding.root){
        // Method like a listener; bring the item and the action of click
        fun bind(item: ModelAdapterMenu, action:MenuClickListener){
            // Synchronize the item response with the view
            binding.apply {
                ivImage.setImageResource(item.image!!)
                tvTitleCard.text =  item.titleCard
                root.setOnClickListener { action.onClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerCardMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, listener)
    }

    override fun getItemCount(): Int = items.size
}

class MenuClickListener(val listener: (item: ModelAdapterMenu) -> Unit){
    fun onClick(item: ModelAdapterMenu) = listener(item)
}
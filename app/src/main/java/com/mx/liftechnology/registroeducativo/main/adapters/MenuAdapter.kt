package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardMenuBinding

/** MenuAdapter - Build the adapter to menu (home)
 * @author pelkidev
 * @since 1.0.0
 * @param listener click on item's card
 * */
class MenuAdapter(
    private val listener: (ModelPrincipalMenuData) -> Unit
) : ListAdapter<ModelPrincipalMenuData, MenuAdapter.ViewHolder>(ItemsDiffCallBack) {

    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelPrincipalMenuData>() {
        override fun areItemsTheSame(oldItem: ModelPrincipalMenuData, newItem: ModelPrincipalMenuData) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ModelPrincipalMenuData, newItem: ModelPrincipalMenuData) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ModelPrincipalMenuData, listener: (ModelPrincipalMenuData) -> Unit) {
            binding.apply {
                root.setOnClickListener { listener(item)}
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

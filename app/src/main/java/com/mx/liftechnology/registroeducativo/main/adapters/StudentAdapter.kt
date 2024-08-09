package com.mx.liftechnology.registroeducativo.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardMenuBinding
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardStudentBinding
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelAdapterMenu

class StudentAdapter(
    private val items: List<StudentEntity>,
    private val listener : StudentClickListener
):
ListAdapter<StudentEntity, StudentAdapter.ViewHolder>(ItemsDiffCallBack){

    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<StudentEntity>() {
        override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity) =
            oldItem.idCurp == newItem.idCurp

        override fun areContentsTheSame(oldItem: StudentEntity, newItem: StudentEntity) =
            oldItem == newItem
    }

    class ViewHolder(private val binding : RecyclerCardStudentBinding): RecyclerView.ViewHolder(binding.root){
        // Method like a listener; bring the item and the action of click
        fun bind(item: StudentEntity, action:StudentClickListener){
            // Synchronize the item response with the view
            binding.apply {
                tvListNumber.text =  item.listNumber.toString()
                tvName.text =  ("${item.name} ${item.lastName} ${item.secondLastName}")
                root.setOnClickListener { action.onClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerCardStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, listener)
    }

    override fun getItemCount(): Int = items.size
}

class StudentClickListener(val listener: (item: StudentEntity) -> Unit){
    fun onClick(item: StudentEntity) = listener(item)
}
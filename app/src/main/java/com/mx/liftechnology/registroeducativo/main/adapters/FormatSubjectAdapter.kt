package com.mx.liftechnology.registroeducativo.main.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.domain.model.ModelFormatSubject
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardSubjectBinding

/**  - Build the adapter for Periods (Register schoool)
 * @author pelkidev
 * @since 1.0.0
 * @param items list to build
 * @param listener click on item's card
 * */
class FormatSubjectAdapter(
    private var items: MutableList<ModelFormatSubject>
) : ListAdapter<ModelFormatSubject, FormatSubjectAdapter.ViewHolder>(ItemsDiffCallBack) {
    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelFormatSubject>() {
        override fun areItemsTheSame(oldItem: ModelFormatSubject, newItem: ModelFormatSubject) =
            oldItem.position == newItem.position

        override fun areContentsTheSame(oldItem: ModelFormatSubject, newItem: ModelFormatSubject) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Method like a listener; bring the item and the action of click
        fun bind(item: ModelFormatSubject) {
            // Synchronize the item response with the view
            binding.apply {
                etFieldName.setText(item.name)
                etFieldPercent.setText(item.percent)

                // Detectar cambios en los EditTexts
                etFieldName.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        item.name = s.toString()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })

                etFieldPercent.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        item.percent = s.toString()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerCardSubjectBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = items.size

    fun getList() = items
}

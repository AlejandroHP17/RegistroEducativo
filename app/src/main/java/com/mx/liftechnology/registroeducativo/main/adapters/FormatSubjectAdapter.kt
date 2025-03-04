package com.mx.liftechnology.registroeducativo.main.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.databinding.RecyclerCardSubjectBinding

/** FormatSubjectAdapter - Build the adapter to subjects (Register subject)
 * @author pelkidev
 * @since 1.0.0
 * */
class FormatSubjectAdapter(
) : ListAdapter<ModelFormatSubjectDomain, FormatSubjectAdapter.ViewHolder>(ItemsDiffCallBack) {
    /** Use the [ItemsDiffCallBack] to detect if any item is duplicated and then no return the value */
    companion object ItemsDiffCallBack : DiffUtil.ItemCallback<ModelFormatSubjectDomain>() {
        override fun areItemsTheSame(oldItem: ModelFormatSubjectDomain, newItem: ModelFormatSubjectDomain) =
            oldItem.position == newItem.position

        override fun areContentsTheSame(oldItem: ModelFormatSubjectDomain, newItem: ModelFormatSubjectDomain) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RecyclerCardSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelFormatSubjectDomain) {
            binding.apply {
                etFieldName.setText(item.name)
                //etFieldPercent.setText(item.percent)

                // Detectar cambios en los EditTexts
                etFieldName.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        item.name = s.toString()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        //Nothing
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        //Nothing
                    }
                })

                etFieldPercent.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        item.percent = s.toString()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        //Nothing
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        //Nothing
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerCardSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    fun getList(): MutableList<ModelFormatSubjectDomain> = currentList

    fun updateList(newList: List<ModelFormatSubjectDomain>) {
        submitList(newList)
    }
}

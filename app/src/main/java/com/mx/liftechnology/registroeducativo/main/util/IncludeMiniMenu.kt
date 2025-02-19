package com.mx.liftechnology.registroeducativo.main.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.mx.liftechnology.registroeducativo.databinding.IncludeMiniMenuBinding
import com.mx.liftechnology.registroeducativo.main.adapters.MenuAdapter

class IncludeMiniMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: IncludeMiniMenuBinding = IncludeMiniMenuBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.rvCardMenu.layoutManager = GridLayoutManager(context, 2)
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun setAdapter(adapter: MenuAdapter) {
        binding.rvCardMenu.adapter = adapter
    }

    fun setEmptyState() {
        binding.rvCardMenu.visibility = View.GONE
        binding.ivEmptyState.visibility = View.VISIBLE
    }
}

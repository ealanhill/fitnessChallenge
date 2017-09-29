package me.ealanhill.fitnesschallenge.calendar.pointsEntry

import android.databinding.DataBindingUtil
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import me.ealanhill.fitnesschallenge.databinding.ItemPointEntryBinding

class PointEntryHolder(itemView: LinearLayout): PointsViewHolder(itemView) {
    val binding: ItemPointEntryBinding = DataBindingUtil.bind<ItemPointEntryBinding>(itemView)

    override fun bind(model: EntryFormModel) {
        binding.label.text = model.label
        binding.value.text = Math.abs(model.value).toString()
        binding.decrement.setOnClickListener {
            var intValue = Integer.parseInt(binding.value.text as String)
            handleValueChange(--intValue, model)
        }
        binding.increment.setOnClickListener {
            var intValue = Integer.parseInt(binding.value.text as String)
            handleValueChange(++intValue, model)
        }
    }

    private fun handleValueChange(value: Int, model: EntryFormModel) {
        val toSaveValue = if (value < 0) 0 else value
        binding.value.text = toSaveValue.toString()
        model.value = if (model.operation == "subtract") -1 * toSaveValue else toSaveValue
    }

}

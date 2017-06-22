package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.databinding.DataBindingUtil
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.databinding.ItemPointEntryBinding
import me.ealanhill.wtfitnesschallenge.model.EntryFormModel

class PointEntryHolder(itemView: LinearLayout): PointsViewHolder(itemView) {
    val binding: ItemPointEntryBinding = DataBindingUtil.bind<ItemPointEntryBinding>(itemView)

    override fun bind(model: EntryFormModel) {
        binding.inputItem.hint = model.label
        binding.inputItem.inputType = InputType.TYPE_CLASS_NUMBER
        binding.inputItem.setText(Math.abs(model.value).toString())
        binding.inputItem.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {
                val string = s.toString()
                if (TextUtils.isEmpty(string).not()) {
                    model.value = if (model.operation == "subtract") string.toInt() * -1 else string.toInt()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }

        })
    }

}

package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.databinding.DataBindingUtil
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.databinding.ItemPointEntryBinding

class PointEntryHolder(itemView: LinearLayout): PointsViewHolder(itemView) {
    val binding: ItemPointEntryBinding = DataBindingUtil.bind<ItemPointEntryBinding>(itemView)

    override fun bind(item: EntryFormItem) {
        binding.inputItem.hint = item.label
        binding.inputItem.inputType = InputType.TYPE_CLASS_NUMBER
        binding.inputItem.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(s.toString())) {
                    item.value = s.toString().toInt()
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

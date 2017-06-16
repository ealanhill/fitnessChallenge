package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.databinding.DataBindingUtil
import android.text.InputType
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.action.UpdatePointsAction
import me.ealanhill.wtfitnesschallenge.databinding.ItemPointEntryBinding
import me.ealanhill.wtfitnesschallenge.store.PointStore

class PointEntryHolder(itemView: LinearLayout, pointStore: PointStore): PointsViewHolder(itemView, pointStore) {
    val binding: ItemPointEntryBinding = DataBindingUtil.bind<ItemPointEntryBinding>(itemView)

    override fun bind(item: EntryFormItem) {
        binding.inputItem.hint = item.label
        binding.inputItem.inputType = InputType.TYPE_CLASS_NUMBER
        binding.inputItem.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                pointStore.dispatch(
                        UpdatePointsAction.create(item.label,
                                Integer.valueOf(binding.inputItem.text.toString())))
            }
        }
    }

}

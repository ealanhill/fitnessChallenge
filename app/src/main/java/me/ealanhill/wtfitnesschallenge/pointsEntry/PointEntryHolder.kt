package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.databinding.DataBindingUtil
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.databinding.ItemPointEntryBinding

class PointEntryHolder(itemView: LinearLayout): PointsViewHolder(itemView) {
    val binding: ItemPointEntryBinding = DataBindingUtil.bind<ItemPointEntryBinding>(itemView)

    override fun bind(item: EntryFormItem) {
        binding.inputItem.hint = item.label
    }

}

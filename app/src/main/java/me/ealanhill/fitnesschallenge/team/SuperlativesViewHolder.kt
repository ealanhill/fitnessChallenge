package me.ealanhill.fitnesschallenge.team

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import me.ealanhill.fitnesschallenge.databinding.ItemTextAndNumberBinding

class SuperlativesViewHolder(itemView: LinearLayout): RecyclerView.ViewHolder(itemView) {
    var binding: ItemTextAndNumberBinding = DataBindingUtil.bind<ItemTextAndNumberBinding>(itemView)

    fun bind(title: String, value: String) {
        binding.itemText.text = title
        binding.itemNumber.text = value
    }
}
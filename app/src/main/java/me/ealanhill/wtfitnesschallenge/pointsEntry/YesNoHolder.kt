package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.databinding.DataBindingUtil
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.FitnessChallengeApplication
import me.ealanhill.wtfitnesschallenge.R
import me.ealanhill.wtfitnesschallenge.databinding.ItemYesNoBinding
import me.ealanhill.wtfitnesschallenge.model.EntryFormModel

class YesNoHolder(itemView: LinearLayout): PointsViewHolder(itemView) {

    val binding: ItemYesNoBinding = DataBindingUtil.bind(itemView)

    override fun bind(model: EntryFormModel) {
        binding.yesNoQuestion.text = model.label
        binding.yesAnswer.text = FitnessChallengeApplication.context.getString(R.string.yes)
        binding.noAnswer.text = FitnessChallengeApplication.context.getText(R.string.no)
        if (model.value == 1) {
            binding.yesAnswer.isChecked = true
        } else {
            binding.noAnswer.isChecked = true
        }
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.yes_answer -> model.value = 1
                R.id.no_answer -> model.value = 0
                else -> throw IllegalArgumentException("Unknown id")
            }
        }
    }

}

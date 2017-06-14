package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.databinding.DataBindingUtil
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.FitnessChallengeApplication
import me.ealanhill.wtfitnesschallenge.R
import me.ealanhill.wtfitnesschallenge.databinding.ItemYesNoBinding

class YesNoHolder(itemView: LinearLayout): PointsViewHolder(itemView) {

    val binding: ItemYesNoBinding = DataBindingUtil.bind(itemView)

    override fun bind(item: EntryFormItem) {
        binding.yesNoQuestion.text = item.label
        binding.yesAnswer.text = FitnessChallengeApplication.context.getString(R.string.yes)
        binding.noAnswer.text = FitnessChallengeApplication.context.getText(R.string.no)
    }

}

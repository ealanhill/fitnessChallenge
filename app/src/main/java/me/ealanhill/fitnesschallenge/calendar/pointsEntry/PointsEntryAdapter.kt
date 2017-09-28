package me.ealanhill.wtfitnesschallenge.calendar.pointsEntry

import android.support.design.widget.TextInputLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import me.ealanhill.wtfitnesschallenge.R
import java.util.*

class PointsEntryAdapter(var models: List<EntryFormModel> = Collections.emptyList()):
        RecyclerView.Adapter<PointsViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (models[position].type == "pointEntry") {
            return R.layout.item_point_entry
        } else if (models[position].type == "yesNo") {
            return R.layout.item_yes_no
        }

        throw IllegalArgumentException("Unknown type " + models[position].type)
    }

    override fun onBindViewHolder(holder: PointsViewHolder, position: Int) {
        holder.bind(models[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        when(viewType) {
            R.layout.item_point_entry -> return PointEntryHolder(
                    inflater.inflate(R.layout.item_point_entry, parent, false) as LinearLayout)
            R.layout.item_yes_no -> return YesNoHolder(
                    inflater.inflate(R.layout.item_yes_no, parent, false) as LinearLayout)
        }
        throw IllegalArgumentException("Unknown type " + viewType)
    }

    override fun getItemCount(): Int = models.size

    fun setState(newModels: List<EntryFormModel>) {
        models = newModels
        notifyDataSetChanged()
    }
}

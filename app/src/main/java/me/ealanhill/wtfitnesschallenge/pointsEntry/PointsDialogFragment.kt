package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import me.ealanhill.wtfitnesschallenge.CalendarViewModel
import me.ealanhill.wtfitnesschallenge.DateItem
import me.ealanhill.wtfitnesschallenge.R
import me.ealanhill.wtfitnesschallenge.databinding.DialogPointsEntryBinding
import me.ealanhill.wtfitnesschallenge.store.MainStore

class PointsDialogFragment: DialogFragment() {

    private val ID = "id"

    private lateinit var store: MainStore
    private lateinit var dateItem: DateItem

    private var dayId: Int = -1

    fun newInstance(id: Int): PointsDialogFragment {
        val args: Bundle = Bundle()
        args.putInt(ID, id)
        val fragment: PointsDialogFragment = PointsDialogFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        store = ViewModelProviders.of(activity as AppCompatActivity)
                .get(CalendarViewModel::class.java)
                .store
        dayId = savedInstanceState.getInt(ID)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_points_entry, null)
        val binding: DialogPointsEntryBinding = DataBindingUtil.setContentView<DialogPointsEntryBinding>(activity, R.layout.dialog_points_entry)
                .apply {
                    pointsRecyclerVew.setHasFixedSize(true)
                    pointsRecyclerVew.layoutManager = LinearLayoutManager(activity)
//                    pointsRecyclerVew.adapter = PointsAdapter()
                }

        for (item: DateItem in store.state.dateItems) {
            if (item.date == dayId) {
                dateItem = item
                break
            }
        }

        return AlertDialog.Builder(activity, theme)
                .setTitle(getString(R.string.date_format, dateItem.month, dateItem.date))
                .setView(view)
                .setPositiveButton(android.R.string.ok, { dialog, which ->
                    // TODO
                })
                .create()
    }
}
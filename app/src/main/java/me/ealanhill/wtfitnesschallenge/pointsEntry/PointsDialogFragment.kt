package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import me.ealanhill.wtfitnesschallenge.CalendarViewModel
import me.ealanhill.wtfitnesschallenge.DateItem
import me.ealanhill.wtfitnesschallenge.R
import me.ealanhill.wtfitnesschallenge.databinding.DialogPointsEntryBinding
import me.ealanhill.wtfitnesschallenge.store.MainStore
import okio.Okio
import java.io.InputStream

class PointsDialogFragment: DialogFragment() {

    private lateinit var store: MainStore
    private lateinit var dateItem: DateItem
    private lateinit var items: List<EntryFormItem>

    private var dayId: Int = -1

    companion object {
        private val ID = "id"

        fun newInstance(id: Int): PointsDialogFragment {
            val args: Bundle = Bundle()
            args.putInt(ID, id)
            val fragment: PointsDialogFragment = PointsDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = ViewModelProviders.of(activity as AppCompatActivity)
                .get(CalendarViewModel::class.java)
                .store
        dayId = arguments.getInt(ID)

        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        val type = Types.newParameterizedType(List::class.java, EntryFormItem::class.java)
        val entryFormItemAdapter = moshi.adapter<List<EntryFormItem>>(type)
        activity.assets.open("test.json").use {
            inputStream: InputStream? ->
                items = entryFormItemAdapter.fromJson(Okio.buffer(Okio.source(inputStream)))!!
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_points_entry, null)
        val binding: DialogPointsEntryBinding = DataBindingUtil.setContentView<DialogPointsEntryBinding>(activity, R.layout.dialog_points_entry)
                .apply {
                    pointsRecyclerVew.setHasFixedSize(true)
                    pointsRecyclerVew.layoutManager = LinearLayoutManager(activity)
                    pointsRecyclerVew.adapter = PointsEntryAdapter(items)
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
package me.ealanhill.wtfitnesschallenge.pointsEntry

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import me.ealanhill.wtfitnesschallenge.CalendarViewModel
import me.ealanhill.wtfitnesschallenge.DateItem
import me.ealanhill.wtfitnesschallenge.R
import me.ealanhill.wtfitnesschallenge.action.UpdateCalendarPointsAction
import me.ealanhill.wtfitnesschallenge.databinding.DialogPointsEntryBinding
import me.ealanhill.wtfitnesschallenge.store.MainStore
import okio.Okio
import java.io.InputStream
import java.util.ArrayList

class PointsDialogFragment: DialogFragment(), LifecycleRegistryOwner {

    private lateinit var mainStore: MainStore
    private lateinit var dateItem: DateItem
    private lateinit var items: List<EntryFormItem>

    private var dayId: Int = -1

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = registry

    companion object {
        private val ID = "id"
        private val ITEMS = "items"

        fun newInstance(id: Int, items: List<EntryFormItem>): PointsDialogFragment {
            val args: Bundle = Bundle()
            args.putInt(ID, id)
            args.putParcelableArrayList(ITEMS, items.toMutableList() as ArrayList<out Parcelable>)
            val fragment: PointsDialogFragment = PointsDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainStore = ViewModelProviders.of(activity as AppCompatActivity)
                .get(CalendarViewModel::class.java)
                .store
        dayId = arguments.getInt(ID)
        items = arguments.getParcelableArrayList(ITEMS)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = activity.layoutInflater.inflate(R.layout.dialog_points_entry, null)
        val binding = DataBindingUtil.bind<DialogPointsEntryBinding>(view)
                .apply {
                    pointsRecyclerVew.setHasFixedSize(true)
                    pointsRecyclerVew.layoutManager = LinearLayoutManager(activity)
                    pointsRecyclerVew.adapter = PointsEntryAdapter(items)
                }

        for (item: DateItem in mainStore.state.dateItems) {
            if (item.date == dayId) {
                dateItem = item
                break
            }
        }

        return AlertDialog.Builder(activity, theme)
                .setTitle(getString(R.string.date_format, dateItem.month, dateItem.date))
                .setView(view)
                .setPositiveButton(android.R.string.ok, { dialog, which ->
                    val points: MutableMap<String, Int> = mutableMapOf()
                    items.map { entryFormItem ->
                        val value = if (entryFormItem.operation == "subtract") entryFormItem.value * -1 else entryFormItem.value
                        points.put(entryFormItem.name, value)
                    }
                    mainStore.dispatch(UpdateCalendarPointsAction.create(dateItem, points))
                })
                .create()
    }
}
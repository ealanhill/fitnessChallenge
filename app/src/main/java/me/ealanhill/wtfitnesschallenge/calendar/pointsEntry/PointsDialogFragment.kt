package me.ealanhill.wtfitnesschallenge.calendar.pointsEntry

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import me.ealanhill.wtfitnesschallenge.MainActivity
import me.ealanhill.wtfitnesschallenge.calendar.CalendarViewModel
import me.ealanhill.wtfitnesschallenge.calendar.DateItem
import me.ealanhill.wtfitnesschallenge.R
import me.ealanhill.wtfitnesschallenge.calendar.action.CalendarActionCreator
import me.ealanhill.wtfitnesschallenge.calendar.action.UpdateCalendarPointsAction
import me.ealanhill.wtfitnesschallenge.calendar.action.UploadPointsAction
import me.ealanhill.wtfitnesschallenge.databinding.DialogPointsEntryBinding
import me.ealanhill.wtfitnesschallenge.calendar.CalendarStore
import java.util.*
import javax.inject.Inject

class PointsDialogFragment: DialogFragment(), LifecycleRegistryOwner {

    private lateinit var calendarStore: CalendarStore
    private lateinit var pointEntryViewModel: PointEntryViewModel
    private lateinit var dateItem: DateItem

    @Inject
    lateinit var calendarActionCreator: CalendarActionCreator

    private var models: List<EntryFormModel> = Collections.emptyList()
    private var dayId: Int = -1

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = registry

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
        MainActivity.loadActionCreatorComponent.inject(this)
        calendarStore = ViewModelProviders.of(targetFragment)
                .get(CalendarViewModel::class.java)
                .store
        pointEntryViewModel = ViewModelProviders.of(this).get(PointEntryViewModel::class.java)
        dayId = arguments.getInt(ID)
        dateItem = calendarStore.state.getDate(dayId)
        val year = calendarStore.state.calendar.get(Calendar.YEAR)
        pointEntryViewModel.store.dispatch(calendarActionCreator.getEntryForm(year, dateItem.month, dayId))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = activity.layoutInflater.inflate(R.layout.dialog_points_entry, null)
        val binding = DataBindingUtil.bind<DialogPointsEntryBinding>(view)
                .apply {
                    pointsRecyclerVew.setHasFixedSize(true)
                    pointsRecyclerVew.layoutManager = LinearLayoutManager(activity)
                    pointsRecyclerVew.adapter = PointsEntryAdapter(models)
                }

        pointEntryViewModel.state
                .observe(this, android.arch.lifecycle.Observer<PointEntryState> {
                    data ->
                    data?.let {
                        binding.loading.visibility = if (data.loading) View.VISIBLE else View.GONE
                        models = data.entryFormModels
                        (binding.pointsRecyclerVew.adapter as PointsEntryAdapter).setState(models)
                    }
                })

        return AlertDialog.Builder(activity, theme)
                .setTitle(getString(R.string.date_format, dateItem.month, dateItem.date))
                .setView(view)
                .setPositiveButton(android.R.string.ok, { dialog, which ->
                    val points: MutableMap<String, Int> = mutableMapOf()
                    models.map { entryFormItem ->
                        points.put(entryFormItem.name, entryFormItem.value)
                    }
                    calendarStore.dispatch(UpdateCalendarPointsAction.create(dateItem, points))
                    pointEntryViewModel.store.dispatch(UploadPointsAction(models, dateItem.year,
                            dateItem.month, dateItem.date))
                })
                .create()
    }
}
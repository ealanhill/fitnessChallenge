package me.ealanhill.fitnesschallenge

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

interface AutoUpdatableAdapter {

    fun <T> RecyclerView.Adapter<*>.autoNotify(oldList: List<T>,
                                               newList: List<T>,
                                               diffUtil: DiffUtil.Callback,
                                               compare: (T, T) -> Boolean) {
        val diff = DiffUtil.calculateDiff(diffUtil)
        diff.dispatchUpdatesTo(this)
    }
}

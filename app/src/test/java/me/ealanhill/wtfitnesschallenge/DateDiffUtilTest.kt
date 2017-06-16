package me.ealanhill.wtfitnesschallenge

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import me.ealanhill.wtfitnesschallenge.util.DateDiffUtil
import org.junit.Test

class DateDiffUtilTest {

    @Test
    fun test_map_diffing_they_are_the_same() {
        val oldDateItem = DateItem("Jun", 1, 0, mapOf("string" to 1, "string 2" to 2, "string 3" to 3))
        val newDateItem = DateItem("Jun", 1, 0, mapOf("string" to 1, "string 2" to 2, "string 3" to 3))

        assertTrue("Maps were not the same", DateDiffUtil(listOf(oldDateItem), listOf(newDateItem)).areContentsTheSame(0, 0))
    }

    @Test
    fun test_map_diff_different_maps() {
        val oldDateItem = DateItem("Jun", 1, 0, mapOf("string" to 3, "string 2" to 2, "string 3" to 3))
        val newDateItem = DateItem("Jun", 1, 0, mapOf("string" to 1, "string 2" to 2, "string 3" to 3))

        assertFalse("Maps were the same, old date: " + oldDateItem.pointsMap + ", new date: " + newDateItem.pointsMap, DateDiffUtil(listOf(oldDateItem), listOf(newDateItem)).areContentsTheSame(0, 0))
    }
}

package me.ealanhill.wtfitnesschallenge.calendar

class DateItem(val year: Int,
               val month: String,
               val date: Int,
               var totalPoints: Int,
               var pointsMap: Map<String, Int>)

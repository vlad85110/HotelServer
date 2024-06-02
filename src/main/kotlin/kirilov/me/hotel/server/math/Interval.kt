package kirilov.me.hotel.server.math

import kotlin.math.max
import kotlin.math.min

class Interval(val start: Long, val end: Long) {
    fun isIntersectsWih(other: Interval): Boolean {
        val minX = min(start, other.start)
        val maxX = max(end, other.end)

        return maxX - minX <= len() + other.len()
    }

    private fun len(): Long {
        return end - start
    }
}
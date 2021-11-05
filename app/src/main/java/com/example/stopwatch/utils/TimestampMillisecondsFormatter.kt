package com.example.stopwatch.utils

class TimestampMillisecondsFormatter {
    fun formatTime(timestamp: Long): String {
        val msFormatted = (timestamp % 1000).pad(3)
        val seconds = timestamp / 1000
        val secondsFormatted = (seconds % 60).pad(2)
        val minutes = seconds / 60
        val minutesFormatted = (minutes % 60).pad(2)
        val hours = minutes / 60
        return if (hours > 0) {
            val hoursFormatted = (minutes % 60).pad(2)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else "$minutesFormatted:$secondsFormatted:$msFormatted"
    }

    private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')
}

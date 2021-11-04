package com.example.stopwatch

class ElapsedTimeCalculator(private val timestampProvider: TimestampProvider) {
    fun calculate(state: StopwatchState.Running): Long {
        val currentTimestamp = timestampProvider.getMs()
        val elapsedTime: Long =
            if (currentTimestamp > state.startTime) currentTimestamp - state.startTime
            else 0
        return elapsedTime + state.elapsedTime
    }
}
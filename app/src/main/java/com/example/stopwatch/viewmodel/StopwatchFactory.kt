package com.example.stopwatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stopwatch.model.StopwatchStateHolder
import kotlinx.coroutines.CoroutineScope

class StopwatchFactory(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope: CoroutineScope
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        StopwatchListOrchestrator(stopwatchStateHolder, scope) as T
}
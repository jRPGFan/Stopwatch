package com.example.stopwatch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stopwatch.model.ElapsedTimeCalculator
import com.example.stopwatch.viewmodel.StopwatchListOrchestrator
import com.example.stopwatch.model.StopwatchStateCalculator
import com.example.stopwatch.databinding.ActivityMainBinding
import com.example.stopwatch.model.StopwatchStateHolder
import com.example.stopwatch.model.TimestampProvider
import com.example.stopwatch.model.TimestampProviderImpl
import com.example.stopwatch.utils.TimestampMillisecondsFormatter
import com.example.stopwatch.viewmodel.StopwatchFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val timestampProvider: TimestampProvider = TimestampProviderImpl()

    private val stopwatchListOrchestrator = StopwatchFactory(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider),
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(Dispatchers.Main + SupervisorJob())
    ).create(StopwatchListOrchestrator::class.java)

    private val stopwatchListOrchestrator2 = StopwatchFactory(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider),
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(Dispatchers.Main + SupervisorJob())
    ).create(StopwatchListOrchestrator::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        init()

        CoroutineScope(Dispatchers.Main + SupervisorJob())
            .launch { stopwatchListOrchestrator.ticker.collect { viewBinding.textTime.text = it } }

        CoroutineScope(Dispatchers.Main + SupervisorJob())
            .launch { stopwatchListOrchestrator2.ticker.collect { viewBinding.textTime2.text = it } }
    }

    private fun init() {
        viewBinding.buttonStart.setOnClickListener { stopwatchListOrchestrator.start() }
        viewBinding.buttonPause.setOnClickListener { stopwatchListOrchestrator.pause() }
        viewBinding.buttonStop.setOnClickListener { stopwatchListOrchestrator.stop() }

        viewBinding.buttonStart2.setOnClickListener { stopwatchListOrchestrator2.start() }
        viewBinding.buttonPause2.setOnClickListener { stopwatchListOrchestrator2.pause() }
        viewBinding.buttonStop2.setOnClickListener { stopwatchListOrchestrator2.stop() }

    }
}
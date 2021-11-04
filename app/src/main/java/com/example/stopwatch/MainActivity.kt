package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val timestampProvider = object : TimestampProvider {
        override fun getMs(): Long = System.currentTimeMillis()
    }

    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider),
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(Dispatchers.Main + SupervisorJob())
    )

    private val stopwatchListOrchestrator2 = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider),
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(Dispatchers.Main + SupervisorJob())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        CoroutineScope(Dispatchers.Main + SupervisorJob())
            .launch { stopwatchListOrchestrator.ticker.collect{ viewBinding.textTime.text = it } }

        viewBinding.buttonStart.setOnClickListener { stopwatchListOrchestrator.start() }
        viewBinding.buttonPause.setOnClickListener { stopwatchListOrchestrator.pause() }
        viewBinding.buttonStop.setOnClickListener { stopwatchListOrchestrator.stop() }

        CoroutineScope(Dispatchers.Main + SupervisorJob())
            .launch { stopwatchListOrchestrator2.ticker.collect{ viewBinding.textTime2.text = it } }
        viewBinding.buttonStart2.setOnClickListener { stopwatchListOrchestrator2.start() }
        viewBinding.buttonPause2.setOnClickListener { stopwatchListOrchestrator2.pause() }
        viewBinding.buttonStop2.setOnClickListener { stopwatchListOrchestrator2.stop() }
    }
}
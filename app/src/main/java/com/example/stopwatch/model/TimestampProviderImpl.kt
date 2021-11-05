package com.example.stopwatch.model

class TimestampProviderImpl : TimestampProvider {
    override fun getMs(): Long = System.currentTimeMillis()
}
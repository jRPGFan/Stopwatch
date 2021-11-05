package com.example.stopwatch.model

interface TimestampProvider {
    fun getMs(): Long
}
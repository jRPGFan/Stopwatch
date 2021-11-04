package com.example.stopwatch

interface TimestampProvider {
    fun getMs(): Long
}
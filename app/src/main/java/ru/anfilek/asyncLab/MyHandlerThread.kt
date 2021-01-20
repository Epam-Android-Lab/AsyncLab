package ru.anfilek.asyncLab

import android.os.Handler
import android.os.Message
import android.util.Log
import java.util.*

class MyHandlerThread : android.os.HandlerThread(TAG) {

    companion object {
        private const val TAG = "MyHandlerThread"
    }

    init {
        start()
        prepareHandler()
    }

    lateinit var handler: Handler
        private set

    fun post(r : Runnable) {
        handler.post(r)
    }

    private fun prepareHandler() {
        handler = Handler(looper)
    }
}
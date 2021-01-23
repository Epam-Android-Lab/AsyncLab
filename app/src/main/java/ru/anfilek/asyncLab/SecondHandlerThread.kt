package ru.anfilek.asyncLab

import android.os.Handler
import android.os.Message

class SecondHandlerThread (val threadCompetitor: Handler.Callback) : android.os.HandlerThread(TAG), Handler.Callback {

    var handler: Handler

    init {
        start()
        handler = Handler(looper)
    }

    companion object {
        private const val TAG = "Second"
    }

    override fun handleMessage(msg: Message): Boolean {
        TODO("Not yet implemented")
    }
}
package ru.anfilek.asyncLab

import android.os.Handler
import android.os.Message

class FirstHandlerThread : android.os.HandlerThread(TAG), Handler.Callback {

    private var handler: Handler

    init {
        start()
        handler = Handler(looper)
    }

    companion object {
        private const val TAG = "First"
    }

    lateinit var threadCompetitor: Handler.Callback

    fun setCompetitor(thread: Handler.Callback) {
        threadCompetitor = thread
    }

    override fun handleMessage(msg: Message): Boolean {
        TODO("Not yet implemented")
    }

    fun startGame() {
        val message = Message()
        message.data.apply {
            putInt("key", 0)
        }
    }
}


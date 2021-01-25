package ru.anfilek.asyncLab

import android.os.Handler
import android.os.Looper
import android.os.Message
import kotlin.random.Random

class FirstHandlerThread : android.os.HandlerThread(TAG), Handler.Callback {

    private var handler: Handler

    init {
        start()
        handler = Handler(Looper.getMainLooper())
    }

    companion object {
        private const val TAG = "First"
    }

    lateinit var threadCompetitor: Handler.Callback

    fun setCompetitor(thread: Handler.Callback) {
        threadCompetitor = thread
    }

    private var currentNumber: Int = 0

    override fun handleMessage(msg: Message): Boolean {

        if (msg.data.getInt("key") < 100 && msg.data != null) {
            currentNumber = msg.data.getInt("key") + Random.nextInt(1, 5)
                //отображение текущего значения в TextView

            if(currentNumber >= 100) {
                //отображение победителя

            } else {
                msg.data.apply {
                    putInt("key", currentNumber)
                }
                threadCompetitor.handleMessage(msg)
            }
        }
        return true
    }

    fun startGame() {
        val message = Message()
        message.data.apply {
            putInt("key", 0)
        }
        threadCompetitor.handleMessage(message)
    }
}


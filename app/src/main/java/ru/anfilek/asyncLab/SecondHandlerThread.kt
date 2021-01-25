package ru.anfilek.asyncLab

import android.os.Handler
import android.os.Message
import kotlin.random.Random

class SecondHandlerThread (val threadCompetitor: Handler.Callback) : android.os.HandlerThread(TAG), Handler.Callback {

    var handler: Handler

    init {
        start()
        handler = Handler(looper)
    }

    companion object {
        private const val TAG = "Second"
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
}
package ru.anfilek.asyncLab

import android.os.Handler
import android.os.Message
import kotlin.random.Random

class SecondHandlerThread (private val threadCompetitor: Handler.Callback, private val mainHandler: Handler) : android.os.HandlerThread(TAG), Handler.Callback {

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
            val newMessage = Message()
                newMessage.data.apply {
                    putInt("second", currentNumber)
                }
                mainHandler.dispatchMessage(newMessage)

            if(currentNumber >= 100) {
                //отображение победителя
                val finalMessage = Message()
                finalMessage.data.apply {
                    putString("finalResult2", "Second thread is Winner!")
                }
                mainHandler.dispatchMessage(finalMessage)

            } else {
                //передача текущего значения другому потоку
                newMessage.data.apply {
                    putInt("key", currentNumber)
                }
                threadCompetitor.handleMessage(newMessage)
            }
        }
        return true
    }
}
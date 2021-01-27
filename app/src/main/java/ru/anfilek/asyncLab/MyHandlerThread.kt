package ru.anfilek.asyncLab

import android.os.Handler
import android.os.Message
import android.util.Log
import kotlin.random.Random

class MyHandlerThread(private val handlerName: String, private val mainHandler: Handler) :
    android.os.HandlerThread(handlerName), Handler.Callback {

    companion object {
        const val TAG = "MyHandlerThread"
    }

    init {
        this.start()
    }

    private var handler: Handler? = null

    private var anotherHandler: MyHandlerThread? = null

    fun doWork(value: Int = 0) {
        handler ?: initHandler()
        sendMessage(value)
    }

    fun setAnotherHandler(another: MyHandlerThread) {
        anotherHandler = another
    }

    private fun initHandler() {
        handler = Handler(looper, this)
    }

    private fun sendMessage(value: Int) {
        handler?.sendEmptyMessage(value)
    }

    override fun handleMessage(msg: Message): Boolean {
        Log.d(
            TAG,
            "Thread $handlerName: message received: ${msg.what}"
        )

        val randomInt = Random.nextInt(1, 6)

        Log.d(TAG, "Thread $handlerName will add $randomInt")

        val newMessage = msg.what + randomInt

        if (newMessage >= 100) {
            Log.d(
                TAG,
                "Thread $handlerName won! Sending message to ui"
            )

            val message = Message().apply {
                obj = Pair(newMessage, handlerName)
            }
            mainHandler.sendMessage(message)
        } else {
            Log.d(
                TAG,
                "Thread $handlerName is sending message to another thread"
            )
            anotherHandler?.doWork(newMessage)
        }

        return true
    }
}
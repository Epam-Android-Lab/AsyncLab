package ru.anfilek.asyncLab

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import kotlin.random.Random

const val FIRST_THREAD_TAG = "FIRST"
const val SECOND_THREAD_TAG = "SECOND"
const val STATUS_WORK = "WORK"
const val STATUS_DONE = "DONE"

fun testSharedResources() {
    val thread2 = SecondThreadHandler()
    val thread1 = FirstThreadHandler(thread2)
    thread2.anotherHandler = thread1
    thread1.startGame()
}

class FirstThreadHandler(private val anotherHandler: Handler.Callback) :
        android.os.HandlerThread(FIRST_THREAD_TAG), Handler.Callback {

    init {
        start()
        prepareHandler()
    }

    lateinit var handler: Handler
        private set

    private fun prepareHandler() {
        handler = Handler(looper)
    }

    override fun handleMessage(msg: Message): Boolean {
        handleMassageCase(this.name, msg, anotherHandler)
        return true
    }

    fun startGame() {
        val m = Message()
        m.data.apply {
            putString("status", STATUS_WORK)
            putInt("value", 0)
        }
        Log.d(this.name, "start")
        anotherHandler.handleMessage(m)
    }
}

class SecondThreadHandler : android.os.HandlerThread(SECOND_THREAD_TAG), Handler.Callback {

    init {
        start()
        prepareHandler()
    }

    lateinit var handler: Handler
        private set

    lateinit var anotherHandler: Handler.Callback

    private fun prepareHandler() {
        handler = Handler(Looper.getMainLooper())
    }

    override fun handleMessage(msg: Message): Boolean {
        handleMassageCase(this.name, msg, anotherHandler)
        return true
    }
}

fun handleMassageCase(tag: String, msg: Message, anotherHandler: Handler.Callback) {
    Log.d(tag, msg.data.getString("status") ?: "FAIL")
    Log.d(tag, "${msg.data.getInt("value")}")
    val status: String
    val value: Int

    if (msg.data != null && msg.data.getString("status") != STATUS_DONE) {
        value = msg.data.getInt("value") + Random.nextInt(1, 5)

        status = if (value < 100) { STATUS_WORK } else STATUS_DONE

        msg.data.apply {
            putString("status", status)
            putInt("value", value)
        }
        anotherHandler.handleMessage(msg)
    }
}
package ru.anfilek.asyncLab

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import ru.anfilek.asyncLab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener { startHandlerThread() }
        binding.btnStop.setOnClickListener { stopHandlerThread() }
        binding.btnAsync.setOnClickListener { startAsync() }
        binding.btnFreeze.setOnClickListener { freeze() }

        testSharedResources()
    }

    private fun startHandlerThread() {
        val mainHandler = Handler(mainLooper) {
            binding.tv.text = (it.obj as Pair<*, *>).second.toString()
            return@Handler true
        }
        val handlerThread1 = MyHandlerThread("1", mainHandler)
        val handlerThread2 = MyHandlerThread("2", mainHandler)

        handlerThread1.setAnotherHandler(handlerThread2)
        handlerThread2.setAnotherHandler(handlerThread1)

        handlerThread1.doWork(1)
    }

    private fun stopHandlerThread() {
        // optional
    }

    private fun startAsync() {
        MyAsyncTask().execute()
    }

    private fun freeze() {
        Thread.sleep(9000)
    }

    // DO NOT DO THIS! NEVER!
    class MyAsyncTask : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
//            tv.text = result
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }

        override fun onCancelled(result: String?) {
            super.onCancelled(result)
        }

        override fun onCancelled() {
            super.onCancelled()
        }

        override fun doInBackground(vararg params: String?): String {
            Thread.sleep(3000)
            return "That's all"
        }
    }
}



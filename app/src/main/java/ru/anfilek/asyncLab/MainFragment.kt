package ru.anfilek.asyncLab

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.anfilek.asyncLab.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private var handlerThread: MyHandlerThread? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val counterFragment = CounterFragment()

        binding.btnStart.setOnClickListener { startHandlerThread() }
        binding.btnStop.setOnClickListener { stopHandlerThread() }
        binding.btnAsync.setOnClickListener { startAsync() }
        binding.btnFreeze.setOnClickListener { freeze() }
        binding.buttonGame.setOnClickListener{

            parentFragmentManager.beginTransaction().apply {
                setReorderingAllowed(true)
                add(R.id.fragmentContainerView, counterFragment)
                addToBackStack(null)
                commit()
            }
        }
        //testSharedResources()
    }

    private fun startHandlerThread() {
        handlerThread = MyHandlerThread()
        handlerThread?.doWork()
        Toast.makeText(context, "Start!", Toast.LENGTH_SHORT).show()
        //handlerThread?.start()
        //handlerThread?.post()
    }

    private fun stopHandlerThread() {
        // optional
        handlerThread?.quit()
        Toast.makeText(context, "Stop!", Toast.LENGTH_SHORT).show()
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
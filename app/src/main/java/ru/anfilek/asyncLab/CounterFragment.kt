package ru.anfilek.asyncLab

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import ru.anfilek.asyncLab.databinding.FragmentCounterBinding

class CounterFragment : Fragment(R.layout.fragment_counter) {

    private val mainHandlerThread = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {

            if (msg.data.containsKey("first")) {
                setResultText1(msg.data.getInt("first"))
            } else {
                if (msg.data.containsKey("second")) {
                    setResultText2(msg.data.getInt("second"))
                } else {
                    if (msg.data.containsKey("finalResult1")) {
                        binding.result.text = msg.data.getString("finalResult1")
                        binding.startButton.isEnabled = true
                    } else {
                        if (msg.data.containsKey("finalResult2")) {
                            binding.result.text = msg.data.getString("finalResult2")
                            binding.startButton.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private var _binding: FragmentCounterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startButton.setOnClickListener {
            binding.startButton.isEnabled = false
            val thread1 = FirstHandlerThread(mainHandlerThread)
            val thread2 = SecondHandlerThread(thread1, mainHandlerThread)
            thread1.setCompetitor(thread2)
            thread1.startGame()
        }
    }

    fun setResultText1 (num: Int) {
        binding.thread1CurrentResult.text = "$num"
    }

    fun setResultText2 (num: Int) {
        binding.thread2CurrentResult.text = "$num"
    }

}
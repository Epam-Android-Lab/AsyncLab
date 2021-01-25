package ru.anfilek.asyncLab

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import ru.anfilek.asyncLab.databinding.FragmentCounterBinding

class CounterFragment : Fragment(R.layout.fragment_counter) {

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

            val thread1 = FirstHandlerThread()
            val thread2 = SecondHandlerThread(thread1)
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
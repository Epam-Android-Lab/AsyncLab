package ru.anfilek.asyncLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.anfilek.asyncLab.databinding.FragmentCounterBinding

class CounterFragment : Fragment(R.layout.fragment_counter) {

    private var binding : FragmentCounterBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCounterBinding.inflate(layoutInflater)

        binding?.startButton?.setOnClickListener {
            binding!!.startButton.isEnabled = false

            val thread1 = FirstHandlerThread()
            val thread2 = SecondHandlerThread(thread1)
            thread1.setCompetitor(thread2)
            thread1.startGame()
        }
    }
}
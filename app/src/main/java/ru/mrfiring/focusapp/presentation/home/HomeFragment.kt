package ru.mrfiring.focusapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import ru.mrfiring.focusapp.databinding.FragmentHomeBinding
import ru.mrfiring.focusapp.di.viewmodel.ViewModelFactory
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        viewModel.contacts.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Received: $it", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

}